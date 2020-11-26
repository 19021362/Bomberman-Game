package uet.oop.bomberman.entities.tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.loadMap;

public class Wall extends Entity {

    public Wall(int x, int y, Image img) {
        super(x, y, img);
        setCanPass(false);
    }

    @Override
    public void update() {

    }

}
