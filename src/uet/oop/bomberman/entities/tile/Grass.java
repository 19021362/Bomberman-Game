package uet.oop.bomberman.entities.tile;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.loadMap;

import javafx.scene.image.Image;

public class Grass extends Entity {

    public Grass(int x, int y, Image img) {
        super(x, y, img);
        setCanPass(true);
    }

    @Override
    public void update() {

    }

}
