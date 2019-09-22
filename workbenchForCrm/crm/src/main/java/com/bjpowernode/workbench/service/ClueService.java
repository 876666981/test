package com.bjpowernode.workbench.service;

import com.bjpowernode.workbench.domian.Activity;
import com.bjpowernode.workbench.domian.Clue;
import com.bjpowernode.workbench.domian.Tran;

/**
 * Author:林间有风
 * 2019/9/20
 */
public interface ClueService {
    boolean save(Clue c);

    Clue detail(String id);

    boolean bund(String clueId, String[] activityId);

    boolean convert(String clueId, String createBy, Tran t);
}
