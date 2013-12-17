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
import net.sourceforge.cobertura.reporting.api.helpers.PlainStringTemplateReportGenerator;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class T_AbstractStringTemplateReportGeneratorTest {

    // Shared state
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
    }

    @Test(expected = NullPointerException.class)
    public void validateExceptionOnNullTemplatesMap() {

        // Act & Assert
        new PlainStringTemplateReportGenerator(null, id2DataMap, dateTime);
    }

    @Test(expected = NullPointerException.class)
    public void validateExceptionOnNullId2DataMap() {

        // Act & Assert
        new PlainStringTemplateReportGenerator(templates, null, dateTime);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNoTemplatesForReportIdentifierFound() {

        // Assemble
        final PlainStringTemplateReportGenerator unitUnderTest = new PlainStringTemplateReportGenerator(dateTime);
        final LocalizedReportIdentifier lri = new LocalizedReportIdentifier("classCoverage", swedish);

        // Act & Assert
        unitUnderTest.compileReport(lri);
    }

    @Test
    public void validateReportGeneration() {

        // Assemble
        final String id = "classCoverage";
        final String keyPrefix = "foo_";
        final String valuePrefix = "bar_";
        final PlainStringTemplateReportGenerator unitUnderTest = new PlainStringTemplateReportGenerator(dateTime);
        final LocalizedReportIdentifier lri = new LocalizedReportIdentifier("classCoverage", swedish);
        final String template = "Line 1: ${foo_1}\n"
                + "Line 2: ${foo_2}\n"
                + "Line 3: ${foo_3}\n"
                + "Line 4: ${foo_4}\n";
        final String expected = "Line 1: bar_1\n"
                + "Line 2: bar_2\n"
                + "Line 3: bar_3\n"
                + "Line 4: bar_4\n";

        for(int i = 0; i < 10; i++) {
            unitUnderTest.addData(id, keyPrefix + i, valuePrefix + i);
        }

        // Act
        unitUnderTest.addTemplate(lri, template);
        final PlainStringReport plainStringReport = unitUnderTest.compileReport(lri);

        // Assert
        Assert.assertEquals(expected, plainStringReport.getResult());
    }

    @Test
    public void validateStringRepresentation() {

        // Assemble
        final String id = "classCoverage";
        final String keyPrefix = "foo_";
        final String valuePrefix = "bar_";
        final PlainStringTemplateReportGenerator unitUnderTest = new PlainStringTemplateReportGenerator(dateTime);
        final LocalizedReportIdentifier lri = new LocalizedReportIdentifier("classCoverage", swedish);

        final String template = "Line 1: ${foo_1}\n"
                + "Line 2: ${foo_2}\n"
                + "Line 3: ${foo_3}\n"
                + "Line 4: ${foo_4}\n";

        final String expected =
                "AbstractStringTemplateReportGenerator held (1) templates for "
                        + "the following LocalizedReportIdentifiers:\n"
                        + " [classCoverage::sv_SE::0]\n"
                        + "The following tokens were known:\n"
                        + "{classCoverage={foo_0=bar_0, foo_1=bar_1, foo_2=bar_2, foo_3=bar_3, foo_4=bar_4, "
                        + "foo_5=bar_5, foo_6=bar_6, foo_7=bar_7, foo_8=bar_8, foo_9=bar_9}}";

        for(int i = 0; i < 10; i++) {
            unitUnderTest.addData(id, keyPrefix + i, valuePrefix + i);
        }

        // Act
        unitUnderTest.addTemplate(lri, template);
        final String result = unitUnderTest.toString();

        // Assert
        Assert.assertEquals(expected.trim(), result.trim());
    }
}
