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

/**
 * Report generator specification, which indicates how to produce a Report originating
 * from a template document and (a set of) data structures. The report is conceptually
 * constructed by this ReportGenerator which replaces tokens within supplied templates.
 * <p/>
 * Typically, report generation is performed in three steps:
 * <p/>
 * <pre>
 *     <code>
 *         // Acquire the concrete ReportGenerator
 *         ReportGenerator&lt;Map, String, SimpleHtmlReport&gt; reportGen = ...
 *
 *         // Add templates to the ReportGenerator
 *         Locale swedish = new Locale("sv", "se");
 *         LocalizedReportIdentifier swedishClassCoverageId = new LocalizedReportIdentifier("classCoverage", swedish);
 *         reportGen.addTemplate(swedishClassCoverageId, swedishClassCoverageTemplate);
 *
 *         // Add some data to the ReportGenerator
 *         Map&lt;SourceLocation, Integer&gt; classACoverage = ...
 *         Map&lt;SourceLocation, Integer&gt; classBCoverage = ...
 *         reportGen.addData("classCoverage", classACoverage);
 *         reportGen.addData("classCoverage", classBCoverage);
 *
 *         // Now produce a Report
 *         SimpleHtmlReport swedishClassCoverageReport = reportGenerator.compileReport(swedishClassCoverageId);
 *     </code>
 * </pre>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid, jGuru Europe AB</a>
 */
public interface ReportGenerator<D, T, R extends Report> {

    /**
     * Adds data with the supplied identifier to this ReportGenerator.
     *
     * @param id   The identifier of this data, such as "classCoverage" or "packageBranchCoverage".
     *             This identifier classifies data to permit this ReportGenerator to create several
     *             different reports from the total data added.
     * @param data The report data to add for report generation.
     */
    void addData(String id, D data);

    /**
     * Adds a template for use within this ReportGenerator.
     *
     * @param identifier The identifier for the template to add.
     * @param template   The template itself.
     */
    void addTemplate(LocalizedReportIdentifier identifier, T template);

    /**
     * Generates a report for the supplied id and locale.
     *
     * @param identifier The identifier for the report to generate.
     * @return The generated report.
     * @throws java.lang.IllegalArgumentException if the combination of id and locale arguments could not be used
     *                                            to produce a report properly.
     */
    R compileReport(LocalizedReportIdentifier identifier) throws IllegalArgumentException;
}
