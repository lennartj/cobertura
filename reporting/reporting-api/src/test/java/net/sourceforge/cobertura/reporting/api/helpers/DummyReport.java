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

import net.sourceforge.cobertura.reporting.api.LocalizedReportIdentifier;
import net.sourceforge.cobertura.reporting.api.Report;
import org.joda.time.DateTime;

import java.util.SortedMap;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid, jGuru Europe AB</a>
 */
public class DummyReport implements Report {

    public LocalizedReportIdentifier localizedReportIdentifier;
    public DateTime generationTimestamp;
    public SortedMap<Integer, String> templates;

    public DummyReport(final LocalizedReportIdentifier localizedReportIdentifier,
                       final DateTime generationTimestamp,
                       final SortedMap<Integer, String> templates) {
        this.localizedReportIdentifier = localizedReportIdentifier;
        this.generationTimestamp = generationTimestamp;
        this.templates = templates;
    }

    /**
     * Retrieves the identifier of this Report.
     *
     * @return the non-null identifier of this Report.
     */
    @Override
    public LocalizedReportIdentifier getId() {
        return localizedReportIdentifier;
    }

    /**
     * Acquires the timestamp when this Report was generated.
     *
     * @return the timestamp when this Report was generated.
     */
    @Override
    public DateTime getGenerationTimestamp() {
        return generationTimestamp;
    }

    public SortedMap<Integer, String> getTemplates() {
        return templates;
    }
}
