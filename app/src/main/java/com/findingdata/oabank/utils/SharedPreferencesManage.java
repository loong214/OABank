package com.findingdata.oabank.utils;

import com.findingdata.oabank.FDApplication;
import com.findingdata.oabank.entity.FilterValueEntity;

/**
 * Created by Loong on 2019/11/20.
 * Version: 1.0
 * Describe: SharedPreferences管理类
 */
public class SharedPreferencesManage {
    public static void setToken(String token){
        SharedPreferencesUtil.getInstance().saveStringValue(FDApplication.getAppContext(),"fd","Token",token);
    }
    public static String getToken(){
        return SharedPreferencesUtil.getInstance().getStringValue(FDApplication.getAppContext(),"fd","Token");
    }
    public static void setFilterValueEntity(FilterValueEntity filterValueEntity){
        SharedPreferencesUtil.getInstance().saveObject(FDApplication.getAppContext(),"fd","FilterValue",filterValueEntity);
    }
    public static FilterValueEntity getFilterValueEntity(){
        return (FilterValueEntity) SharedPreferencesUtil.getInstance().getObject(FDApplication.getAppContext(),"fd","FilterValue");
    }
}
