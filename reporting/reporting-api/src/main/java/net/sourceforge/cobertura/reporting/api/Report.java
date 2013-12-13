package net.sourceforge.cobertura.reporting.api;

import net.sourceforge.cobertura.reporting.api.export.ExporterRegistry;
import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Specification for a generic Report in the context of Cobertura.
 * Details of Localization aspects of reporting are defined within subtypes.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid, jGuru Europe AB</a>
 */
public interface Report extends Serializable {

    /**
     * Retrieves the human-readable name of this Report.
     *
     * @return the non-null name of this Report.
     */
    String getName();

    /**
     * Acquires the timestamp when this Report was generated.
     *
     * @return the timestamp when this Report was generated.
     */
    DateTime getGenerationTimestamp();

    /**
     * Retrieves the active ExporterRegistry in use for this Report.
     *
     * @return the active ExporterRegistry in use for this Report.
     */
    ExporterRegistry getExporterRegistry();

    /**
     * Exports this Report using the supplied format to the given target directory or file.
     *
     * @param format The desired reporting export format, which must be one of the formats
     *               retrieved from the {@code getAvailableFormats() } method.
     * @param target The target resource (i.e. directory or file path) to which this Report
     *               should be exported.
     * @throws IllegalArgumentException If the format given was not available for this ReportExporter,
     *                                  or if the supplied target was invalid for exporting the report.
     */
    void export(String format, String target) throws IllegalArgumentException;
}
