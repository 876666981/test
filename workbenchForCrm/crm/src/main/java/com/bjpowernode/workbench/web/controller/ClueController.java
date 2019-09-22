package com.bjpowernode.workbench.web.controller;

import com.bjpowernode.settings.domain.User;
import com.bjpowernode.utils.DateTimeUtil;
import com.bjpowernode.utils.PrintJson;
import com.bjpowernode.utils.ServiceFactory;
import com.bjpowernode.utils.UUIDUtil;
import com.bjpowernode.workbench.domian.Activity;
import com.bjpowernode.workbench.domian.Clue;
import com.bjpowernode.workbench.domian.Tran;
import com.bjpowernode.workbench.service.ActivityService;
import com.bjpowernode.workbench.service.ClueService;
import com.bjpowernode.workbench.service.impl.ActivityServiceImpl;
import com.bjpowernode.workbench.service.impl.ClueServiceImpl;

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
 * 2019/9/20
 */
public class ClueController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/workbench/clue/save.do".equals(path))
            save(req,resp);
        else if ("/workbench/clue/detail.do".equals(path))
            detail(req,resp);
        else if ("/workbench/clue/getActivityListByNameAndNotByClueId.do".equals(path))
            getActivityListByNameAndNotByClueId(req,resp);
        else if ("/workbench/clue/bund.do".equals(path))
            bund(req,resp);
        else if ("/workbench/clue/getActivityListByClueId.do".equals(path))
            getActivityListByClueId(req,resp);
        else if ("/workbench/clue/getActivityListByName.do".equals(path))
            getActivityListByName(req,resp);
        else if ("/workbench/clue/convert.do".equals(path))
            convert(req,resp);
    }

    private void convert(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String clueId = req.getParameter("clueId");
        //flag 判断是否要创建交易
        String flag = req.getParameter("flag");
        Tran t = null;
        String createBy = ((User)req.getSession().getAttribute("user")).getName();
        if ("a".equals(flag)){
            t = new Tran();
            t.setId(UUIDUtil.getUUID());
            t.setMoney(req.getParameter("money"));
            t.setName(req.getParameter("name"));
            t.setExpectedDate(req.getParameter("expectedDate"));
            t.setStage(req.getParameter("stage"));
            t.setActivityId(req.getParameter("activity"));
            t.setCreateBy(createBy);
            t.setCreateTime(DateTimeUtil.getSysTime());
        }
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag1 = clueService.convert(clueId,createBy,t);
        if (flag1)
            resp.sendRedirect(req.getContextPath()+"/workbench/clue/index.jsp");
        else
            resp.sendRedirect(req.getContextPath()+"/error_404.html");
    }

    private void getActivityListByName(HttpServletRequest req, HttpServletResponse resp) {
        String aname = req.getParameter("aname");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> activityList = activityService.getActivityListByName(aname);
        PrintJson.printJsonObj(resp, activityList);
    }

    private void getActivityListByClueId(HttpServletRequest req, HttpServletResponse resp) {
        String clueId = req.getParameter("clueId");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> activity = activityService.getActivityListByClueId(clueId);
        PrintJson.printJsonObj(resp, activity);
    }

    private void bund(HttpServletRequest req, HttpServletResponse resp) {
        String clueId = req.getParameter("clueId");
        String[] activityId = req.getParameterValues("activityId");
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag = clueService.bund(clueId,activityId);
        PrintJson.printJsonFlag(resp, flag);
    }

    private void getActivityListByNameAndNotByClueId(HttpServletRequest req, HttpServletResponse resp) {
        String aname = req.getParameter("aname");
        String clueId = req.getParameter("clueId");
        Map<String,String> map = new HashMap<>();
        map.put("aname", aname);
        map.put("clueId", clueId);
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> activityList = activityService.getActivityListByNameAndNotByClueId(map);
        PrintJson.printJsonObj(resp, activityList);
    }

    private void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Clue clue = clueService.detail(id);
        System.out.println("xxx");
        req.setAttribute("c", clue);
        req.getRequestDispatcher("/workbench/clue/detail.jsp").forward(req, resp);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行线索的添加操作");

        String id = UUIDUtil.getUUID();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");

        Clue c = new Clue();
        c.setId(id);
        c.setWebsite(website);
        c.setState(state);
        c.setSource(source);
        c.setPhone(phone);
        c.setOwner(owner);
        c.setNextContactTime(nextContactTime);
        c.setMphone(mphone);
        c.setJob(job);
        c.setFullname(fullname);
        c.setEmail(email);
        c.setDescription(description);
        c.setCreateTime(createTime);
        c.setCreateBy(createBy);
        c.setContactSummary(contactSummary);
        c.setCompany(company);
        c.setAppellation(appellation);
        c.setAddress(address);

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.save(c);

        PrintJson.printJsonFlag(response, flag);
    }
}
