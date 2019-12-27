package com.findingdata.oabank.utils.http;

/**
 * Created by Loong on 2019/11/23.
 * Version: 1.0
 * Describe: 请求方法枚举
 */
public enum HttpMethod {
    Post(0x001),
    Get(0x002),
    Upload(0x003),
    Download(0x004);
    private final int type;

    HttpMethod(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
