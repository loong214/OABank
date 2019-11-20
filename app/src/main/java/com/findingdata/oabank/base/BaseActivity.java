package com.findingdata.oabank.base;

import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.findingdata.oabank.R;
import com.findingdata.oabank.receiver.NetBroadcastReceiver;
import com.findingdata.oabank.utils.ExitAppUtils;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.NetworkUtils;
import com.findingdata.oabank.utils.StatusBarUtil;

import org.xutils.x;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by zengx on 2019/11/17.
 * Describe: Activity基础类
 */
public class BaseActivity extends AppCompatActivity implements NetBroadcastReceiver.NetEvent {
    public NetBroadcastReceiver netBroadcastReceiver;
    public static NetBroadcastReceiver.NetEvent event;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        event=this;
        ExitAppUtils.getInstance().addActivity(this);
        x.view().inject(this);
        setStatusBar();
        setOrientation();
        initReceiver();
    }

    //设置状态栏颜色
    protected void setStatusBar() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }

    //设置方向
    protected void setOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    //注册网络状态检测广播服务
    private void initReceiver(){
        //实例化IntentFilter对象
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netBroadcastReceiver = new NetBroadcastReceiver();
        registerReceiver(netBroadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netBroadcastReceiver);
        ExitAppUtils.getInstance().delActivity(this);
    }

    @Override
    public void onNetChange(int netMobile) {
        LogUtils.d(NetworkUtils.isNetConnect(netMobile));
    }
}
