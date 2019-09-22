package com.bjpowernode.settings.service;

import com.bjpowernode.settings.domain.User;

import javax.security.auth.login.LoginException;
import java.util.List;

/**
 * Author:林间有风
 * 2019/9/16
 */
public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserListNameByID();
}
