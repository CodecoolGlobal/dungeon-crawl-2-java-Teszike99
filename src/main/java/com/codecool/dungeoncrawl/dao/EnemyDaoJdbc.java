package com.codecool.dungeoncrawl.dao;
import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.GameState;

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
    public void add(EnemyModel enemy, GameState state) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO enemy (game_state_id, enemy_name, strength, hp, x, y) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, state.getId());
            statement.setString(2, enemy.getName());
            statement.setInt(3, enemy.getStrength());
            statement.setInt(4, enemy.getHp());
            statement.setInt(5, enemy.getX());
            statement.setInt(6, enemy.getY());
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
            String sql = "SELECT enemy_name, strength, hp, x, y FROM enemy WHERE game_state_id = ?";
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

    @Override
    public void deleteAllWithGameStateId(int gameStateId){
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM enemy WHERE game_state_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, gameStateId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

