package com.bjpowernode.settings.service;

import com.bjpowernode.settings.domain.DicType;
import com.bjpowernode.settings.domain.DicType;
import com.bjpowernode.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

/**
 * Author:林间有风
 * 2019/9/19
 */
public interface DicSerivice {
    Map<String, List<DicValue>> getAll();
}
