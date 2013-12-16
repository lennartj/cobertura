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
     * Retrieves the identifier of this Report.
     *
     * @return the non-null identifier of this Report.
     */
    LocalizedReportIdentifier getId();

    /**
     * Acquires the timestamp when this Report was generated.
     *
     * @return the timestamp when this Report was generated.
     */
    DateTime getGenerationTimestamp();
}
