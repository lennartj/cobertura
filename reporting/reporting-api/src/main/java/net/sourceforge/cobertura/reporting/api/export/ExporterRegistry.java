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
