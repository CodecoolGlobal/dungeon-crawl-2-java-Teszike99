package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.GameState;

import java.util.List;

public interface EnemyDao {

    void add(EnemyModel enemy, GameState state);
    void update(EnemyModel enemy);
    List<EnemyModel> getAll(int id);
}
