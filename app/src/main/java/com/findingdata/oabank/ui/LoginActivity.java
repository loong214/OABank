package com.findingdata.oabank.ui;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.findingdata.oabank.R;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.entity.BaseEntity;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.NetworkUtils;
import com.findingdata.oabank.utils.SharedPreferencesManage;
import com.findingdata.oabank.utils.http.JsonParse;
import com.findingdata.oabank.utils.http.MyCallBack;
import com.findingdata.oabank.utils.http.XHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;

import static com.findingdata.oabank.utils.Config.BASE_URL;

/**
 * Created by zengx on 2019/11/16.
 * Describe: 登录Activity
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.login_et_tel)
    private EditText login_et_tel;
    @ViewInject(R.id.login_et_password)
    private EditText login_et_password;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Event({R.id.login_btn_submit})
    private void eventOnClick(View v){
        switch (v.getId()){
            case R.id.login_btn_submit:
                login();
                break;
        }
    }
    //登录
    private void login(){
        Map<String,Object> params=new HashMap<>();
        params.put("account",login_et_tel.getText().toString());
        params.put("password",login_et_password.getText().toString());
        XHttp.Post(BASE_URL+"api/v1/auth/login",params,new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                BaseEntity<String> entity= JsonParse.parse(result,String.class);
                if(entity.getCode()==200){
                    SharedPreferencesManage.setToken(entity.getData());
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,entity.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                Toast.makeText(LoginActivity.this,ex.getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {
                super.onFinished();
            }
        });
    }
}
