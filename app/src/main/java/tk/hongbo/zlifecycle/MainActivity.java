package tk.hongbo.zlifecycle;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import tk.hongbo.zlifecycle.tools.LogHelper;
import tk.hongbo.zlifecycle.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    TextView msg;

    UserViewModel model;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init
        msg = findViewById(R.id.msg);

        //添加日志监听
        getLifecycle().addObserver(new LogHelper());

        //ViewModel
        model = ViewModelProviders.of(this).get(UserViewModel.class);

        model.getUsers().observe(this, observer -> {
            msg.setText(""); //清空
            observer.forEach(user -> {
                String oldStr = msg.getText().toString();
                msg.setText(oldStr + "\r\n" + user.uid + "," + user.firstName + " " + user.lastName);
            });
        });
    }

    public void addData(View view) {
        model.addData();
    }

    public void deleteData(View view){
        model.deletaData();
    }
}
