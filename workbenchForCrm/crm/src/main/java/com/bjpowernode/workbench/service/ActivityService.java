package com.bjpowernode.workbench.service;

import com.bjpowernode.vo.PaginationVo;
import com.bjpowernode.workbench.domian.Activity;
import com.bjpowernode.workbench.domian.ActivityRemark;

import java.util.List;
import java.util.Map;

/**
 * Author:林间有风
 * 2019/9/17
 */
public interface ActivityService {
    boolean save(Activity activity);

    PaginationVo<Activity> getPageList(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> getFindByID(String id);

    boolean update(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByAid(String id);

    boolean saveRemark(ActivityRemark activityRemark);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByName(String aname);
}
