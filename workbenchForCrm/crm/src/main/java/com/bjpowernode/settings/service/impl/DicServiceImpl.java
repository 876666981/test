package com.bjpowernode.settings.service.impl;

import com.bjpowernode.settings.dao.DicTypeDao;
import com.bjpowernode.settings.dao.DicValueDao;
import com.bjpowernode.settings.domain.DicType;
import com.bjpowernode.settings.domain.DicValue;
import com.bjpowernode.settings.service.DicSerivice;
import com.bjpowernode.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:林间有风
 * 2019/9/19
 */
public class DicServiceImpl implements DicSerivice {
    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String,List<DicValue>> map = new HashMap<>();
        List<DicType> dicValueList = dicTypeDao.getList();
        for (DicType dt: dicValueList){
            List<DicValue> dicValues = dicValueDao.getList(dt.getCode());
            map.put(dt.getCode()+"List", dicValues);
        }
        return map;
    }
}
