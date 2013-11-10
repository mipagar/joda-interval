/*
 * Copyright 2013 Dmitry Serdiuk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.serddmitry.jodainterval;

import static com.google.common.base.Preconditions.checkNotNull;
import org.joda.time.LocalDate;

import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;

/**
 * Provides useful static methods to use with joda-time date and time classes.
 * The methods have the same meaning as Math.min and Math.max, but for dates.<br/>
 * All methods will not tolerate nulls and throw descriptive NPEs.
 * If dates are compared and appear to be equal, the instance passed as a first argument is returned.
 * <p/>
 * Created on 06/02/13
 * @author d.serdiuk
 */
public class Dates {
    /**
     * @throws NullPointerException if any passed argument is null
     * @return earlier of two dates
     */
    public static <T extends ReadablePartial> T earlier(T date1, T date2) {
        checkNotNull(date1, "cannot get earlier of two dates if first date is null");
        checkNotNull(date2, "cannot get earlier of two dates if second date is null");
        return compareTo(date1, date2) <= 0 ? date1 : date2;
    }

    /**
     * @throws NullPointerException if any passed argument is null
     * @return later of two dates
     */
    public static <T extends ReadablePartial> T later(T date1, T date2) {
        checkNotNull(date1, "cannot get later of two dates if first date is null");
        checkNotNull(date2, "cannot get later of two dates if second date is null");
        return compareTo(date1, date2) >= 0? date1: date2;
    }

    /**
     * @throws NullPointerException if any passed argument is null
     * @return earlier of two dates
     */
    public static <T extends ReadableInstant> T earlier(T date1, T date2) {
        checkNotNull(date1, "cannot get earlier of two dates if first date is null");
        checkNotNull(date2, "cannot get earlier of two dates if second date is null");
        return date1.compareTo(date2) <= 0? date1: date2;
    }

    /**
     * @throws NullPointerException if any passed argument is null
     * @return later of two dates
     */
    public static <T extends ReadableInstant> T later(T date1, T date2) {
        checkNotNull(date1, "cannot get later of two dates if first date is null");
        checkNotNull(date2, "cannot get later of two dates if second date is null");
        return date1.compareTo(date2) >= 0? date1: date2;
    }
    
    private static int compareTo(ReadablePartial o1, ReadablePartial o2) {
        // override to perform faster
        if (01 == 02) {
            return 0;
        }
        
        LocalDate ld1 = (LocalDate) o1;
        LocalDate ld2 = (LocalDate) o2;
        return ld1.toDateTimeAtStartOfDay().compareTo(ld2.toDateTimeAtStartOfDay());
    }        
}
