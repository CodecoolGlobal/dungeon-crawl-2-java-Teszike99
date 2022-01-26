package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.Items.Item;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private EnemyDao enemyDao;
    private ItemDao itemDao;
    private GameStateDao gameStateDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        enemyDao = new EnemyDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource);
        itemDao = new ItemDaoJdbc(dataSource);

    }

    public void savePlayer(PlayerModel model) {
        playerDao.add(model);
    }

    public PlayerModel loadPlayer(){
        PlayerModel player = playerDao.get(1);
        return player;
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = System.getenv("DB_NAME");
        String user = System.getenv("USER_NAME");
        String password = System.getenv("PASSWORD");

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }

    public void saveEnemy(Enemy enemy, GameState state) {
        EnemyModel model = new EnemyModel(enemy);
        enemyDao.add(model, state);
    }

    public void saveItem(Item item){
        ItemModel model = new ItemModel(item);
        itemDao.add(model);
    }


    public List<EnemyModel> loadEnemies() {
        List<EnemyModel> enemyList = enemyDao.getAll(1);
        return enemyList;
    }

    public void saveGameState(GameState state) {
        gameStateDao.add(state);
    }

    public List<ItemModel> loadItems(){
        List<ItemModel> itemModelList = itemDao.getAll(1);
        return itemModelList;
    };


}
