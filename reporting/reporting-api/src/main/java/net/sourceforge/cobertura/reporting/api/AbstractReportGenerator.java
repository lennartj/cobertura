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

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Abstract implementation of the ReportGenerator interface, mainly performing argument validation in method calls.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public abstract class AbstractReportGenerator<D, T, R extends Report> implements ReportGenerator<D, T, R> {

    // Internal state
    private SortedMap<String, D> dataMap;
    private SortedMap<LocalizedReportIdentifier, T> templatesMap;

    /**
     * Default constructor creating empty internal state holders.
     */
    protected AbstractReportGenerator() {
        this(new TreeMap<String, D>(), new TreeMap<LocalizedReportIdentifier, T>());
    }

    /**
     * Compound constructor creating an AbstractReportGenerator wrapping the supplied objects.
     *
     * @param templatesMap A map relating LocalizedReportIdentifier instances to template data. Cannot be null.
     * @param dataMap      A map relating scope identifiers to data used to calculate a report. Cannot be null.
     */
    protected AbstractReportGenerator(final SortedMap<String, D> dataMap,
                                      final SortedMap<LocalizedReportIdentifier, T> templatesMap) {

        // Check sanity
        Validate.notNull(dataMap, "Cannot handle null dataMap argument.");
        Validate.notNull(templatesMap, "Cannot handle null templatesMap argument.");

        // Assign internal state
        this.dataMap = dataMap;
        this.templatesMap = templatesMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void addData(final String id, final D data) {

        // Check sanity
        Validate.notEmpty(id, "Cannot handle null or empty id argument.");
        Validate.notNull(data, "Cannot handle null data argument.");

        // Simply delegate
        internalAddData(id, data, dataMap);
    }

    /**
     * Adds a template for use within this ReportGenerator.
     *
     * @param identifier The identifier for the template to add.
     * @param template   The template itself.
     */
    @Override
    public void addTemplate(final LocalizedReportIdentifier identifier, final T template) {

        // Check sanity
        Validate.notNull(identifier, "Cannot handle null identifier argument.");
        Validate.notNull(template, "Cannot handle null template argument.");

        // Don't overwrite an existing template?
        if (templatesMap.containsKey(identifier)) {
            throw new IllegalArgumentException("Template for identifier " + identifier
                    + " already added. Will not overwrite existing template.");
        }

        templatesMap.put(identifier, template);
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
    public final R compileReport(final LocalizedReportIdentifier identifier) throws IllegalArgumentException {

        // Check sanity
        Validate.notNull(identifier, "Cannot handle null identifier argument.");

        // Delegate
        // TODO: Add fork/join or executor mechanics around this call?
        return internalCompileReport(identifier, templatesMap, dataMap);
    }

    //
    // Private helpers
    //

    /**
     * Template factory method to add report data to the internal state of this AbstractReportGenerator.
     *
     * @param dataMap     The data map before adding any data.
     * @param id          The identifier of this data, such as "classCoverage" or "packageBranchCoverage".
     *                    This identifier classifies data to permit this ReportGenerator to create several
     *                    different reports from the total data added. Never null or empty.
     * @param nonNullData The data to add. Never null.
     */
    protected abstract void internalAddData(final String id, final D nonNullData, final SortedMap<String, D> dataMap);

    /**
     * Performs the actual report compilation for the supplied (non-null) LocalizedReportIdentifier,
     * returning the complete Report.
     *
     * @param nonNullIdentifier The non-null LocalizedReportIdentifier used to start the report compilation.
     * @param templatesMap      The current map holding all templates.
     * @param dataMap           The current map relating ID to data objects.
     * @return The generated report.
     */
    protected abstract R internalCompileReport(final LocalizedReportIdentifier nonNullIdentifier,
                                               final SortedMap<LocalizedReportIdentifier, T> templatesMap,
                                               final SortedMap<String, D> dataMap);
}
