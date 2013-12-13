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

import net.sourceforge.cobertura.reporting.api.export.DefaultExporterRegistry;
import net.sourceforge.cobertura.reporting.api.export.ExporterRegistry;
import net.sourceforge.cobertura.reporting.api.helpers.DebugLocalizableReport;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.jguru.nazgul.core.resource.api.LocalResources;
import se.jguru.nazgul.core.resource.impl.resourcebundle.ResourceBundleLocalResources;

import java.util.Locale;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid, jGuru Europe AB</a>
 */
public class T_AbstractLocalizableReportTest {

    // Shared state
    private Locale activeLocale;
    private LocalResources localizedResources;
    private DateTime generationTimestamp;
    private ExporterRegistry registry;

    @Before
    public void setupSharedState() {

        activeLocale = Locale.UK;
        localizedResources = new ResourceBundleLocalResources("testdata/i18n/trivialreport", activeLocale);
        generationTimestamp = new DateTime(2013, 12, 13, 15, 33, 0, DateTimeZone.UTC);
        registry = new DefaultExporterRegistry();

        // Check sanity
        Assert.assertEquals("a tad trivial [uk] report", localizedResources.getLocalized("name", "unknown"));
    }

    @Test(expected = NullPointerException.class)
    public void validateExceptionOnNullLocale() {

        // Act & Assert
        new DebugLocalizableReport(null, localizedResources, generationTimestamp, registry);
    }
}
