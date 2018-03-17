package tk.hongbo.zlifecycle;

import android.app.Application;
import android.arch.persistence.room.Room;

import tk.hongbo.zlifecycle.dao.AppDatabase;

/**
 * Created by HONGBO on 2018/3/17 19:11.
 */
public class AppApplication extends Application {

    private static final String DB_NAME = "zLifecycle";
    private AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDb() {
        if (db == null) {
            //创建数据库
            db = Room.databaseBuilder(this.getApplicationContext(), AppDatabase.class, DB_NAME).build();
        }
        return db;
    }
}
