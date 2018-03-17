package tk.hongbo.zlifecycle.tools;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

/**
 * Created by HONGBO on 2018/3/13 11:26.
 */
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
