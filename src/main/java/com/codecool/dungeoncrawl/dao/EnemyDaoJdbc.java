package com.codecool.dungeoncrawl.dao;
import com.codecool.dungeoncrawl.model.EnemyModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnemyDaoJdbc implements EnemyDao{
    private DataSource dataSource;

    public EnemyDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;

    }

    @Override
    public void add(EnemyModel enemy) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO enemy (enemy_name, strength, hp, x, y) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, enemy.getName());
            statement.setInt(2, enemy.getStrength());
            statement.setInt(3, enemy.getHp());
            statement.setInt(4, enemy.getX());
            statement.setInt(5, enemy.getY());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            enemy.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(EnemyModel enemy) {

    }

    @Override
    public List<EnemyModel> getAll(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT enemy_name, strength, hp, x, y FROM enemy INNER JOIN game_state ON game_state.map_id = enemy.map_id INNER JOIN game_state ON game_state.player_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            List<EnemyModel> enemyList = new ArrayList<>();
            while (rs.next()){
                EnemyModel enemy = new EnemyModel(
                        rs.getString(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getInt(5));
                enemyList.add(enemy);
            }
            return enemyList;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading author with id: " + id, e);
        }
    }
}

