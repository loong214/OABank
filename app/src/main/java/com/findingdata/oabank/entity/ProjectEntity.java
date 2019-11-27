package com.findingdata.oabank.entity;

import java.io.Serializable;

/**
 * Created by Loong on 2019/11/26.
 * Version: 1.0
 * Describe: 项目实体
 */
public class ProjectEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * p_id : 29b61600-1018-11ea-818a-030aa1e7aaa9
     * p_name : 时代倾城
     * p_client : 长沙银行金星北路支行
     * p_status : 1004
     * p_reason : 客户不要了
     * p_city : 长沙市
     * p_type : 住宅
     * p_loan_type : 房抵快贷
     * p_loan_person : 玛丽亚
     * p_create_time : 2019-11-26T06:44:15.000Z
     * p_customer : 经典评估
     * p_price : 133
     */

    private String p_id;
    private String p_name;
    private String p_client;
    private String p_status;
    private String p_reason;
    private String p_city;
    private String p_type;
    private String p_loan_type;
    private String p_loan_person;
    private String p_create_time;
    private String p_customer;
    private Double p_price;

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_client() {
        return p_client;
    }

    public void setP_client(String p_client) {
        this.p_client = p_client;
    }

    public String getP_status() {
        return p_status;
    }

    public void setP_status(String p_status) {
        this.p_status = p_status;
    }

    public String getP_reason() {
        return p_reason;
    }

    public void setP_reason(String p_reason) {
        this.p_reason = p_reason;
    }

    public String getP_city() {
        return p_city;
    }

    public void setP_city(String p_city) {
        this.p_city = p_city;
    }

    public String getP_type() {
        return p_type;
    }

    public void setP_type(String p_type) {
        this.p_type = p_type;
    }

    public String getP_loan_type() {
        return p_loan_type;
    }

    public void setP_loan_type(String p_loan_type) {
        this.p_loan_type = p_loan_type;
    }

    public String getP_loan_person() {
        return p_loan_person;
    }

    public void setP_loan_person(String p_loan_person) {
        this.p_loan_person = p_loan_person;
    }

    public String getP_create_time() {
        return p_create_time;
    }

    public void setP_create_time(String p_create_time) {
        this.p_create_time = p_create_time;
    }

    public String getP_customer() {
        return p_customer;
    }

    public void setP_customer(String p_customer) {
        this.p_customer = p_customer;
    }

    public Double getP_price() {
        return p_price;
    }

    public void setP_price(Double p_price) {
        this.p_price = p_price;
    }
}
