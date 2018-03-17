package tk.hongbo.zlifecycle.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

import tk.hongbo.zlifecycle.AppApplication;
import tk.hongbo.zlifecycle.dao.AppDatabase;
import tk.hongbo.zlifecycle.dao.entity.User;

/**
 * Created by HONGBO on 2018/3/15 16:48.
 */
public class UserViewModel extends AndroidViewModel {

    AppDatabase db;
    MutableLiveData<List<User>> users;

    public UserViewModel(@NonNull Application application) {
        super(application);
        this.db = ((AppApplication) getApplication()).getDb();
    }

    public LiveData<List<User>> getUsers() {
        if (users == null) {
            users = new MutableLiveData<>();
            loadUsers();
        }
        return users;
    }

    private void loadUsers() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                users.postValue(db.userDao().getAll());
                return null;
            }
        }.execute();
    }

    public void addData() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                User user = new User();
                user.firstName = "HONGBO";
                user.lastName = "ZHAO";
                db.userDao().insertAll(user);
                loadUsers();
                return null;
            }
        }.execute();
    }

    public void deletaData() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                List<User> data = db.userDao().getAll();
                db.userDao().delete(data.get(data.size() - 1));
                loadUsers();
                return null;
            }
        }.execute();
    }
}
