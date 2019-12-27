package com.findingdata.oabank.utils.http;

import org.xutils.common.Callback;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Loong on 2019/12/26.
 * Version: 1.0
 * Describe: 请求参数封装
 */
public class RequestParam<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String url;
    private HttpMethod method;
    private Map<String,String> getRequestMap;
    private Map<String,Object> postRequestMap;
    private Callback.CommonCallback<T> callback;

    public RequestParam(String url, HttpMethod method, Map<String, String> getRequestMap, Map<String, Object> postRequestMap, Callback.CommonCallback<T> callback) {
        this.url = url;
        this.method = method;
        this.getRequestMap = getRequestMap;
        this.postRequestMap = postRequestMap;
        this.callback = callback;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public Map<String, String> getGetRequestMap() {
        return getRequestMap;
    }

    public void setGetRequestMap(Map<String, String> getRequestMap) {
        this.getRequestMap = getRequestMap;
    }

    public Map<String, Object> getPostRequestMap() {
        return postRequestMap;
    }

    public void setPostRequestMap(Map<String, Object> postRequestMap) {
        this.postRequestMap = postRequestMap;
    }

    public Callback.CommonCallback<T> getCallback() {
        return callback;
    }

    public void setCallback(Callback.CommonCallback<T> callback) {
        this.callback = callback;
    }
}
