package com.findingdata.oabank.base;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Message;
import android.view.Window;
import android.widget.Toast;

import com.findingdata.oabank.R;
import com.findingdata.oabank.entity.Transition;
import com.findingdata.oabank.receiver.NetBroadcastReceiver;
import com.findingdata.oabank.utils.AtyTransitionUtil;
import com.findingdata.oabank.utils.ExitAppUtils;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.NetworkUtils;
import com.findingdata.oabank.utils.StatusBarUtil;
import com.findingdata.oabank.utils.http.RequestParam;
import com.findingdata.oabank.weidgt.ProgressDialogView;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import static com.findingdata.oabank.FDApplication.activityTrans;
import static com.findingdata.oabank.base.BaseHandler.HTTP_REQUEST;

/**
 * Created by zengx on 2019/11/17.
 * Describe: Activity基础类
 */
public class BaseActivity extends FragmentActivity implements NetBroadcastReceiver.NetEvent {
    public NetBroadcastReceiver netBroadcastReceiver;
    public static NetBroadcastReceiver.NetEvent event;
    private ProgressDialogView progressDialogView = null;

    private boolean isFullScreen;
    private boolean showStatus;

    private static List<Callback.Cancelable> taskList;

    public boolean isNetConnect=true;
    protected static BaseHandler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        event=this;
        ExitAppUtils.getInstance().addActivity(this);

        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //垂直显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if(isFullScreen){
            if(showStatus){
                StatusBarUtil.setTranslucentStatus(this);
            }else{
                StatusBarUtil.fullScreen(this);
            }
        }else{
            StatusBarUtil.setStatusBarMode(this, true, R.color.view_background);
        }
        x.view().inject(this);

        initReceiver();
        taskList=new ArrayList<>();
        handler=new BaseHandler(this,taskList);
    }



    /**
     * 是否全屏
     * @return
     */
    protected void setFullScreen(boolean showStatus){
        this.isFullScreen=true;
        this.showStatus=showStatus;
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
        if (progressDialogView != null) {
            progressDialogView.stopLoad();
            progressDialogView = null;
        }
        for (int i = 0; i < taskList.size(); i++) {
            if(!taskList.get(i).isCancelled())
                taskList.get(i).cancel();
        }
        ExitAppUtils.getInstance().delActivity(this);
        if(isActivityTrans()){
            Class clazz=this.getClass();
            activityTrans.remove(clazz);
        }
    }

    @Override
    public void onNetChange(int netMobile) {
        isNetConnect=NetworkUtils.isNetConnect(netMobile);
        LogUtils.d(NetworkUtils.isNetConnect(netMobile));
    }

    /**
     * 发送一个请求
     * @param requestParam 请求体
     * @param showProgressDialog 是否转菊花
     */
    protected void sendRequest(RequestParam requestParam,boolean showProgressDialog){
        if(isNetConnect){
            if(showProgressDialog){
                startProgressDialog();
            }
            Message message=new Message();
            message.what=HTTP_REQUEST;
            Bundle bundle=new Bundle();
            bundle.putSerializable("request",requestParam);
            message.setData(bundle);
            handler.sendMessage(message);
        }else{
           showToast("没有网络，请前往网络设置检查");
        }
    }

    /**
     * 启动新的Activity 默认Trans-LeftIn
     * @param clazz
     */
    public void startActivity(Class clazz){
        startActivity(clazz,Transition.RightIn);
    }

    /**
     * 启动新的Activity
     * @param clazz
     * @param transition  转场动画
     */
    public void startActivity(Class clazz, Transition transition){
        activityTrans.put(clazz,transition);
        startActivity(new Intent(this, clazz));
        executeTransition(transition);
    }

    /**
     * 启动新的Activity
     * @param clazz
     * @param bundle
     */
    public void startActivity(Class clazz, Bundle bundle){
        startActivity(clazz,bundle,Transition.RightIn);
    }


    /**
     * 启动新的Activity
     * @param clazz
     * @param bundle
     * @param transition 转场动画
     */
    public void startActivity(Class clazz, Bundle bundle,Transition transition){
        activityTrans.put(clazz,transition);
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
        executeTransition(transition);
    }

    /**
     * 启动新的Activity
     * @param clazz
     * @param bundle
     * @param requestCode
     * @param transition 转场动画
     */
    public void startActivityForResult( Class clazz, Bundle bundle, int requestCode,Transition transition) {
        activityTrans.put(clazz,transition);
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
        executeTransition(transition);
    }

    @Override
    public void finish() {
        super.finish();
        if(isActivityTrans()){
            Class clazz=this.getClass();
            Transition currentTrans=getTrans(clazz);
            activityTrans.remove(clazz);
            executeTransition(getReverse(currentTrans));
        }
    }

    //执行转场
    private void executeTransition(Transition transition){
        switch (transition){
            case TopIn:
                AtyTransitionUtil.enterFromTop(this);
                break;
            case TopOut:
                AtyTransitionUtil.exitToTop(this);
                break;
            case LeftIn:
                AtyTransitionUtil.enterFromLeft(this);
                break;
            case LeftOut:
                AtyTransitionUtil.exitToLeft(this);
                break;
            case BottomIn:
                AtyTransitionUtil.enterFromBottom(this);
                break;
            case BottomOut:
                AtyTransitionUtil.exitToBottom(this);
                break;
            case RightIn:
                AtyTransitionUtil.enterFromRight(this);
                break;
            case RightOut:
                AtyTransitionUtil.exitToRight(this);
                break;
        }
    }

    //监听返回键
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //获取当前activity的Trans
    private Transition getTrans(Class clazz){
        return activityTrans.get(clazz);
    }
    //当前Activity是否存在Trans
    private Boolean isActivityTrans(){
        return activityTrans.containsKey(this.getClass());
    }
    //获取反向的Trans
    private Transition getReverse(Transition transition){
        Transition tran = Transition.TopOut;
        switch (transition){
            case TopIn:
                tran = Transition.TopOut;
                break;
            case TopOut:
                tran = Transition.TopIn;
                break;
            case LeftIn:
                tran = Transition.LeftOut;
                break;
            case LeftOut:
                tran = Transition.LeftIn;
                break;
            case BottomIn:
                tran = Transition.BottomOut;
                break;
            case BottomOut:
                tran = Transition.BottomIn;
                break;
            case RightIn:
                tran = Transition.RightOut;
                break;
            case RightOut:
                tran = Transition.RightIn;
                break;
        }
        return tran;
    }
    //启动加载框
    protected void startProgressDialog() {
        if (progressDialogView == null) {
            progressDialogView = new ProgressDialogView();
        }
        progressDialogView.startLoad(this, "",false);
    }
    //启动加载框
    protected void startProgressDialog(String msg) {
        if (progressDialogView == null) {
            progressDialogView = new ProgressDialogView();
        }
        progressDialogView.startLoad(this, msg,false);
    }
    //启动加载框
    protected void startProgressDialog(boolean cancelable) {
        if (progressDialogView == null) {
            progressDialogView = new ProgressDialogView();
        }
        progressDialogView.startLoad(this, "",cancelable);
    }
    //启动加载框
    protected void startProgressDialog(String msg,boolean cancelable) {
        if (progressDialogView == null) {
            progressDialogView = new ProgressDialogView();
        }
        progressDialogView.startLoad(this, msg,cancelable);
    }
    //关闭加载框
    protected void stopProgressDialog() {
        if (progressDialogView != null) {
            progressDialogView.stopLoad();
        }
    }
    //显示Toast
    protected void showToast(String content){
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
    }
}
