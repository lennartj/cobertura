package net.sourceforge.cobertura.reporting.api.helpers;

import net.sourceforge.cobertura.reporting.api.AbstractLocalizableReport;
import net.sourceforge.cobertura.reporting.api.export.ExporterRegistry;
import org.joda.time.DateTime;
import se.jguru.nazgul.core.resource.api.LocalResources;

import java.util.Locale;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid, jGuru Europe AB</a>
 */
public class DebugLocalizableReport extends AbstractLocalizableReport {

    public DebugLocalizableReport(final Locale activeLocale,
                                  final LocalResources localizedResources,
                                  final DateTime generationTimestamp,
                                  final ExporterRegistry exporterRegistry) {

        super(activeLocale, localizedResources, generationTimestamp, exporterRegistry);
    }

    /**
     * Instructs this Report to calculate its internal state before being exported.
     * This should be implemented in subclasses to prepare for report exporting.
     */
    @Override
    protected void calculateReportState() {

    }
}
