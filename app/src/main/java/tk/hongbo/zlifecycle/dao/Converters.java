package tk.hongbo.zlifecycle.dao;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by HONGBO on 2018/3/14 11:50.
 */
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
