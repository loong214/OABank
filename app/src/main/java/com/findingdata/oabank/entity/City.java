package com.findingdata.oabank.entity;

/**
 * Created by Loong on 2019/11/26.
 * Version: 1.0
 * Describe: 抵押物区域
 */
public enum City {
    All("0","不限"),
    CSS("430100","长沙市"),
    KF("430101","开福区"),
    FR("430102","芙蓉区"),
    YH("430103","雨花区"),
    YL("430104","岳麓区"),
    TX("430105","天心区"),
    WC("430106","望城区"),
    LY("430107","浏阳市"),
    CSX("430108","长沙县"),
    NX("430109","宁乡县");
    private final String type;
    private final String chs;

    City(String type, String chs) {
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
