package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;

import java.util.List;

public interface PlayerDao {
    void add(PlayerModel player);
    void update(PlayerModel player);
    int getPlayerId(String name);
    PlayerModel get(String loadedName);
    List<PlayerModel> getAll();
}
