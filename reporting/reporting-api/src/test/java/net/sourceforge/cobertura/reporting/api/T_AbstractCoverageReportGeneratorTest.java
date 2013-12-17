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

import net.sourceforge.cobertura.metrics.model.coverage.CoverageRecord;
import net.sourceforge.cobertura.metrics.model.location.SourceLocation;
import net.sourceforge.cobertura.reporting.api.helpers.DummyCoverageReportGenerator;
import net.sourceforge.cobertura.reporting.api.helpers.PlainStringReport;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class T_AbstractCoverageReportGeneratorTest {

    // Shared state
    private SortedMap<String, SortedSet<CoverageRecord>> dataMap;
    private SortedMap<LocalizedReportIdentifier, String> templatesMap;
    private DateTime dateTime;
    private Locale swedish;

    private String packageName;
    private String className;
    private String methodSignature;
    private int lineNumber;
    private int branchSegment;

    @Before
    public void setupSharedState() {

        dateTime = new DateTime(2013, 12, 17, 0, 7, 0, DateTimeZone.UTC);
        templatesMap = new TreeMap<LocalizedReportIdentifier, String>();
        dataMap = new TreeMap<String, SortedSet<CoverageRecord>>();
        swedish = new Locale("sv", "se");

        packageName = LocalizedReportIdentifier.class.getPackage().getName();
        className = LocalizedReportIdentifier.class.getSimpleName();
        methodSignature = "toString()";
        lineNumber = 109;
        branchSegment = 0;
    }

    @Test(expected = NullPointerException.class)
    public void validateExceptionOnNullDataMap() {

        // Act & Assert
        new DummyCoverageReportGenerator(null, templatesMap, dateTime);
    }

    @Test(expected = NullPointerException.class)
    public void validateExceptionOnNullTemplatesMap() {

        // Act & Assert
        new DummyCoverageReportGenerator(dataMap, null, dateTime);
    }

    @Test
    public void validateAddingData() {

        // Assemble
        final LocalizedReportIdentifier testId = new LocalizedReportIdentifier("testId", swedish);
        final DummyCoverageReportGenerator unitUnderTest = new DummyCoverageReportGenerator(dateTime);
        final SortedSet<CoverageRecord> coverageRecords = new TreeSet<CoverageRecord>();

        for(int i = 0; i < 10; i++) {
            SourceLocation current = new SourceLocation(packageName, className, methodSignature, lineNumber + i, 0);
            coverageRecords.add(new CoverageRecord(current, (i%4) * 2));
        }

        // Act
        for(CoverageRecord current : coverageRecords) {
            unitUnderTest.addData("testId", current);
        }

        final PlainStringReport result = unitUnderTest.compileReport(testId);

        // Assert
        Assert.assertEquals(dateTime, result.getGenerationTimestamp());
        Assert.assertEquals(testId, result.getId());
        Assert.assertTrue(result.getResult().contains(
                "net.sourceforge.cobertura.reporting.api.LocalizedReportIdentifier::toString(),line:109"));
    }

    @Test
    public void validateAddingCoverageWithSameSourceLocationYieldsCorrectInternalState() {

        // Assemble
        final List<Integer> hits = Arrays.asList(2, 3, 4, 5, 6);
        int total = 0;
        for(Integer current : hits) {
            total += current;
        }

        final String expected = "{}-->{testId=[net.sourceforge.cobertura.reporting.api."
                + "LocalizedReportIdentifier::toString(),line:109,segment:0: " + total + "]}";
        final LocalizedReportIdentifier testId = new LocalizedReportIdentifier("testId", swedish);
        final DummyCoverageReportGenerator unitUnderTest = new DummyCoverageReportGenerator(dateTime);

        final SourceLocation location = new SourceLocation(packageName, className, methodSignature, lineNumber, 0);

        // Act
        for(Integer current : hits) {
            unitUnderTest.addData("testId", new CoverageRecord(location, current));
        }

        final PlainStringReport result = unitUnderTest.compileReport(testId);

        // Assert
        Assert.assertEquals(expected, result.getResult());
    }
}
