package com.findingdata.oabank;

import android.app.Application;

import com.pgyersdk.crash.PgyCrashManager;

import org.xutils.x;

/**
 * Created by zengx on 2019/11/14.
 * Describe:
 */
public class FDApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        //蒲公英crash收集注册
        PgyCrashManager.register();
    }
}
