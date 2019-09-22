package com.bjpowernode.workbench.service.impl;

import com.bjpowernode.settings.dao.UserDao;
import com.bjpowernode.settings.domain.User;
import com.bjpowernode.utils.SqlSessionUtil;
import com.bjpowernode.vo.PaginationVo;
import com.bjpowernode.workbench.dao.ActivityDao;
import com.bjpowernode.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.workbench.domian.Activity;
import com.bjpowernode.workbench.domian.ActivityRemark;
import com.bjpowernode.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:林间有风
 * 2019/9/17
 */
public class ActivityServiceImpl implements ActivityService {
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    @Override
    public boolean save(Activity activity) {
        boolean flag = true;
        int count = activityDao.save(activity);
        if (count == 0){
            flag = false;
        }
        return flag;
    }

    @Override
    public PaginationVo<Activity> getPageList(Map<String, Object> map) {
        List<Activity> data = activityDao.getPageList(map);
        int total = activityDao.getTotal(map);
        PaginationVo<Activity> paginationVo = new PaginationVo<>();
        paginationVo.setDataList(data);
        paginationVo.setTotal(total);
        return paginationVo;
    }

    @Override
    public boolean delete(String[] ids) {
        boolean flag = true;
        int count1 = activityRemarkDao.getCountByIds(ids);
        int count2 = activityRemarkDao.deleteByactivityId(ids);
        if (count1 != count2){
            flag = false;
        }
        int count3 = activityDao.delete(ids);
        if (count3 != ids.length){
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getFindByID(String id) {
        Activity a = activityDao.getFindByID(id);
        List<User> userList = userDao.getUserListNameByID();
        Map<String,Object> map = new HashMap<>();
        map.put("userList", userList);
        map.put("activityFind", a);
        return map;
    }

    @Override
    public boolean update(Activity activity) {
        boolean flag = true;
        int count = activityDao.update(activity);
        if (count == 0){
            flag = false;
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {
        Activity activity = activityDao.detail(id);
        return activity;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String id) {
        List<ActivityRemark> activityRemarkList = activityRemarkDao.getRemarkListByAid(id);
        return activityRemarkList;
    }

    @Override
    public boolean saveRemark(ActivityRemark activityRemark) {
        boolean flag = true;
        int count = activityRemarkDao.saveRemark(activityRemark);
        if (count != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map) {
        List<Activity> activityList = activityDao.getActivityListByNameAndNotByClueId(map);
        return activityList;
    }

    @Override
    public List<Activity> getActivityListByClueId(String clueId) {
        List<Activity> activityList = activityDao.getActivityListByClueId(clueId);
        return activityList;
    }

    @Override
    public List<Activity> getActivityListByName(String aname) {
        List<Activity> activityList = activityDao.getActivityListByName(aname);
        return activityList;
    }
}
