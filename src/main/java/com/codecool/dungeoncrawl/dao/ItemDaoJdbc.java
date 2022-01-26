package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.ItemModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoJdbc implements ItemDao {
    private DataSource dataSource;

    public ItemDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(ItemModel item, GameState state) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO item (game_state_id, item_name, x, y) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, state.getId());
            statement.setString(2, item.getName());
            statement.setInt(3, item.getX());
            statement.setInt(4, item.getY());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            item.setId(resultSet.getInt(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<ItemModel> getAll(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT item_name, x, y FROM item INNER JOIN game_state ON game_state.id = item.game_state_id INNER JOIN game_state ON game_state.player_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<ItemModel> itemList = new ArrayList<>();
            while (rs.next()){
                ItemModel item = new ItemModel(
                        rs.getString(1),
                        rs.getInt(2),
                        rs.getInt(3));
                        itemList.add(item);
            }
            return itemList;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading author with id: " + id, e);
        }

    }

}
