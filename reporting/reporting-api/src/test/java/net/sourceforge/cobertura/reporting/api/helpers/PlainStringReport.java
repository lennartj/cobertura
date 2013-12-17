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

import net.sourceforge.cobertura.reporting.api.AbstractReport;
import net.sourceforge.cobertura.reporting.api.LocalizedReportIdentifier;
import org.joda.time.DateTime;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid, jGuru Europe AB</a>
 */
public class PlainStringReport extends AbstractReport<String> {

    // Internal state
    private String result;

    public PlainStringReport(final LocalizedReportIdentifier reportIdentifier,
                             final DateTime generationTimestamp,
                             final String result) {

        super(reportIdentifier, generationTimestamp);
        this.result = result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResult() {
        return result;
    }
}
