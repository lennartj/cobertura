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
package net.sourceforge.cobertura.reporting.api.export;

import org.apache.commons.lang3.Validate;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Default ExporterRegistry implementation with a completely in-memory implementation.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid, jGuru Europe AB</a>
 */
public class DefaultExporterRegistry implements ExporterRegistry {

    // Internal state
    private SortedMap<String, ReportExporter> format2ExporterMap;

    /**
     * Creates a new DefaultExporterRegistry without any registered ReportExporter instances.
     */
    public DefaultExporterRegistry() {
        this(new TreeSet<ReportExporter>());
    }

    /**
     * Creates a new DefaultExporterRegistry holding the supplied ReportExporter instances.
     *
     * @param reportExporters The initial internal state for this DefaultExporterRegistry.
     */
    public DefaultExporterRegistry(final Set<ReportExporter> reportExporters) {

        // Check sanity
        Validate.notNull(reportExporters, "Cannot handle null reportExporters argument.");

        // Assign internal state
        format2ExporterMap = new TreeMap<String, ReportExporter>();
        for(ReportExporter current : reportExporters) {
            format2ExporterMap.put(current.getExportFormat(), current);
        }
    }

    /**
     * {@code}
     */
    @Override
    public void add(final ReportExporter exporter, final boolean overwriteExistingExporters)
            throws IllegalArgumentException {

        // Check sanity
        Validate.notNull(exporter, "Cannot handle null exporter argument.");

        if(format2ExporterMap.keySet().contains(exporter.getExportFormat()) && !overwriteExistingExporters) {
            throw new IllegalArgumentException("Could not add ReportExporter of type ["
                    + exporter.getClass().getName() + "] since it conflicts with an existing ReportExporter for "
                    + "format [" + exporter.getExportFormat() + "]");
        }

        // All done.
        format2ExporterMap.put(exporter.getExportFormat(), exporter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReportExporter getReportExporter(final String format) throws IllegalArgumentException {

        // Check sanity
        Validate.notEmpty(format, "Cannot handle null or empty format argument.");

        final ReportExporter toReturn = format2ExporterMap.get(format);
        if (toReturn == null) {
            throw new IllegalArgumentException("No ReportExporter supports format [" + format
                    + "]. Supported formats: " + format2ExporterMap.keySet());
        }

        // All done.
        return toReturn;
    }

    /**
     * Retrieves an Iterator over the formats supported by all ReportExporter instances
     * found in this DefaultExporterRegistry.
     *
     * @return an Iterator over the formats supported by all ReportExporter instances
     * found in this DefaultExporterRegistry.
     */
    @Override
    public Iterator<String> iterator() {
        return format2ExporterMap.keySet().iterator();
    }
}
