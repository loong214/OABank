package com.findingdata.oabank.entity;

/**
 * Created by Loong on 2019/11/20.
 * Version: 1.0
 * Describe: 项目列表状态枚举
 */
public enum ProjectCenterListType {
    Todo("1001"),//待派单
    Doing("1002"),//进行中
    Pause("1003"),//暂停
    Stop("1004");//已终止
    private final String type;

    ProjectCenterListType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
