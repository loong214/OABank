package com.findingdata.oabank.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import androidx.annotation.Nullable;

import static com.findingdata.oabank.utils.Config.BASE_URL;

/**
 * Created by zengx on 2019/11/16.
 * Describe: 登录Activity
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.login_tv_base_url)
    private TextView login_tv_base_url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        login_tv_base_url.setText("当前HOST："+BASE_URL);
    }


    @Event({R.id.login_btn_login})
    private void eventOnClick(View v){
        switch (v.getId()){
            case R.id.login_btn_login:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
        }
    }
}
