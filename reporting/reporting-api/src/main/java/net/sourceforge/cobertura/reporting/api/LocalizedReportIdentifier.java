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

import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.Validate;

import java.io.Serializable;
import java.util.Locale;

/**
 * Simple wrapper for a report identifier sporting localization awareness and
 * default sortability.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid, jGuru Europe AB</a>
 */
public class LocalizedReportIdentifier implements Serializable, Comparable<LocalizedReportIdentifier> {

    private static final long serialVersionUID = 0xC0BE201;

    /**
     * The separator between different parts of the LocalizedReportIdentifier's data.
     */
    public static final String SEPARATOR = "::";

    // Internal state
    private String id;
    private Locale locale;
    private int sequence;

    /**
     * Convenience constructor creating a LocalizedReportIdentifier wrapping the supplied data
     * and using 0 for sequence.
     *
     * @param id     A unique identifier string, such as "classCoverage" or "packageBranchCoverage".
     *               Cannot be null or empty.
     * @param locale The locale of this LocalizedReportIdentifier. Cannot be null.
     */
    public LocalizedReportIdentifier(final String id, final Locale locale) {
        this(id, locale, 0);
    }

    /**
     * Compound constructor creating a LocalizedReportIdentifier wrapping the supplied data.
     *
     * @param id       A unique identifier string, such as "classCoverage" or "packageBranchCoverage".
     *                 Cannot be null or empty.
     * @param locale   The locale of this LocalizedReportIdentifier. Cannot be null.
     * @param sequence The sequence of this LocalizedReportIdentifier, which must be a positive number
     *                 (starting with 0).
     */
    public LocalizedReportIdentifier(final String id, final Locale locale, final int sequence) {

        // Check sanity
        Validate.notEmpty(id, "Cannot handle null or empty id argument.");
        Validate.notNull(locale, "Cannot handle null locale argument.");
        Validate.isTrue(sequence >= 0, "Cannot handle negative sequence argument.");

        // Assign internal state
        this.id = id;
        this.locale = locale;
        this.sequence = sequence;
    }

    /**
     * @return A unique identifier string, such as "classCoverage" or "packageBranchCoverage".
     * Never null or empty.
     */
    public String getId() {
        return id;
    }

    /**
     * @return The locale of this LocalizedReportIdentifier. Never null.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * @return The sequence of this LocalizedReportIdentifier, which is a positive number (starting with 0).
     */
    public int getSequence() {
        return sequence;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[" + getId() + SEPARATOR + getLocale().toString() + SEPARATOR + getSequence() + "]";
    }

    /**
     * Parses the supplied string to a LocalizedReportIdentifier, given that the format of the
     * supplied string is compliant with {@code "[" + getId() + SEPARATOR + getLocale().toString()
     * + SEPARATOR + getSequence() + "]"} or {@code getId() + SEPARATOR + getLocale().toString()
     * + SEPARATOR + getSequence()}.
     *
     * @param toParse The non-empty string to parse into a LocalizedReportIdentifier.
     * @return The LocalizedReportIdentifier stemming from the supplied toParse string.
     */
    public static LocalizedReportIdentifier parse(final String toParse) {

        // Check sanity
        Validate.notEmpty(toParse, "Cannot handle null or empty toParse argument.");

        // Find the separators
        int beginIndex = toParse.charAt(0) == '[' ? 1 : 0;
        int firstSeparatorIndex = toParse.indexOf(SEPARATOR);
        int lastSeparatorIndex = toParse.lastIndexOf(SEPARATOR);
        int endIndex = toParse.charAt(toParse.length() - 1) == ']' ? toParse.length() - 1 : toParse.length();

        // Check sanity
        String msg = "Incorrect toParse string structure. Required format: [id" + SEPARATOR + "locale"
                + SEPARATOR + "sequence]. Got: ";
        Validate.isTrue(firstSeparatorIndex != -1, msg + toParse);
        Validate.isTrue(lastSeparatorIndex != -1, msg + toParse);
        Validate.isTrue(firstSeparatorIndex != lastSeparatorIndex, msg + toParse);

        // Chop up the parts
        String id = toParse.substring(beginIndex, firstSeparatorIndex);
        String localeString = toParse.substring(firstSeparatorIndex + SEPARATOR.length(), lastSeparatorIndex);
        String sequence = toParse.substring(lastSeparatorIndex + SEPARATOR.length(), endIndex);

        Validate.notEmpty(id, msg + toParse);
        Validate.notEmpty(localeString, msg + toParse);
        Validate.notEmpty(sequence, msg + toParse);

        // All done.
        return new LocalizedReportIdentifier(id, LocaleUtils.toLocale(localeString), Integer.parseInt(sequence));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final LocalizedReportIdentifier that) {

        // Check sanity
        if (that == null) {
            return Integer.MIN_VALUE;
        }
        if (that == this) {
            return 0;
        }

        // Simply compare the strings here.
        return toString().compareTo(that.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {

        if (obj == null || !(obj instanceof LocalizedReportIdentifier)) {
            return false;
        }

        // All done.
        return obj == this || toString().equals(obj.toString());
    }
}
