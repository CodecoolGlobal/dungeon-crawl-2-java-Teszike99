package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.Items.Item;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public void saveItem(Item item, GameState state){
        ItemModel ItemModel = new ItemModel(item);
        itemDao.add(ItemModel, state);
    }

    public PlayerModel loadPlayer(String loadedName){
        PlayerModel player = playerDao.get(loadedName);
        return player;
    }

    public List<EnemyModel> loadEnemies(int id) {
        List<EnemyModel> enemyList = enemyDao.getAll(id);
        return enemyList;
    }

    public List<ItemModel> loadItems(int id){
        List<ItemModel> itemModelList = itemDao.getAll(id);
        return itemModelList;
    };

    public void save(Player player, String currentMap, String saveName, List<Enemy> enemies, List<Item> items) {
        PlayerModel playerModel = new PlayerModel(player);
        playerModel.setPlayerName(saveName);
        playerDao.add(playerModel);
        GameState gameModel = new GameState(currentMap, playerModel);
        gameStateDao.add(gameModel);
        items.forEach(item -> saveItem(item, gameModel));
        enemies.forEach(enemy -> saveEnemy(enemy, gameModel));
    }

    public void load(String loadedName){

    }

    public Set<String> loadChoices() {
        return playerDao.getAll().stream().map(PlayerModel::getPlayerName).collect(Collectors.toSet());
    }


    public GameState loadMap(int id) {
        GameState gameState = gameStateDao.get(id);
        return gameState;

    }
}
