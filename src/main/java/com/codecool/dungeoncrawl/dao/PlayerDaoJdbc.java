package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerDaoJdbc implements PlayerDao {
    private DataSource dataSource;

    public PlayerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerModel player) {
        Boolean isSaved = this.getAll().stream().anyMatch(playerModel -> playerModel.getPlayerName().equals(player.getPlayerName()));
        if (isSaved){
            this.update(player);
        }
        else {
            try (Connection conn = dataSource.getConnection()) {
                String sql = "INSERT INTO player (player_name, hp, strength, inventory, x, y) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, player.getPlayerName());
                statement.setInt(2, player.getHp());
                statement.setInt(3, player.getStrength());
                statement.setString(4, player.getInventory().toString());
                statement.setInt(5, player.getX());
                statement.setInt(6, player.getY());
                statement.executeUpdate();
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                player.setId(resultSet.getInt(1));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
   
        }

    }

    @Override
    public void update(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {

            String selectId = "SELECT id FROM player WHERE player_name = ?";
            PreparedStatement stSelect = conn.prepareStatement(selectId);
            stSelect.setString(1, player.getPlayerName());
            ResultSet rs = stSelect.executeQuery();
            rs.next();
            player.setId(rs.getInt(1));

            String sql = "UPDATE player SET player_name = ?, hp = ?, strength = ?, x = ?, y = ?, inventory = ? WHERE player_name = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, player.getPlayerName());
            st.setInt(2, player.getHp());
            st.setInt(3, player.getStrength());
            st.setInt(4, player.getX());
            st.setInt(5, player.getY());
            st.setString(6, player.getInventory().toString());
            st.setString(7, player.getPlayerName());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlayerModel get(String name) {
            try (Connection conn = dataSource.getConnection()) {
                String sql = "SELECT id, player_name, hp, strength, x, y FROM player WHERE player_name = ?";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, name);
                ResultSet rs = st.executeQuery();
                if (!rs.next()) {
                    return null;
                }
                PlayerModel data = new PlayerModel(rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
                data.setId(rs.getInt(1));
                return data;
            } catch (SQLException e) {
                throw new RuntimeException("Error while reading author with id: " + name, e);
            }
        }

    @Override
    public List<PlayerModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, player_name, hp, strength, x, y FROM player";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<PlayerModel> result = new ArrayList<>();
            while (rs.next()) {
                PlayerModel player = new PlayerModel(rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5), rs.getInt(6));
                player.setId(rs.getInt(1));
                result.add(player);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all players", e);
        }
    }
}
