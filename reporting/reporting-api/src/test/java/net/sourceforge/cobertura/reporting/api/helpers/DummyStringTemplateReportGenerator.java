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
package net.sourceforge.cobertura.reporting.api.helpers;

import net.sourceforge.cobertura.reporting.api.AbstractStringTemplateReportGenerator;
import net.sourceforge.cobertura.reporting.api.LocalizedReportIdentifier;
import org.joda.time.DateTime;

import java.util.Map;
import java.util.SortedMap;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid, jGuru Europe AB</a>
 */
public class DummyStringTemplateReportGenerator extends AbstractStringTemplateReportGenerator<DummyReport> {

    // Internal state
    private DateTime generationTime;

    /**
     * Default constructor creating an AbstractStringTemplateReportGenerator with empty internal state.
     */
    public DummyStringTemplateReportGenerator(final DateTime generationTime) {
        this.generationTime = generationTime;
    }

    /**
     * Compound constructor creating an AbstractStringTemplateReportGenerator instance
     * wrapping the supplied argument data.
     *
     * @param templates  A map relating LocalizedReportIdentifier instances to Strings holding template data,
     *                   typically markup-formatted strings of some kind.
     * @param id2DataMap A map relating identifiers to maps holding (token to value) tuples, used in
     */
    public DummyStringTemplateReportGenerator(final Map<LocalizedReportIdentifier, String> templates,
                                              final Map<String, Map<String, String>> id2DataMap,
                                              final DateTime generationTime) {
        super(templates, id2DataMap);
        this.generationTime = generationTime;
    }

    /**
     * Performs the actual report compilation for the supplied (non-null) LocalizedReportIdentifier,
     * returning the complete Report.
     *
     * @param nonNullIdentifier The non-null LocalizedReportIdentifier used to start the report compilation.
     * @param templates         The report templates related to the given report identifier.
     * @return The generated report.
     */
    @Override
    protected DummyReport doCompileReport(final LocalizedReportIdentifier nonNullIdentifier,
                                          final SortedMap<Integer, String> templates) {

        return new DummyReport(nonNullIdentifier, generationTime, templates);
    }
}
