package com.findingdata.oabank.base;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.findingdata.oabank.R;
import com.findingdata.oabank.utils.ExitAppUtils;
import com.findingdata.oabank.utils.StatusBarUtil;

import org.xutils.x;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by zengx on 2019/11/17.
 * Describe: Activity基础类
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitAppUtils.getInstance().addActivity(this);
        x.view().inject(this);
        setStatusBar();
        setOrientation();
    }
    //设置状态栏颜色
    protected void setStatusBar(){
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }
    //设置方向
    protected void setOrientation(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExitAppUtils.getInstance().delActivity(this);
    }
}
