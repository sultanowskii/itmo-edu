package lib.date;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeConverter {
    public static Timestamp toDBTimestamp(ZonedDateTime zoneDateTime) {
        return zoneDateTime == null ? null : Timestamp.from(zoneDateTime.toInstant());
    }

    public static ZonedDateTime fromDBTimestamp(Timestamp sqlTimestamp) {
        return sqlTimestamp == null ? null : sqlTimestamp.toInstant().atZone(ZoneId.systemDefault());
    }
}
