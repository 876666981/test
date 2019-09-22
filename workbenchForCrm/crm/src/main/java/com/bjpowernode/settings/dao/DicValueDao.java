package com.bjpowernode.settings.dao;

import com.bjpowernode.settings.domain.DicValue;

import java.util.List;

/**
 * Author:林间有风
 * 2019/9/19
 */
public interface DicValueDao {
    List<DicValue> getList(String code);
}
