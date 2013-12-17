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

import net.sourceforge.cobertura.metrics.model.coverage.CoverageRecord;
import net.sourceforge.cobertura.reporting.api.AbstractCoverageReportGenerator;
import net.sourceforge.cobertura.reporting.api.LocalizedReportIdentifier;
import org.joda.time.DateTime;

import java.util.SortedMap;
import java.util.SortedSet;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class DummyCoverageReportGenerator extends AbstractCoverageReportGenerator<PlainStringReport, String> {

    // Internal state
    private DateTime generationTime;

    /**
     * Default constructor creating empty internal state holders.
     */
    public DummyCoverageReportGenerator(final DateTime generationTime) {
        this.generationTime = generationTime;
    }

    /**
     * Compound constructor creating an AbstractReportGenerator wrapping the supplied objects.
     *
     * @param dataMap      A map relating scope identifiers to data used to calculate a report. Cannot be null.
     * @param templatesMap A map relating LocalizedReportIdentifier instances to template data. Cannot be null.
     */
    public DummyCoverageReportGenerator(final SortedMap<String, SortedSet<CoverageRecord>> dataMap,
                                        final SortedMap<LocalizedReportIdentifier, String> templatesMap,
                                        final DateTime generationTime) {
        super(dataMap, templatesMap);
        this.generationTime = generationTime;
    }

    /**
     * Performs the actual report compilation for the supplied (non-null) LocalizedReportIdentifier,
     * returning the complete Report.
     *
     * @param nonNullIdentifier The non-null LocalizedReportIdentifier used to start the report compilation.
     * @param templatesMap      The current map holding all templates.
     * @param dataMap           The current map relating ID to data objects.
     * @return The generated report.
     */
    @Override
    protected PlainStringReport internalCompileReport(final LocalizedReportIdentifier nonNullIdentifier,
                                                      final SortedMap<LocalizedReportIdentifier, String> templatesMap,
                                                      final SortedMap<String, SortedSet<CoverageRecord>> dataMap) {

        return new PlainStringReport(nonNullIdentifier, generationTime, "" + templatesMap + "-->" + dataMap);
    }
}
