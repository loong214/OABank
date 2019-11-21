package com.findingdata.oabank.entity;

import java.io.Serializable;

/**
 * Created by Loong on 2019/11/21.
 * Version: 1.0
 * Describe:
 */
public class BaseEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * code : 200
     * data : {"f_id":"44e21750-0c56-11ea-8003-9f39673eebae"}
     * msg : 上传附件成功
     */

    private int code;
    private T data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
