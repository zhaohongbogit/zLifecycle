# zLifecycle

### 练习android官方新架构控件Android Architecture Components使用
> android官方底层已经支持lifecycle相关，有必要顺应官方发展趋势进行项目改造。
让项目从结构上更易于理解、开发、维护、改造和稳定，此demo是为了说明套件相关简单用法，如果需要在
项目中使用，需要根据具体项目需要，加以改造升级。

##### Lifecycle
* 可以让普通类监听Acitivyt或者fragment的生命周期
* 改造一些关联生命周期类的插件，初始化和销毁不需要放在activity和fragment中，自身就可以维护
* 在activity或者fragment中增加生命周期观察者
```
 getLifecycle().addObserver(new LogHelper());
```
* 增加一个观察者只需要继承LifecycleObserver就可以
```
public class LogHelper implements LifecycleObserver {

    private static final String TAG = LogHelper.class.getSimpleName();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void connect() {
        Log.d(TAG, "LogHelper-connect");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void close() {
        Log.d(TAG, "LogHelper-close");
    }
}
```


##### ViewModel 
* 主要用于数据分离，关联UI交互的放在activity、fragment里面，非UI纯数据操作的放在viewModel里面。做好分离，减小activity和fragment的复杂度。
* ViewModel的引用
```
model = ViewModelProviders.of(this).get(UserViewModel.class);
```
* ViewModel的实例
```
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

    public void addData(String msg) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                User user = new User();
                user.firstName = "HONGBO";
                user.lastName = "ZHAO " + msg;
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
```


##### LiveData
* LiveData主要做数据观察，被LiveData加持的数据形成被观察者，使用数据的地方会主动收到数据改变通知。
* LiveData的使用
```
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

model.getUsers().observe(this, observer -> {
    msg.setText(""); //清空
    observer.forEach(user -> {
        String oldStr = msg.getText().toString();
        msg.setText(oldStr + "\r\n" + user.uid + "," + user.firstName + " " + user.lastName);
        nestedScrollView.scrollBy(0, nestedScrollView.getScrollY());
    });
});
```


##### Room
* Room主要做数据库映射，方便好用
* 使用方式
```
@Database(entities = {User.class, Book.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
```



