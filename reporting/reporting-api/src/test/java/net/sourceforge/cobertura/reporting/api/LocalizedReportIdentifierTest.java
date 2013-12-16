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

import org.junit.Assert;
import org.junit.Test;
import sun.security.jgss.krb5.Krb5InitCredential;

import java.util.Locale;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid, jGuru Europe AB</a>
 */
public class LocalizedReportIdentifierTest {

    // Shared state
    private String id = "someIdentifier";
    private Locale swedish = new Locale("sw", "se"); // sv_SE

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnEmptyIdentifier() {

        // Act & Assert
        new LocalizedReportIdentifier("", swedish);
    }

    @Test(expected = NullPointerException.class)
    public void validateExceptionOnNullLocale() {

        // Act & Assert
        new LocalizedReportIdentifier(id, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNegativeSequence() {

        // Act & Assert
        new LocalizedReportIdentifier(id, swedish, -42);
    }

    @Test
    public void validateStringForm() {

        // Assemble
        final LocalizedReportIdentifier unitUnderTest = new LocalizedReportIdentifier(id, swedish);

        // Act
        final String result = unitUnderTest.toString();
        final LocalizedReportIdentifier parsed = LocalizedReportIdentifier.parse(result);

        // Assert
        Assert.assertEquals("[someIdentifier::sw_SE::0]", result);
        Assert.assertEquals(unitUnderTest, parsed);
    }

    @Test
    public void validateComparisonAndEquality() {

        // Assemble
        final LocalizedReportIdentifier unitUnderTest1 = new LocalizedReportIdentifier(id, swedish);
        final String stringForm = "[someIdentifier::sw_SE::0]";

        // Act
        final LocalizedReportIdentifier unitUnderTest2 = LocalizedReportIdentifier.parse(stringForm);

        // Assert
        Assert.assertEquals(unitUnderTest1, unitUnderTest2);
        Assert.assertNotSame(unitUnderTest1, unitUnderTest2);
        Assert.assertEquals(0, unitUnderTest1.compareTo(unitUnderTest2));
        Assert.assertEquals(unitUnderTest1.hashCode(), unitUnderTest2.hashCode());
    }
}
