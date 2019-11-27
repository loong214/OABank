package com.findingdata.oabank.entity;

import java.io.Serializable;

/**
 * Created by Loong on 2019/11/26.
 * Version: 1.0
 * Describe: 项目过滤选择项的值
 */
public class FilterValueEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String loanValue;
    private String typeValue;
    private String cityValue;

    public FilterValueEntity(String loanValue, String typeValue, String cityValue) {
        this.loanValue = loanValue;
        this.typeValue = typeValue;
        this.cityValue = cityValue;
    }

    public String getLoanValue() {
        return loanValue;
    }

    public void setLoanValue(String loanValue) {
        this.loanValue = loanValue;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getCityValue() {
        return cityValue;
    }

    public void setCityValue(String cityValue) {
        this.cityValue = cityValue;
    }

    @Override
    public String toString() {
        return "FilterValueEntity{" +
                "loanValue='" + loanValue + '\'' +
                ", typeValue='" + typeValue + '\'' +
                ", cityValue='" + cityValue + '\'' +
                '}';
    }
}
