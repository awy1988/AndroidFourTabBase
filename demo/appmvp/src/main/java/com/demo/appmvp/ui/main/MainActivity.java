package com.demo.appmvp.ui.main;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.demo.appmvp.R;
import com.demo.appmvp.data.UserRepository;
import com.demo.appmvp.data.db.User;
import com.demo.appmvp.di.component.DaggerActivityComponent;
import com.demo.appmvp.di.module.DatabaseModule;
import com.demo.appmvp.ui.App;
import com.demo.corelib.model.common.LinksModel;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.corelib.util.SPUtils;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    UserRepository mUserRepository;

    TextView mTvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DaggerActivityComponent.builder()
            .databaseModule(new DatabaseModule(App.getContext()))
            .build()
            .inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvUserName = findViewById(R.id.tv_user_name);

        mUserRepository.getUserInfo(SPUtils.getAccessToken(), new RequestCallbackListener<User>() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onCompleted(User data, LinksModel links) {
                mTvUserName.setText(data.name);
            }

            @Override
            public void onEndedWithError(String errorInfo) {
                Toast.makeText(MainActivity.this, errorInfo, Toast.LENGTH_SHORT).show();
            }
        });
    }




}
