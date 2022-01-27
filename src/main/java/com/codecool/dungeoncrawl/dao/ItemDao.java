package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.ItemModel;

import java.util.List;

public interface ItemDao {
    void add(ItemModel item, GameState state);
    void deleteAllWithGameStateId(int gameStateId);
    List<ItemModel> getAll(int i);
}
