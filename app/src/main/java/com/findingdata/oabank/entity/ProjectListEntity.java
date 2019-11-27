package com.findingdata.oabank.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2019/11/22.
 * Version: 1.0
 * Describe: 项目列表实体
 */
public class ProjectListEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * totalCount : 23
     * pageIndex : 1
     * pageSize : 10
     * list : []
     */

    private int totalCount;
    private int pageIndex;
    private int pageSize;
    private List<ProjectEntity> list;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<ProjectEntity> getList() {
        return list;
    }

    public void setList(List<ProjectEntity> list) {
        this.list = list;
    }

}
