package com.findingdata.oabank.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by zengx on 2019/11/17.
 * Describe: Utils
 */
public class Utils {
    /**
     * 获取当前客户端版本信息versionName
     * @param mContext
     * @return
     */
    public static String getCurrentVersion(Context mContext) {
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0);
            return info.versionName.trim();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        return "";
    }
    /**
     * 获取当前客户端版本信息versionCode
     * @param mContext
     * @return
     */
    public static int getCurrentBuild(Context mContext) {
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        return 0;
    }

    public static String transformIOSTime(String time){
        String str=time.replace("T","日 ");
        str=str.replaceFirst("/-/","年");
        str=str.replaceFirst("/-/","月");
        return str;
    }


}
