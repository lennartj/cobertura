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
import org.joda.time.DateTime;

/**
 * Abstract Report implementation holding state accessors required by all AbstractReports.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public abstract class AbstractReport<T> implements Report<T> {

    // Internal state
    private LocalizedReportIdentifier reportIdentifier;
    private DateTime generationTimestamp;

    /**
     * Compound constructor creating an AbstractReport wrapping the supplied parameters.
     *
     * @param reportIdentifier    The localized Report identifier for this AbstractReport.
     * @param generationTimestamp The timestamp of generation for this AbstractReport.
     */
    protected AbstractReport(final LocalizedReportIdentifier reportIdentifier,
                             final DateTime generationTimestamp) {

        // Check sanity
        Validate.notNull(reportIdentifier, "Cannot handle null reportIdentifier argument.");
        Validate.notNull(generationTimestamp, "Cannot handle null generationTimestamp argument.");

        // Assign internal state
        this.reportIdentifier = reportIdentifier;
        this.generationTimestamp = generationTimestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final LocalizedReportIdentifier getId() {
        return reportIdentifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DateTime getGenerationTimestamp() {
        return generationTimestamp;
    }
}
