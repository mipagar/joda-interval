/*
 * Copyright 2013 duncan.
 *
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
 */
package com.github.hibernate.jodainterval;

import com.github.serddmitry.jodainterval.LocalDateInterval;
import com.github.serddmitry.jodainterval.LocalDateIntervals;
import org.hibernate.usertype.ParameterizedType;
import org.jadira.usertype.dateandtime.joda.columnmapper.DateColumnLocalDateMapper;
import org.jadira.usertype.spi.shared.AbstractParameterizedMultiColumnUserType;
import org.jadira.usertype.spi.shared.ColumnMapper;
import org.jadira.usertype.spi.shared.IntegratorConfiguredType;
import org.joda.time.LocalDate;

/**
 *
 * @author duncan
 */
public class PersistentLocalDateInterval extends AbstractParameterizedMultiColumnUserType<LocalDateInterval> implements ParameterizedType, IntegratorConfiguredType {

    private static final String[] PROPERTY_NAMES = new String[] { "first", "last" };

//    private static final Type[] TYPES = new Type[] { StandardBasicTypes.DATE, StandardBasicTypes.DATE };

    private static final DateColumnLocalDateMapper[] COLUMN_MAPPERS = new DateColumnLocalDateMapper[] { new DateColumnLocalDateMapper(), new DateColumnLocalDateMapper() };


    public String[] getPropertyNames() {
        return PROPERTY_NAMES;
    }

//    @Override
//    public Object getPropertyValue(Object component, int property) throws HibernateException {
//        LocalDateInterval interval = (LocalDateInterval) component;
//        return (property == 0) ? interval.getFirst().toDateTimeAtStartOfDay().toDate() : interval.getLast().toDateTimeAtStartOfDay().toDate();
//    }

//    public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor session)
//            throws HibernateException, SQLException {
//        if (value == null) {
//            statement.setNull(index, StandardBasicTypes.DATE.sqlType());
//            statement.setNull(index + 1, StandardBasicTypes.DATE.sqlType());
//            return;
//        }
//        LocalDateInterval interval = (LocalDateInterval) value;
//        statement.setDate(index, asDate(interval.getFirst()));
//        statement.setDate(index + 1, asDate(interval.getLast()));
//    }

//    @Override
//    public Class returnedClass() {
//        return LocalDateIntervalImpl.class;
//    } 

    @Override
    protected ColumnMapper<?, ?>[] getColumnMappers() {
        return COLUMN_MAPPERS;
    }

    @Override
    protected LocalDateInterval fromConvertedColumns(Object[] convertedColumns) {
        LocalDate begin = (LocalDate) convertedColumns[0];
        LocalDate end = (LocalDate) convertedColumns[1];

        return LocalDateIntervals.includingLast(begin, end);
    }

    @Override
    protected Object[] toConvertedColumns(LocalDateInterval value) {
        return new Object[] { value.getFirst(), value.getLast() };
    }
}
