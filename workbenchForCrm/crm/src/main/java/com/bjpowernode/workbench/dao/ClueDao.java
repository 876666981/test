package com.bjpowernode.workbench.dao;


import com.bjpowernode.workbench.domian.Clue;
import com.bjpowernode.workbench.domian.ClueActivityRelation;

public interface ClueDao {


    int save(Clue c);


    Clue detail(String id);

    int bund(ClueActivityRelation c);
}
