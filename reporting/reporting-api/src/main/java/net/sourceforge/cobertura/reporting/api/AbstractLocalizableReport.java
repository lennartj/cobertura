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

import net.sourceforge.cobertura.reporting.api.export.ExporterRegistry;
import net.sourceforge.cobertura.reporting.api.export.ReportExporter;
import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import se.jguru.nazgul.core.resource.api.LocalResources;

import java.util.Locale;

/**
 * Abstract implementation of the LocalizableReport interface sporting utilities for i18n.
 * Extend this class to provide specific Report implementations.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid, jGuru Europe AB</a>
 */
public abstract class AbstractLocalizableReport implements LocalizableReport {

    // Internal state
    private LocalResources localizedResources;
    private DateTime generationTimestamp;
    private Locale activeLocale;
    private final Object lock = new Object();
    private ExporterRegistry exporterRegistry;

    /**
     * Compound constructor creating an AbstractLocalizableReport wrapping the supplied data.
     *
     * @param activeLocale        The preferred Locale for this AbstractLocalizableReport.
     * @param localizedResources  The LocalResources used to acquire localized values for this Report.
     * @param generationTimestamp The timestamp when this report was generated.
     * @param exporterRegistry    The ExporterRegistry which holds the active ReportExporters for this Report.
     */
    protected AbstractLocalizableReport(final Locale activeLocale,
                                        final LocalResources localizedResources,
                                        final DateTime generationTimestamp,
                                        final ExporterRegistry exporterRegistry) {

        // Check sanity
        Validate.notNull(activeLocale, "Cannot handle null activeLocale argument.");
        Validate.notNull(localizedResources, "Cannot handle null localizedResources argument.");
        Validate.notNull(generationTimestamp, "Cannot handle null generationTimestamp argument.");
        Validate.notNull(exporterRegistry, "Cannot handle null exporterRegistry argument.");

        // Assign internal state
        this.localizedResources = localizedResources;
        this.exporterRegistry = exporterRegistry;
        this.generationTimestamp = generationTimestamp;
        setLocale(activeLocale);

        // Check sanity for data within the LocalResources instance.
        Validate.notEmpty(getName(), "Property 'name' must be defined within the LocalResources given.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLocale(final Locale preferredLocale) {

        // Check sanity
        Validate.notNull(preferredLocale, "Cannot handle null preferredLocale argument.");

        // Assign if possible.
        synchronized (lock) {
            if (!localizedResources.setDefaultLocale(preferredLocale)) {
                throw new IllegalStateException("Could not properly assign preferredLocale ["
                        + preferredLocale + "]. Nonexistent resource?");
            } else {
                this.activeLocale = preferredLocale;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Locale getLocale() {
        return activeLocale;
    }

    /**
     * Retrieves the name as the value with the key "name" within the internal LocalResources instance.
     * <p/>
     * {@inheritDoc}
     */
    @Override
    public final String getName() {
        return localizedResources.getLocalized("name", "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DateTime getGenerationTimestamp() {
        return generationTimestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ExporterRegistry getExporterRegistry() {
        return exporterRegistry;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void export(final String format, final String target) throws IllegalArgumentException {

        // Check sanity
        Validate.notEmpty(format, "Cannot handle null or empty format argument.");
        Validate.notEmpty(target, "Cannot handle null or empty target argument.");

        // Find the correct exporter
        ReportExporter requestedExporter = exporterRegistry.getReportExporter(format);

        // Calculate state, and export the Report
        calculateReportState();
        performExport(requestedExporter, target);
    }

    //
    // Private helpers
    //

    /**
     * Instructs this Report to calculate its internal state before being exported.
     * This should be implemented in subclasses to prepare for report exporting.
     */
    protected abstract void calculateReportState();

    /**
     * Default export method for this Report, where all arguments are non-null and non-empty.
     * Override in subclasses if an alternate implementation is desired.
     *
     * @param exporter The ReportExporter instance.
     * @param target   The target file or directory to which this Report should be exported.
     */
    protected void performExport(final ReportExporter exporter, final String target) {

        // Default implementation.
        exporter.export(this, target);
    }

    /**
     * @return The active LocalResources for this AbstractLocalizableReport.
     */
    protected final LocalResources getLocalizedResources() {
        return localizedResources;
    }
}
