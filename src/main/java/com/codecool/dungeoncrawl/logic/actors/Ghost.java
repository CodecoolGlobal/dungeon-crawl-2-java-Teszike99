package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Ghost extends Enemy{
    Player player;
    int moveX;
    int moveY;
    int strength = 2;
    int health;

    public Ghost(Cell cell) {
        super(cell);
        health = 10;
    }

    @Override
    public String getTileName() {
        return "ghost";
    }

    @Override
    public void move() {
        getPositions();
        Cell nextCell = this.getCell().getNeighbor(moveX, moveY);
        if (checkAttack(nextCell.getActor())){
            attack(this);
        }else{
            move(nextCell);
        }

    }

    @Override
    protected int getStrength() {
        return strength;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    protected void setHealth(int newHealth) {
        health = newHealth;
    }

    private void getPositions() {
        player = this.getCell().getGameMap().getPlayer();
        int differenceX = this.getX() - player.getX();
        int differenceY= this.getY() - player.getY();
        if (changeToPositive(differenceX) < changeToPositive(differenceY)){
            moveX = 0;
            moveY = differenceY < 0 ? 1 : -1;
        }else{
            moveY = 0;
            moveX = differenceX < 0 ? 1 : -1;
        }
    }


    private int changeToPositive(int diff){
        if (diff < 0){
           return diff * -1;
        }else{
            return diff;
        }
    }
}
