package tk.hongbo.zlifecycle;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tk.hongbo.zlifecycle.dao.AppDatabase;
import tk.hongbo.zlifecycle.dao.entity.User;
import tk.hongbo.zlifecycle.tools.LogHelper;
import tk.hongbo.zlifecycle.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //添加日志监听
        getLifecycle().addObserver(new LogHelper());

        //创建数据库
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "zLifecycle").build();

        //Insert
        insertData();

        //ViewModel
        UserViewModel model = ViewModelProviders.of(this).get(UserViewModel.class);
        model.getUsers().observe(this, users -> {
            //Update the UI
        });
    }

    private void insertData() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                User user = new User();
                user.firstName = "HONGBO";
                user.lastName = "ZHAO";
                db.userDao().insertAll(user);
                return null;
            }
        }.execute();
    }
}
