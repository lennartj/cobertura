/*
 * #%L
 * cobertura-reporting-api
 * %%
 * Copyright (C) 2013 Cobertura
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package net.sourceforge.cobertura.reporting.api;

import org.apache.commons.lang3.Validate;

import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Abstract implementation of a ReportGenerator using Strings for templates and data.
 *
 * @param <T> The concrete type of Report produced by this AbstractStringTemplateReportGenerator.
 * @param <V> The type of token values used by this AbstractStringTemplateReportGenerator.
 *            Object is a likely candidate for this type unless a very specific ReportGenerator is used.
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid, jGuru Europe AB</a>
 */
public abstract class AbstractStringTemplateReportGenerator<T extends Report, V>
        implements ReportGenerator<Map<String, V>, String, T> {

    // Internal state
    private Map<LocalizedReportIdentifier, String> templates;
    private Map<String, Map<String, V>> id2DataMap;

    /**
     * Default constructor creating an AbstractStringTemplateReportGenerator with empty internal state.
     */
    protected AbstractStringTemplateReportGenerator() {
        this(new TreeMap<LocalizedReportIdentifier, String>(), new TreeMap<String, Map<String, V>>());
    }

    /**
     * Compound constructor creating an AbstractStringTemplateReportGenerator instance
     * wrapping the supplied argument data.
     *
     * @param templates  A map relating LocalizedReportIdentifier instances to Strings holding template data,
     *                   typically markup-formatted strings of some kind.
     * @param id2DataMap A map relating identifiers to maps holding (token to value) tuples, used in
     *                   report generation.
     */
    protected AbstractStringTemplateReportGenerator(final Map<LocalizedReportIdentifier, String> templates,
                                                    final Map<String, Map<String, V>> id2DataMap) {

        // Check sanity
        Validate.notNull(templates, "Cannot handle null templates argument.");
        Validate.notNull(id2DataMap, "Cannot handle null id2DataMap argument.");

        // Assign internal state
        this.templates = templates;
        this.id2DataMap = id2DataMap;
    }

    /**
     * This implementation does not validate that id2DataMap tuples or values are not
     * overwritten, implying that the caller is responsible for not modifying values
     * which are being used for report generation at the same time.
     * <p/>
     * {@inheritDoc}
     */
    @Override
    public void addData(final String id, final Map<String, V> data) {

        // Check sanity
        Validate.notEmpty(id, "Cannot handle null or empty id argument.");
        Validate.notEmpty(data, "Cannot handle null or empty data argument.");

        // Assign internal state
        Map<String, V> currentData = id2DataMap.get(id);
        if (currentData == null) {
            id2DataMap.put(id, data);
        } else {
            currentData.putAll(data);
        }
    }

    /**
     * Convenience method to add a single data tuple (i.e. key 2 value) to the supplied id.
     *
     * @param id    The identifier of this data, such as "classCoverage" or "packageBranchCoverage".
     *              This identifier classifies data to permit this ReportGenerator to create several
     *              different reports from the total data added.
     * @param key   The report data key to add for report generation.
     * @param value The report data value to add for report generation.
     */
    public void addData(final String id, final String key, final V value) {

        // Check sanity
        Validate.notEmpty(id, "Cannot handle null or empty id argument.");
        Validate.notEmpty(key, "Cannot handle null or empty key argument.");

        // Delegate
        final Map<String, V> wrapper = new TreeMap<String, V>();
        wrapper.put(key, value);
        addData(id, wrapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void addTemplate(final LocalizedReportIdentifier identifier, final String template) {

        // Check sanity
        Validate.notNull(identifier, "Cannot handle null identifier argument.");
        Validate.notEmpty(template, "Cannot handle null or empty template argument.");

        // Add the template overwriting
        if (templates.containsKey(identifier)) {
            throw new IllegalArgumentException("Template for identifier " + identifier
                    + " already added. Will not overwrite existing template.");
        }

        templates.put(identifier, template);
    }

    /**
     * Generates a report for the supplied id and locale.
     *
     * @param identifier The identifier for the report to generate.
     * @return The generated report.
     * @throws IllegalArgumentException if the combination of id and locale arguments could not be used
     *                                  to produce a report properly.
     */
    @Override
    public final T compileReport(final LocalizedReportIdentifier identifier) throws IllegalArgumentException {

        // Check sanity
        Validate.notNull(identifier, "Cannot handle null identifier argument.");

        // Dig out the template given.
        final String template = templates.get(identifier);
        if (template == null) {
            throw new IllegalArgumentException("No template found for LocalizedReportIdentifier " + identifier);
        }

        // Find the relevant templates
        SortedMap<Integer, String> localTemplates = new TreeMap<Integer, String>();
        for (Map.Entry<LocalizedReportIdentifier, String> current : templates.entrySet()) {

            // Should we
            final LocalizedReportIdentifier key = current.getKey();
            if (identifier.getId().equals(key.getId()) && identifier.getLocale().getLanguage().equals(
                    key.getLocale().getLanguage())) {
                localTemplates.put(key.getSequence(), current.getValue());
            }
        }

        // Delegate and return.
        // TODO: Threaded execution (Fork/Join or Executor) around this call?
        return doCompileReport(identifier, localTemplates);
    }

    /**
     * Retrieves a fairly verbose printout of the state of this AbstractStringTemplateReportGenerator.
     * <p/>
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder(
                "AbstractStringTemplateReportGenerator held (" + templates.size()
                        + ") templates for the following LocalizedReportIdentifiers:\n");

        for(Map.Entry<LocalizedReportIdentifier, String> current : templates.entrySet()) {
            builder.append(" ").append(current.getKey()).append("\n");
        }

        builder.append("The following tokens were known:\n").append(id2DataMap.toString());

        // All done.
        return builder.toString();
    }

    //
    // Private helpers
    //


    /**
     * @return A readonly version of the id2TokenMap which is the token map for given identifiers,
     * relating identifiers to maps holding (token to value) tuples, used during report generation.
     */
    protected final Map<String, Map<String, V>> getId2DataMap() {
        return Collections.unmodifiableMap(id2DataMap);
    }

    /**
     * Performs the actual report compilation for the supplied (non-null) LocalizedReportIdentifier,
     * returning the complete Report.
     *
     * @param nonNullIdentifier The non-null LocalizedReportIdentifier used to start the report compilation.
     * @param templates         The report templates related to the given report identifier.
     * @return The generated report.
     */
    protected abstract T doCompileReport(final LocalizedReportIdentifier nonNullIdentifier,
                                         final SortedMap<Integer, String> templates);
}
