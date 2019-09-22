package com.bjpowernode.settings.dao;

import com.bjpowernode.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * Author:林间有风
 * 2019/9/16
 */
public interface UserDao {
    User login(Map<String, String> map);

    List<User> getUserListNameByID();
}
