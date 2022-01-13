package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import java.util.Random;


public class Skeleton extends Enemy {

    int health;
    int strength = 1;
    private static final Random rand = new Random();

    public Skeleton(Cell cell) {
        super(cell);
        health = 5;
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    @Override
    public void move() {
        Cell randomMove = getRandomMove();
        Actor player = randomMove.getActor();
        if (checkEmptyField(randomMove.getType())){
            if (checkAttack(player)){
                attack(this);
            }else {
                move(randomMove);
            }
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

    private Cell getRandomMove() {
        boolean movingPossible = true;
        Cell randomMove = null;
        while (movingPossible){
            int randomX = rand.nextInt(-1,2);
            int randomY = rand.nextInt(-1,2);
            Cell skeletonCell = this.getCell();
            randomMove = skeletonCell.getNeighbor(randomX, randomY);
            movingPossible = moveValidation(randomMove.getType(), randomMove.getActor());
        }
        return randomMove;
    }

    private boolean moveValidation(CellType typeOfTile, Actor actor){;
        if (checkEmptyField(typeOfTile) && !(actor instanceof Enemy)) {
            return false;
        }else {
            return true;
        }
    }
}
