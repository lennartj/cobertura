package net.sourceforge.cobertura.reporting.api.export;

import net.sourceforge.cobertura.reporting.api.Report;

/**
 * Specification for how to export/write a Report to a given target file or directory.
 * The export could be in the form of one or a set of Files.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid, jGuru Europe AB</a>
 */
public interface ReportExporter {

    /**
     * Retrieves the format understood by this ReportExporter instance. In this context,
     * a format specification string must be unique for each type of format.
     *
     * @return the format understood by this ReportExporter.
     */
    String getExportFormat();

    /**
     * Exports the given Report using the supplied format to the given target directory or file.
     *
     * @param toExport The Report to export.
     * @param target   The target resource (i.e. directory or file path) to which this Report
     *                 should be exported.
     * @throws IllegalArgumentException If the supplied target was invalid for exporting the report.
     */
    void export(Report toExport, String target) throws IllegalArgumentException;
}
