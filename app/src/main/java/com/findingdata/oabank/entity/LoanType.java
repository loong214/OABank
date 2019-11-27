package com.findingdata.oabank.entity;

/**
 * Created by Loong on 2019/11/26.
 * Version: 1.0
 * Describe: 贷款类型
 */
public enum LoanType {
    All("0","不限"),
    XW("2001","小薇助业"),
    ZH("2002","综合消费类"),
    LP("2003","楼盘整体评估"),
    ES("2004","二手房"),
    FD("2005","房抵快贷");
    private final String type;
    private final String chs;

    LoanType(String type,String chs) {
        this.type = type;
        this.chs=chs;
    }

    public String getType() {
        return type;
    }

    public String getChs() {
        return chs;
    }
}
