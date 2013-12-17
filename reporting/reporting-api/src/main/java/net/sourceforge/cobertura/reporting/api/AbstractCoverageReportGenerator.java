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

import net.sourceforge.cobertura.metrics.model.coverage.CoverageRecord;
import org.apache.commons.lang3.Validate;

import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Abstract implementation of a ReportGenerator using CoverageRecords for data.
 * The intention is to use all scopes relevant to the Report as identifiers, i.e:
 * Classes, Packages and Projects.
 *
 * @param <T> The concrete type of Template used by this AbstractCoverageReportGenerator.
 * @param <R> The concrete type of Report produced by this AbstractCoverageReportGenerator.
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid, jGuru Europe AB</a>
 */
public abstract class AbstractCoverageReportGenerator<R extends Report, T>
        extends AbstractReportGenerator<SortedSet<CoverageRecord>, T, R> {

    /**
     * Default constructor creating empty internal state holders.
     */
    protected AbstractCoverageReportGenerator() {
    }

    /**
     * Compound constructor creating an AbstractReportGenerator wrapping the supplied objects.
     *
     * @param dataMap      A map relating scope identifiers to data used to calculate a report. Cannot be null.
     * @param templatesMap A map relating LocalizedReportIdentifier instances to template data. Cannot be null.
     */
    protected AbstractCoverageReportGenerator(final SortedMap<String, SortedSet<CoverageRecord>> dataMap,
                                              final SortedMap<LocalizedReportIdentifier, T> templatesMap) {
        super(dataMap, templatesMap);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void internalAddData(final String id,
                                   final SortedSet<CoverageRecord> nonNullData,
                                   final SortedMap<String, SortedSet<CoverageRecord>> dataMap) {

        SortedSet<CoverageRecord> coverageRecords = dataMap.get(id);
        if(coverageRecords == null) {
            coverageRecords = new TreeSet<CoverageRecord>();
            dataMap.put(id, coverageRecords);
        }

        // Add the CoverageRecords to the innerMap, or append the hitcount to a matching CoverageRecord
        outer: for (CoverageRecord current : nonNullData) {
            for(CoverageRecord currentExistingCoverageRecord : coverageRecords) {
                if(current.getLocation().equals(currentExistingCoverageRecord.getLocation())) {
                    currentExistingCoverageRecord.addHits(current.getHitCount());
                    continue outer;
                }
            }

            // This record had a location not already registered.
            // Add it to the dataMap.
            coverageRecords.add(current);
        }
    }

    /**
     * Convenience method to add a single CoverageRecord to the supplied id.
     *
     * @param id     The identifier of this data, such as class or package name.
     * @param record The CoverageRecord to add to this
     */
    public final void addData(final String id, final CoverageRecord record) {

        // Check sanity
        Validate.notNull(record, "Cannot handle null record argument.");

        // Wrap and delegate
        final TreeSet<CoverageRecord> wrapper = new TreeSet<CoverageRecord>();
        wrapper.add(record);
        this.addData(id, wrapper);
    }
}
