package com.bjpowernode.workbench.dao;

import com.bjpowernode.workbench.domian.Activity;

import java.util.List;
import java.util.Map;

/**
 * Author:林间有风
 * 2019/9/17
 */
public interface ActivityDao {
    int save(Activity activity);

    List<Activity> getPageList(Map<String, Object> map);

    int getTotal(Map<String, Object> map);

    int delete(String[] ids);

    Activity getFindByID(String id);

    int update(Activity activity);

    Activity detail(String id);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByName(String aname);
}
