package com.findingdata.oabank.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2019/11/22.
 * Version: 1.0
 * Describe: 消息列表实体
 */
public class NotifyListEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * totalCount : 23
     * pageIndex : 1
     * pageSize : 10
     * list : [{"f_id":"1158e260-0c56-11ea-8003-9f39673eebae","f_original_name":"201911201616127949.jpg","f_name":"92d3e1fab9ec46f07ef7c9f92c33a185.jpg","f_path":"file/92d3e1fab9ec46f07ef7c9f92c33a185.jpg","f_size":29878,"f_mime":"image/jpeg","f_create_time":"2019-11-21T11:57:19.000Z","f_create_by":"c54e3a80-eafb-11e9-96a1-61c4c64d42ff"}]
     */

    private int totalCount;
    private int pageIndex;
    private int pageSize;
    private List<NotifyEntity> list;

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

    public List<NotifyEntity> getList() {
        return list;
    }

    public void setList(List<NotifyEntity> list) {
        this.list = list;
    }

    public static class ListBean {
    }
}
