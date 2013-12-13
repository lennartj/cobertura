package net.sourceforge.cobertura.reporting.api.export;

import java.io.Serializable;

/**
 * Specification for a registry containing several ReportExporters.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid, jGuru Europe AB</a>
 */
public interface ExporterRegistry extends Serializable, Iterable<String> {

    /**
     * Retrieves the ReportExporter which can understand the supplied format.
     *
     * @param format The non-null format to which the retrieved ReportExporter can export reports.
     * @return The ReportExporter suited to export Reports on the supplied format.
     * @throws IllegalArgumentException if no ReportExporter was found to understand the given format.
     */
    ReportExporter getReportExporter(String format) throws IllegalArgumentException;

    /**
     * Adds the supplied exporter to this Registry.
     *
     * @param exporter                   The non-null ReportExporter to add.
     * @param overwriteExistingExporters if {@code true}, permit overwriting an existing ReportExporter for the same
     *                                   format as the given one.
     * @throws IllegalArgumentException if the given ReportExporter's format is already present within this
     *                                  ExporterRegistry and overwriteExistingExporters is false.
     */
    void add(ReportExporter exporter, boolean overwriteExistingExporters) throws IllegalArgumentException;
}
