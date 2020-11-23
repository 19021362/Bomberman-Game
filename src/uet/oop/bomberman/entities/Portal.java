package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.loadMap;


public class Portal extends Entity {
    public Portal(int x, int y, Image img) {
        super(x, y, img);
        setCanPass(false);
    }

    @Override
    public void update() {

    }


}
