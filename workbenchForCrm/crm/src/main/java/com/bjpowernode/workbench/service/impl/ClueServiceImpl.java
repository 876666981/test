package com.bjpowernode.workbench.service.impl;

import com.bjpowernode.utils.SqlSessionUtil;
import com.bjpowernode.utils.UUIDUtil;
import com.bjpowernode.workbench.dao.ClueDao;
import com.bjpowernode.workbench.domian.Activity;
import com.bjpowernode.workbench.domian.Clue;
import com.bjpowernode.workbench.domian.ClueActivityRelation;
import com.bjpowernode.workbench.domian.Tran;
import com.bjpowernode.workbench.service.ClueService;

/**
 * Author:林间有风
 * 2019/9/20
 */
public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);

/*    private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);*/
    @Override
    public boolean save(Clue c) {
        boolean flag = true;

        int count = clueDao.save(c);

        if(count!=1){

            flag = false;

        }

        return flag;
    }

    @Override
    public Clue detail(String id) {
        Clue clue = clueDao.detail(id);
        return clue;
    }

    @Override
    public boolean bund(String clueId, String[] activityId) {
        boolean flag = true;
        int sum = 0;
        for (String a:activityId){
            ClueActivityRelation c = new ClueActivityRelation();
            c.setId(UUIDUtil.getUUID());
            c.setClueId(clueId);
            c.setActivityId(a);
            int count = clueDao.bund(c);
            sum+=count;
        }
        if (sum != activityId.length){
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean convert(String clueId, String createBy, Tran t) {
        return false;
    }
}
