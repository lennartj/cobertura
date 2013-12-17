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

import net.sourceforge.cobertura.reporting.api.helpers.PlainStringReport;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class T_AbstractReportTest {

    // Shared state
    private LocalizedReportIdentifier lri;
    private DateTime dateTime;
    private Locale swedish;
    private Map<LocalizedReportIdentifier, String> templates;
    private Map<String, Map<String, String>> id2DataMap;

    @Before
    public void setupSharedState() {

        dateTime = new DateTime(2013, 12, 17, 0, 7, 0, DateTimeZone.UTC);
        templates = new TreeMap<LocalizedReportIdentifier, String>();
        id2DataMap = new TreeMap<String, Map<String, String>>();
        swedish = new Locale("sv", "se");
        lri = new LocalizedReportIdentifier("classCoverage", swedish);
    }

    @Test(expected = NullPointerException.class)
    public void validateExceptionOnNullLocalizedReportIdentifier() {

        // Act & Assert
        new PlainStringReport(null, dateTime, "result");
    }

    @Test(expected = NullPointerException.class)
    public void validateExceptionOnNullGenerationTimestamp() {

        // Act & Assert
        new PlainStringReport(lri, null, "result");
    }
}
