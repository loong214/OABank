package com.findingdata.oabank.base;

import com.findingdata.oabank.BuildConfig;

/**
 * Created by Loong on 2019/12/27.
 * Version: 1.0
 * Describe: 基础配置
 */
public class Config {

    public static final String BASE_URL= BuildConfig.BASE_URL;//后台服务地址
    public static final String COOKIE_NAME="token";//Cookie名

    public static final String SD_APP_DIR_NAME = "OABank"; //存储程序在外部SD卡上的根目录的名字
    public static final String PHOTO_DIR_NAME = "photo";    //存储照片在根目录下的文件夹名字
}
