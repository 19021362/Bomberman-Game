package uet.oop.bomberman.entities.tile.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.loadMap;

import java.util.List;

public class Live_item extends Item {
    public Live_item(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void power() {
        for (int i = 0; i < loadMap.getMob().size(); i++) {
            if (loadMap.getMob().get(i) instanceof Bomber) {
                ((Bomber) loadMap.getMob().get(i)).setBlood(1);
            }
        }
    }
}
