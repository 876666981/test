package com.bjpowernode.settings.service.impl;



import com.bjpowernode.settings.dao.UserDao;
import com.bjpowernode.settings.domain.User;
import com.bjpowernode.settings.service.UserService;
import com.bjpowernode.utils.DateTimeUtil;
import com.bjpowernode.utils.SqlSessionUtil;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Author:林间有风
 * 2019/9/16
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public List<User> getUserListNameByID() {
        List<User> userList = userDao.getUserListNameByID();
        Iterator<User> userIterator = userList.iterator();

        while (userIterator.hasNext()){
            User user = userIterator.next();
            if (user.getExpireTime().compareTo(DateTimeUtil.getSysTime()) < 0){
                userIterator.remove();
            }
        }
        return userList;
    }

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        Map<String,String> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);
        User user = userDao.login(map);
        if (user == null){
            throw new LoginException("账号密码不正确");
        }
        //验证账号失效时间
        String expireTime = user.getExpireTime();
        if (expireTime.compareTo(DateTimeUtil.getSysTime()) < 0){
            throw new LoginException("账号已失效,请联系管理员");
        }
        //验证账号锁定状态
       if ("0".equals(user.getLockState())){
            throw new LoginException("账号已锁定,请联系管理员");
       }
       //验证浏览器ip
        if (!user.getAllowIps().contains(ip)){
            throw new LoginException("ip受限");
        }
        return user;
    }
}
