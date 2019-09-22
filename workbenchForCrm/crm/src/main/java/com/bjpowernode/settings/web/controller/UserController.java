package com.bjpowernode.settings.web.controller;

import com.bjpowernode.settings.domain.User;
import com.bjpowernode.settings.service.UserService;
import com.bjpowernode.settings.service.impl.UserServiceImpl;
import com.bjpowernode.utils.MD5Util;
import com.bjpowernode.utils.PrintJson;
import com.bjpowernode.utils.ServiceFactory;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:林间有风
 * 2019/9/16
 */
public class UserController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/settings/user/login.do".equals(path)){
            login(req,resp);
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) {
        String loginAct = req.getParameter("loginAct");
        String loginPwd = req.getParameter("loginPwd");
        loginPwd = MD5Util.getMD5(loginPwd);
        String ip = req.getRemoteAddr();
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        try {
            User user = userService.login(loginAct,loginPwd,ip);
            req.getSession().setAttribute("user", user);
            PrintJson.printJsonFlag(resp, true);
        } catch (LoginException e) {
            Map<String,Object> map = new HashMap<>();
            map.put("success", false);
            map.put("msg", e.getMessage());
            PrintJson.printJsonObj(resp, map);
        }
    }
}
