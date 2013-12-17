/*
 * #%L
 * cobertura-reporting-spi-markup
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
package net.sourceforge.cobertura.reporting.spi.markup;

import net.sourceforge.cobertura.reporting.api.AbstractReport;
import net.sourceforge.cobertura.reporting.api.LocalizedReportIdentifier;
import org.joda.time.DateTime;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class AbstractXmlReport extends AbstractReport<String> {

    // Internal state
    private Object xmlReportStructure;

    /**
     * Compound constructor creating an AbstractReport wrapping the supplied parameters.
     *
     * @param reportIdentifier    The localized Report identifier for this AbstractReport.
     * @param generationTimestamp The timestamp of generation for this AbstractReport.
     */
    protected AbstractXmlReport(final LocalizedReportIdentifier reportIdentifier,
                                final DateTime generationTimestamp,
                                final Object jaxbAnnotatedObject) {
        super(reportIdentifier, generationTimestamp);


    }

    /**
     * Retrieves the Report result, if applicable.
     *
     * @return the Report result, or {@code null} if no result was available.
     */
    @Override
    public String getResult() {
        return null;
    }
}
