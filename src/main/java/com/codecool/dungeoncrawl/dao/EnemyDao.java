package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.model.EnemyModel;

import java.util.List;

public interface EnemyDao {

    void add(EnemyModel enemy);
    void update(EnemyModel enemy);
    List<EnemyModel> getAll(int id);
}
