package net.thucydides.core.jpa;

import org.joda.time.DateTime;

import javax.persistence.jpa21.AttributeConverter;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: rahul
 * Date: 8/23/12
 * Time: 8:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class StandardJPADateTimeConverter implements AttributeConverter<DateTime, Timestamp> {


    @Override
    public Timestamp convertToDatabaseColumn(DateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return new Timestamp(dateTime.getMillis());

    }

    @Override
    public DateTime convertToEntityAttribute(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }

        return new DateTime(timestamp);
    }
}
