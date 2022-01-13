package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Ghost extends Enemy{

    int moveX;
    int moveY;
    int diffY;
    int diffX;

    public Ghost(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "ghost";
    }

    @Override
    public void move() {
        getPositions();
        countDifferences();
        Cell nextCell = this.getCell().getNeighbor(moveX, moveY);
        if (checkAttack(nextCell.getType(), nextCell.getActor())){
            attack(this);
        }else{
            move(nextCell);
        }

    }

    private void getPositions() {
        int ghostX = this.getX();
        int ghostY = this.getY();
        int playerX = this.getCell().getGameMap().getPlayer().getX();
        int playerY = this.getCell().getGameMap().getPlayer().getY();
        diffX = ghostX - playerX;
        diffY = ghostY - playerY;
    }

    private void countDifferences() {
        int differY = checkNegative(diffY);
        int differX = checkNegative(diffX);
        if (differX < differY){
            moveX = 0;
            moveY = diffY < 0 ? 1 : -1;
        }else{
            moveY = 0;
            moveX = diffX < 0 ? 1 : -1;
        }
    }

    private int checkNegative(int diff){
        if (diff < 0){
           return diff * -1;
        }else{
            return diff;
        }
    }
}
