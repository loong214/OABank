package com.findingdata.oabank.utils;

import com.findingdata.oabank.FDApplication;

/**
 * Created by Loong on 2019/11/20.
 * Version: 1.0
 * Describe: SharedPreferences管理类
 */
public class SharedPreferencesManage {
    public static void setToken(String token){
        SharedPreferencesUtil.getInstance().saveStringValue(FDApplication.getAppContext(),"fd","token",token);
    }
    public static String getToken(){
        return SharedPreferencesUtil.getInstance().getStringValue(FDApplication.getAppContext(),"fd","token");
    }
}
