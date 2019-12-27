package com.findingdata.oabank.utils.http;


import com.findingdata.oabank.utils.Config;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.TokenUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

import static com.findingdata.oabank.utils.Config.BASE_URL;

/**
 * Created by Loong on 2019/11/20.
 * Version: 1.0
 * Describe: 通用请求封装
 */
public class XHttp {
    /**
     * 发送get请求
     * @param <T>
     */
    public static <T> Callback.Cancelable Get(final String url, final Map<String,String> map, final Callback.CommonCallback<T> callback){
        RequestParams params=new RequestParams(url);
        StringBuilder sbCookie = new StringBuilder();
        sbCookie.append(Config.COOKIE_NAME).append("=")
                .append(TokenUtils.getToken()).append("; path=/; domain=")
                .append(Config.BASE_URL);
        params.addHeader("Cookie",sbCookie.toString());
        LogUtils.d("Cookie",sbCookie);
        if(null!=map){
            for(Map.Entry<String, String> entry : map.entrySet()){
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
            }
        }
        return x.http().get(params, callback);
    }

    /**
     * 发送异步post请求
     *
     * @param <T>
     */
    public static <T> Callback.Cancelable Post(final String url, final Map<String, Object> map, final Callback.CommonCallback<T> callback) {
        if(url.equals(BASE_URL+"/api/Home/Login")){
            RequestParams params = new RequestParams(url);
            if (null != map) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    params.addParameter(entry.getKey(), entry.getValue());
                }
            }
            return x.http().post(params, callback);
        }else{
            RequestParams params = new RequestParams(url);
            StringBuilder sbCookie = new StringBuilder();
            sbCookie.append(Config.COOKIE_NAME).append("=")
                    .append(TokenUtils.getToken()).append("; path=/; domain=")
                    .append(Config.BASE_URL);
            params.addHeader("Cookie",sbCookie.toString());
            LogUtils.d("Cookie",sbCookie);
            if (null != map) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    params.addParameter(entry.getKey(), entry.getValue());
                }
            }
            return x.http().post(params, callback);
        }
    }

    /**
     * 上传文件
     *
     * @param <T>
     */
    public static <T> Callback.Cancelable UpLoadFile(final String url, final Map<String, Object> map, final Callback.CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        params.setMultipart(true);
        return x.http().post(params, callback);
    }


    /**
     * 下载文件
     *
     * @param <T>
     */
    public static <T> Callback.Cancelable DownLoadFile(final String url, final String filepath, final Callback.ProgressCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(filepath);
        return x.http().get(params, callback);
    }

}
