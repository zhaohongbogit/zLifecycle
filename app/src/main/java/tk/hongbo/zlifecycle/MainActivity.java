package tk.hongbo.zlifecycle;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;

import tk.hongbo.zlifecycle.tools.ClassTest;
import tk.hongbo.zlifecycle.tools.LogHelper;
import tk.hongbo.zlifecycle.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    TextView msg;
    NestedScrollView nestedScrollView;

    UserViewModel model;

    private String testMsg = "aaa";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init
        msg = findViewById(R.id.msg);
        nestedScrollView = findViewById(R.id.nestedScrollView);

        //添加日志监听
        getLifecycle().addObserver(new LogHelper());

        //ViewModel
        model = ViewModelProviders.of(this).get(UserViewModel.class);

        model.getUsers().observe(this, observer -> {
            msg.setText(""); //清空
            observer.forEach(user -> {
                String oldStr = msg.getText().toString();
                msg.setText(oldStr + "\r\n" + user.uid + "," + user.firstName + " " + user.lastName);
                nestedScrollView.scrollBy(0, nestedScrollView.getScrollY());
            });
        });
    }

    public void addData(View view) {
        try {
            ClassTest classTest = (ClassTest) Class.forName(ClassTest.class.getName()).newInstance();
            Field msgField = classTest.getClass().getDeclaredField("msg");
            msgField.setAccessible(true);
            String msg = (String) msgField.get(classTest);
            model.addData(testMsg + "-" + msg);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void deleteData(View view) {
        model.deletaData();
    }

    public void changeBuildMsg(View view) {
        try {
            for (Class cls = this.getClass(); cls != Object.class; cls = cls.getSuperclass()) {
                if (cls == MainActivity.class) {
                    Field field = cls.getDeclaredField("testMsg");
                    field.setAccessible(true);
                    field.set(this, "bbb-" + Math.random());
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
