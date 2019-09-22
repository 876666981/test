package com.bjpowernode.workbench.web.controller;

import com.bjpowernode.settings.domain.User;
import com.bjpowernode.settings.service.UserService;
import com.bjpowernode.settings.service.impl.UserServiceImpl;
import com.bjpowernode.utils.DateTimeUtil;
import com.bjpowernode.utils.PrintJson;
import com.bjpowernode.utils.ServiceFactory;
import com.bjpowernode.utils.UUIDUtil;
import com.bjpowernode.vo.PaginationVo;
import com.bjpowernode.workbench.domian.Activity;
import com.bjpowernode.workbench.domian.ActivityRemark;
import com.bjpowernode.workbench.service.ActivityService;
import com.bjpowernode.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:林间有风
 * 2019/9/17
 */
public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/workbench/activity/getUserListNameByID.do".equals(path))
            getUserListNameByID(resp);
        else if("/workbench/activity/save.do".equals(path))
            save(req,resp);
        else if ("/workbench/activity/pageList.do".equals(path))
            getPageList(req,resp);
        else if ("/workbench/activity/delete.do".equals(path))
            delete(req,resp);
        else if ("/workbench/activity/selectByID.do".equals(path))
            selectByID(req,resp);
        else if ("/workbench/activity/update.do".equals(path))
            update(req,resp);
        else if ("/workbench/activity/detail.do".equals(path))
            detail(req,resp);
        else if ("/workbench/activity/getRemarkListByAid.do".equals(path))
            getRemarkListByAid(req,resp);
        else if ("/workbench/activity/saveRemark.do".equals(path))
            saveRemark(req,resp);

    }

    private void saveRemark(HttpServletRequest req, HttpServletResponse resp) {
        String noteContent = req.getParameter("noteContent");
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)req.getSession().getAttribute("user")).getId();
        String editFlag = "0";
        String activityId = req.getParameter("activityId");
        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setActivityId(activityId);
        activityRemark.setId(id);
        activityRemark.setNoteContent(noteContent);
        activityRemark.setCreateBy(createBy);
        activityRemark.setCreateTime(createTime);
        activityRemark.setEditFlag(editFlag);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = activityService.saveRemark(activityRemark);
        Map<String,Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("ar", activityRemark);
        PrintJson.printJsonObj(resp, map);
    }

    private void getRemarkListByAid(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<ActivityRemark> activityRemarkList = activityService.getRemarkListByAid(id);
        PrintJson.printJsonObj(resp, activityRemarkList);
    }

    private void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity activity = activityService.detail(id);
        req.setAttribute("activity", activity);
        req.getRequestDispatcher("/workbench/activity/detail.jsp").forward(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        String owner = req.getParameter("owner");
        String name = req.getParameter("name");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String cost = req.getParameter("cost");
        String description = req.getParameter("description");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)req.getSession().getAttribute("user")).getId();

        Activity activity = new Activity();
        activity.setId(id);
        activity.setName(name);
        activity.setOwner(owner);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setDescription(description);
        activity.setCost(cost);
        activity.setEditBy(editBy);
        activity.setEditTime(editTime);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = activityService.update(activity);
        PrintJson.printJsonFlag(resp, flag);
    }

    private void selectByID(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Map<String,Object> map = activityService.getFindByID(id);
        PrintJson.printJsonObj(resp, map);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) {
        String[] ids = req.getParameterValues("id");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        try {
            boolean flag = activityService.delete(ids);
            PrintJson.printJsonFlag(resp, flag);
        } catch (Exception e) {
            e.printStackTrace();
            PrintJson.printJsonFlag(resp, false);
        }
    }

    private void getPageList(HttpServletRequest req, HttpServletResponse resp) {
        String owner = req.getParameter("owner");
        String name = req.getParameter("name");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");

        //页码
        int pageNo = Integer.valueOf(req.getParameter("pageNo"));
        //每页显示的条数
        int pageSize = Integer.valueOf(req.getParameter("pageSize"));
        //计算出略过的记录数
        int skipCount = (pageNo-1)*pageSize;
        //系统当前时间
        String time = DateTimeUtil.getSysTime();
        Map<String,Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("time", time);
        map.put("pageNo",skipCount);
        map.put("pageSize",pageSize);
        ActivityService a = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        PaginationVo<Activity> paginationVo = a.getPageList(map);
        PrintJson.printJsonObj(resp, paginationVo);
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) {
        String id = UUIDUtil.getUUID();
        String owner = req.getParameter("owner");
        String name = req.getParameter("name");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String cost = req.getParameter("cost");
        String description = req.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();
        String createBy =  ((User)req.getSession().getAttribute("user")).getId();
        Activity activity = new Activity();
        activity.setId(id);
        activity.setCost(cost);
        activity.setCreateBy(createBy);
        activity.setCreateTime(createTime);
        activity.setDescription(description);
        activity.setEndDate(endDate);
        activity.setStartDate(startDate);
        activity.setName(name);
        activity.setOwner(owner);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = activityService.save(activity);
        PrintJson.printJsonFlag(resp, flag);
    }

    private void getUserListNameByID(HttpServletResponse resp) {
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> userList = userService.getUserListNameByID();
        PrintJson.printJsonObj(resp, userList);
    }
}
