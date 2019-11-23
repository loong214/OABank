package com.findingdata.oabank.entity;

import java.io.Serializable;

/**
 * Created by Loong on 2019/11/22.
 * Version: 1.0
 * Describe: 消息实体
 */
public class NotifyEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * f_id : 1158e260-0c56-11ea-8003-9f39673eebae
     * f_original_name : 201911201616127949.jpg
     * f_name : 92d3e1fab9ec46f07ef7c9f92c33a185.jpg
     * f_path : file/92d3e1fab9ec46f07ef7c9f92c33a185.jpg
     * f_size : 29878
     * f_mime : image/jpeg
     * f_create_time : 2019-11-21T11:57:19.000Z
     * f_create_by : c54e3a80-eafb-11e9-96a1-61c4c64d42ff
     */

    private String f_id;
    private String f_original_name;
    private String f_name;
    private String f_path;
    private int f_size;
    private String f_mime;
    private String f_create_time;
    private String f_create_by;

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    public String getF_original_name() {
        return f_original_name;
    }

    public void setF_original_name(String f_original_name) {
        this.f_original_name = f_original_name;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getF_path() {
        return f_path;
    }

    public void setF_path(String f_path) {
        this.f_path = f_path;
    }

    public int getF_size() {
        return f_size;
    }

    public void setF_size(int f_size) {
        this.f_size = f_size;
    }

    public String getF_mime() {
        return f_mime;
    }

    public void setF_mime(String f_mime) {
        this.f_mime = f_mime;
    }

    public String getF_create_time() {
        return f_create_time;
    }

    public void setF_create_time(String f_create_time) {
        this.f_create_time = f_create_time;
    }

    public String getF_create_by() {
        return f_create_by;
    }

    public void setF_create_by(String f_create_by) {
        this.f_create_by = f_create_by;
    }
}
