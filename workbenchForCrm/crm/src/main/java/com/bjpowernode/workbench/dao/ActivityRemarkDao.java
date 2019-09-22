package com.bjpowernode.workbench.dao;

import com.bjpowernode.workbench.domian.ActivityRemark;

import java.util.List;

/**
 * Author:林间有风
 * 2019/9/17
 */
public interface ActivityRemarkDao {
    int getCountByIds(String[] ids);

    int deleteByactivityId(String[] ids);

    List<ActivityRemark> getRemarkListByAid(String id);

    int saveRemark(ActivityRemark activityRemark);
}
