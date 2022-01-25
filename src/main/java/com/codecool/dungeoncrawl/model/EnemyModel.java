package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Enemy;

public class EnemyModel extends BaseModel {

    private String name;
    private int x;
    private int y;
    private int hp;
    private int strength;

    public EnemyModel(String name, int strength, int hp, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.strength = strength;
    }

    public EnemyModel(Enemy enemy) {
        this.name = enemy.getTileName();
        this.x = enemy.getX();
        this.y = enemy.getY();
        this.hp = enemy.getHealth();
        this.strength = enemy.getStrength();
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHp() {
        return hp;
    }

    public int getStrength() {
        return strength;
    }
}
