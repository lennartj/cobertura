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
