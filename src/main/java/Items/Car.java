package Items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Car extends Item {


    public Car(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "car";
    }

}
