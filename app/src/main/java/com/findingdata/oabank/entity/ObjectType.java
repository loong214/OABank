package com.findingdata.oabank.entity;

/**
 * Created by Loong on 2019/11/26.
 * Version: 1.0
 * Describe: 抵押物类型
 */
public enum ObjectType {
    All("0","不限"),
    ZZ("4001","住宅"),
    SY("4002","商业"),
    TD("4003","土地"),
    GY("4004","工业"),
    ZJ("4005","在建工程"),
    BG("4006","办公"),
    QT("4007","其他");
    private final String type;
    private final String chs;

    ObjectType(String type, String chs) {
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
