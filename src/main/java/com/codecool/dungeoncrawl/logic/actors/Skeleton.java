package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import java.util.Random;


public class Skeleton extends Actor {
    private static final Random rand = new Random();
    public Skeleton(Cell cell) {
        super(cell);
    }


    @Override
    public String getTileName() {
        return "skeleton";
    }


    public void move() {
        boolean movingPossible = true;
        while (movingPossible){
            int randomX = rand.nextInt(-1,2);
            int randomY = rand.nextInt(-1,2);
            movingPossible = checkMove(randomX, randomY, getCell().getActor());
        }
    }

}
