package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.Random;


public class Skeleton extends Enemy {
    private static final Random rand = new Random();
    public Skeleton(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    @Override
    public void move() {
        boolean movingPossible = true;
        Cell skeletonCell;
        Cell cellNextToSkeleton = null;
        CellType typeOfTile = null;
        Actor player = null;
        while (movingPossible){
            int randomX = rand.nextInt(-1,2);
            int randomY = rand.nextInt(-1,2);
            skeletonCell = this.getCell();
            cellNextToSkeleton = skeletonCell.getNeighbor(randomX, randomY);
            typeOfTile = cellNextToSkeleton.getType();
            player = cellNextToSkeleton.getActor();
            movingPossible = checkSkeletonMove(typeOfTile, player);
        }
        if (checkEmptyField(typeOfTile, player)){
            putActorOnMap(cellNextToSkeleton);
        }else if(checkAttack(typeOfTile, player, "player", this, "skeleton")){
            attack(player);
        }
    }

    private boolean checkSkeletonMove(CellType typeOfTile, Actor player){;
        if (checkEmptyField(typeOfTile, player)) {
            return false;
        }else if(checkAttack(typeOfTile, player, "player", this, "skeleton")) {
            return false;
        }else{
            return true;
        }
    }
}
