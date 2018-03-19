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
* 


##### LiveData


##### Room
