package com.findingdata.oabank.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.findingdata.oabank.FDApplication;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Created by Loong on 2019/11/20.
 * Version: 1.0
 * Describe: SharedPreferences工具类
 */
public class SharedPreferencesUtil {

    private static SharedPreferencesUtil instance=null;

    public static SharedPreferencesUtil getInstance() {
        if(instance == null){
            instance = new SharedPreferencesUtil();
        }
        return instance;
    }

    /**
     * 存储对象数据
     * @param context
     * @param spName
     * @param key
     * @param ob
     * @return
     */
    public boolean saveObject(Context context, String spName, String key,Object ob) {
        if (ob == null) {
            return false;
        }
        boolean falg = false;
        SharedPreferences preferences = FDApplication.getAppContext()
                .getSharedPreferences(spName, context.MODE_PRIVATE);
        // 创建字节输出
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            // 创建对象输出流，并封装字节流
            oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(ob);
            // 将字节流编码成base64的字符窜

            String oAuth_Base64 = new String(Base64.encodeBase64(baos
                    .toByteArray()));
            falg = preferences.edit().putString(key, oAuth_Base64).commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }

                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return falg;
        }
    }

    /**
     * 获取对象数据
     * @param context
     * @param spName
     * @param key
     * @return
     */
    public Object getObject(Context context, String spName, String key) {
        Object ob = null;
        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        String productBase64 = preferences.getString(key, "");
        // 读取字节
        byte[] base64 = Base64.decodeBase64(productBase64.getBytes());
        // 封装到字节流
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        ObjectInputStream bis = null;
        try {
            // 再次封装
            bis = new ObjectInputStream(bais);
            // 读取对象
            ob = bis.readObject();
        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }

                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ob;
    }

    /**
     * 存储Integer数据
     * @param context
     * @param spName
     * @param key
     * @param value
     */
    public void saveIntValue(Context context, String spName, String key,
                             int value) {
        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        preferences.edit().putInt(key, value).commit();

    }

    /**
     * 获取Integer数据
     * @param context
     * @param spName
     * @param key
     * @return
     */
    public int getIntValue(Context context, String spName, String key) {
        return getIntValue(context, spName, key, 0);
    }
    /**
     * 获取Integer数据
     * @param context
     * @param spName
     * @param key
     * @return
     */
    public int getIntValue(Context context, String spName, String key,
                           int defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        return preferences.getInt(key, defaultValue);
    }

    /**
     * 存储Float数据
     * @param context
     * @param spName
     * @param key
     * @param value
     */
    public void saveFloatValue(Context context, String spName, String key,
                               float value) {
        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        preferences.edit().putFloat(key, value).commit();

    }

    /**
     * 获取Float数据
     * @param context
     * @param spName
     * @param key
     * @return
     */
    public float getFloatValue(Context context, String spName, String key) {
        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        return preferences.getFloat(key, 0);
    }

    /**
     * 存储Boolean数据
     * @param context
     * @param spName
     * @param key
     * @param value
     */
    public void saveBooleanValue(Context context, String spName, String key,
                                 boolean value) {

        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        preferences.edit().putBoolean(key, value).commit();

    }

    /**
     * 获取Boolean数据
     * @param context
     * @param spName
     * @param key
     * @return
     */
    public boolean getBooleanValue(Context context, String spName, String key) {
        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    /**
     * 获取Boolean数据
     * @param context
     * @param spName
     * @param key
     * @param isDefault
     * @return
     */
    public boolean getBooleanValue(Context context, String spName, String key,
                                   boolean isDefault) {
        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        return preferences.getBoolean(key, isDefault);
    }

    /**
     * 存储Long数据
     * @param context
     * @param spName
     * @param key
     * @param value
     */
    public void saveLongValue(Context context, String spName, String key,
                              long value) {

        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);

        preferences.edit().putLong(key, value).commit();
    }

    /**
     * 获取Long数据
     * @param context
     * @param spName
     * @param key
     * @return
     */
    public long getLongValue(Context context, String spName, String key) {
        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        return preferences.getLong(key, 0);
    }

    /**
     * 存储String数据
     * @param context
     * @param spName
     * @param key
     * @param value
     */
    public void saveStringValue(Context context, String spName, String key,
                                String value) {
        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        preferences.edit().putString(key, value).commit();
    }

    /**
     * 获取String数据
     * @param context
     * @param spName
     * @param key
     * @return
     */
    public String getStringValue(Context context, String spName, String key) {
        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }
}
