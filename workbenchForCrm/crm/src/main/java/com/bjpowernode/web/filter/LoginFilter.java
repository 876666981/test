package com.bjpowernode.web.filter;

import com.bjpowernode.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;

        if ("/login.jsp".equals(request.getServletPath()) || "/settings/user/login.do".equals(request.getServletPath())){
            chain.doFilter(req, resp);
        }else{
            User user = (User) request.getSession().getAttribute("user");
            if (user != null){
                chain.doFilter(req, resp);
            }else{
                HttpServletResponse response = (HttpServletResponse) resp;
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }
        }
    }


}
