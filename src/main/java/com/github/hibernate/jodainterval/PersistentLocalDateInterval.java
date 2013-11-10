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
import com.github.serddmitry.jodainterval.LocalDateIntervalImpl;
import com.github.serddmitry.jodainterval.LocalDateIntervals;
import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.contrib.hibernate.PersistentLocalDate;

/**
 *
 * @author duncan
 */
public class PersistentLocalDateInterval implements CompositeUserType, Serializable {

    private static final String[] PROPERTY_NAMES = new String[] { "first", "last" };

    private static final Type[] TYPES = new Type[] { StandardBasicTypes.DATE, StandardBasicTypes.DATE };

    public Object assemble(Serializable cached, SessionImplementor session, Object owner) throws HibernateException {
        return cached;
    }

    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    public Serializable disassemble(Object value, SessionImplementor session) throws HibernateException {
        return (Serializable) value;
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        return x.equals(y);
    }

    public String[] getPropertyNames() {
        return PROPERTY_NAMES;
    }

    public Type[] getPropertyTypes() {
        return TYPES;
    }

    @Override
    public Object getPropertyValue(Object component, int property) throws HibernateException {
        LocalDateInterval interval = (LocalDateInterval) component;
        return (property == 0) ? interval.getFirst().toDateTimeAtStartOfDay().toDate() : interval.getLast().toDateTimeAtStartOfDay().toDate();
    }

    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    public boolean isMutable() {
        return false;
    }

    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner)
            throws HibernateException, SQLException {
        if (resultSet == null) {
            return null;
        }
        PersistentLocalDate pst = new PersistentLocalDate();
        LocalDate start = (LocalDate) pst.nullSafeGet(resultSet, names[0]);
        LocalDate end = (LocalDate) pst.nullSafeGet(resultSet, names[1]);
        if (start == null || end == null) {
            return null;
        }
        return LocalDateIntervals.includingLast(start, end);
    }

    public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            statement.setNull(index, StandardBasicTypes.DATE.sqlType());
            statement.setNull(index + 1, StandardBasicTypes.DATE.sqlType());
            return;
        }
        LocalDateInterval interval = (LocalDateInterval) value;
        statement.setDate(index, asDate(interval.getFirst()));
        statement.setDate(index + 1, asDate(interval.getLast()));
    }
   
    private Date asDate(LocalDate localDate) {
        return new Date(localDate.toDateTimeAtStartOfDay(DateTimeZone.UTC).getMillis());
    }

    public Object replace(Object original, Object target, SessionImplementor session, Object owner)
            throws HibernateException {
        return original;
    }

    @Override
    public Class returnedClass() {
        return LocalDateIntervalImpl.class;
    }

    public void setPropertyValue(Object component, int property, Object value) throws HibernateException {
        throw new UnsupportedOperationException("Immutable LocalInterval");
    }    
}
