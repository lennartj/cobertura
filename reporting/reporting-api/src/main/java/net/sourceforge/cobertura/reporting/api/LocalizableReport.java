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

import java.util.Locale;

/**
 * Specification for a report which can use i18n features to achieve a localized report.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid, jGuru Europe AB</a>
 */
public interface LocalizableReport extends Report {

    /**
     * Assigns the locale for use within this Report.
     * All properties which support l10n should use the preferredLocale to format dates,
     * currencies and other Locale-aware properties.
     *
     * @param preferredLocale The preferred locale for use within this Report.
     */
    void setLocale(Locale preferredLocale);

    /**
     * Retrieves the currently active Locale for this Report.
     *
     * @return The currently active locale for this Report.
     */
    Locale getLocale();
}
