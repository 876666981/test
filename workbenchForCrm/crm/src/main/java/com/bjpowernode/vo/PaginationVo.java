package com.bjpowernode.vo;

import java.util.List;

/**
 * Author:林间有风
 * 2019/9/17
 */
public class PaginationVo<T> {
    private List<T> dataList;
    private int total;

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
