package com.bjpowernode.web.listener;

import com.bjpowernode.settings.domain.DicValue;
import com.bjpowernode.settings.service.DicSerivice;
import com.bjpowernode.settings.service.impl.DicServiceImpl;
import com.bjpowernode.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

/**
 * Author:林间有风
 * 2019/9/19
 */
public class SysIntListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("服务器缓存处理数据字典开始");
        ServletContext application = event.getServletContext();
        DicSerivice dicSerivice = (DicSerivice) ServiceFactory.getService(new DicServiceImpl());
        Map<String, List<DicValue>> map = dicSerivice.getAll();
        Set<String> set = map.keySet();
        for (String k : set) {
            application.setAttribute(k, map.get(k));
        }
        System.out.println("服务器缓存处理数据字典结束");
        Map<String,String> pmap = new HashMap<>();
        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");
        Enumeration<String> e = rb.getKeys();

        while (e.hasMoreElements()){
            String key = e.nextElement();
            String value = rb.getString(key);
            System.out.println(key);
            System.out.println(value);
            System.out.println("==========================");
            pmap.put(key, value);
        }
        application.setAttribute("pMap", pmap);
    }
}
