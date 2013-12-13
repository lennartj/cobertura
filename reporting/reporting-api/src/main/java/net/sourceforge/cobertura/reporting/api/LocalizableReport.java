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
