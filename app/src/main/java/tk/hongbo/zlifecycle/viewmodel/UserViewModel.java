package tk.hongbo.zlifecycle.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import tk.hongbo.zlifecycle.dao.entity.User;

/**
 * Created by HONGBO on 2018/3/15 16:48.
 */
public class UserViewModel extends AndroidViewModel {

    LiveData<List<User>> users;

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<User>> getUsers() {
        if (users == null) {
            users = new MutableLiveData<>();
            loadUsers();
        }
        return users;
    }

    private void loadUsers(){

    }
}
