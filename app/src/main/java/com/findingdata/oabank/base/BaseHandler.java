package com.findingdata.oabank.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.findingdata.oabank.ui.LoginActivity;
import com.findingdata.oabank.ui.MainActivity;
import com.findingdata.oabank.utils.ExitAppUtils;
import com.findingdata.oabank.utils.TokenUtils;
import com.findingdata.oabank.utils.http.RequestParam;
import com.findingdata.oabank.utils.http.XHttp;


import org.xutils.common.util.LogUtil;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Loong on 2019/12/27.
 * Version: 1.0
 * Describe: Handler基类
 */
public class BaseHandler extends Handler {
    public final static int HTTP_REQUEST = 0x001;
    public final static int CHECK_LOGIN = 0x002;
    private final WeakReference<Activity> mActivity;
    private List<org.xutils.common.Callback.Cancelable> taskList;

    public BaseHandler(Activity activity, List<org.xutils.common.Callback.Cancelable> taskList) {
        mActivity = new WeakReference<>(activity);
        this.taskList = taskList;
    }

    @Override
    public void handleMessage(final Message msg) {
        final Activity activity = mActivity.get();
        if (activity != null) {
            if(msg.what == HTTP_REQUEST){
                if(!TokenUtils.isTokenValid()){
                    if(TokenUtils.isLoginInfoExist()){
                        TokenUtils.refreshToken(new TokenUtils.RefreshTokenListener() {
                            @Override
                            public void success() {
                                RequestParam requestParam= (RequestParam) msg.obj;
                                switch (requestParam.getMethod()){
                                    case Post:
                                        taskList.add(XHttp.Post(requestParam.getUrl(),requestParam.getPostRequestMap(),requestParam.getCallback()));
                                        break;
                                    case PostJson:
                                        taskList.add(XHttp.PostJson(requestParam.getUrl(),requestParam.getPostRequestMap(),requestParam.getCallback()));
                                        break;
                                    case Get:
                                        taskList.add(XHttp.Get(requestParam.getUrl(),requestParam.getGetRequestMap(),requestParam.getCallback()));
                                        break;
                                }
                            }

                            @Override
                            public void fail(String errMsg) {
                                new AlertDialog.Builder(activity)
                                        .setTitle("重新登录")
                                        .setMessage("您的登录信息已过期，请重新登录！")
                                        .setCancelable(false)
                                        .setNegativeButton(
                                                "去登录",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        activity.startActivity(new Intent(activity, LoginActivity.class));
                                                        ExitAppUtils.getInstance().exit();
                                                    }
                                                }).show();
                            }
                        });
                    }else{
                        new AlertDialog.Builder(activity)
                                .setTitle("重新登录")
                                .setMessage("您的登录信息已过期，请重新登录！")
                                .setCancelable(false)
                                .setNegativeButton(
                                        "去登录",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                activity.startActivity(new Intent(activity, LoginActivity.class));
                                                ExitAppUtils.getInstance().exit();
                                            }
                                        }).show();
                    }
                }else{
                    RequestParam requestParam= (RequestParam) msg.getData().getSerializable("request");
                    LogUtil.d(requestParam.getMethod().toString()+"++++");
                    switch (requestParam.getMethod()){
                        case Post:
                            taskList.add(XHttp.Post(requestParam.getUrl(),requestParam.getPostRequestMap(),requestParam.getCallback()));
                            break;
                        case PostJson:
                            LogUtil.d(requestParam.getMethod().getType()+"++++");
                            taskList.add(XHttp.PostJson(requestParam.getUrl(),requestParam.getPostRequestMap(),requestParam.getCallback()));
                            break;
                        case Get:
                            LogUtil.d(requestParam.getMethod().getType()+"++++");
                            taskList.add(XHttp.Get(requestParam.getUrl(),requestParam.getGetRequestMap(),requestParam.getCallback()));
                            break;
                    }
                }
            }else if(msg.what==CHECK_LOGIN){
                if(!TokenUtils.isTokenValid()){
                    if(TokenUtils.isLoginInfoExist()){
                        TokenUtils.refreshToken(new TokenUtils.RefreshTokenListener() {
                            @Override
                            public void success() {
                                activity.startActivity(new Intent(activity, MainActivity.class));
                                activity.finish();
                            }

                            @Override
                            public void fail(String errMsg) {
                                activity.startActivity(new Intent(activity,LoginActivity.class));
                                activity.finish();
                            }
                        });
                    }else{
                        activity.startActivity(new Intent(activity,LoginActivity.class));
                        activity.finish();
                    }
                }else{
                    activity.startActivity(new Intent(activity, MainActivity.class));
                    activity.finish();
                }

            }
        }
    }
}