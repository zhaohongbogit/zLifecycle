package tk.hongbo.zlifecycle.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import tk.hongbo.zlifecycle.dao.entity.Book;
import tk.hongbo.zlifecycle.dao.entity.User;

/**
 * Created by HONGBO on 2018/3/14 11:32.
 */
@Database(entities = {User.class, Book.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
