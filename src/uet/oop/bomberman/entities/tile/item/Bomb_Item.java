package uet.oop.bomberman.entities.tile.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.loadMap;

import java.util.List;

public class Bomb_Item extends Item {
    public Bomb_Item(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void power() {
        List<Entity> list = loadMap.getMob();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof Bomber) {
                ((Bomber) list.get(i)).boostBomb();
            }
        }
    }
}
