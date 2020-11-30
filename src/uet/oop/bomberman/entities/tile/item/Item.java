package uet.oop.bomberman.entities.tile.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.graphics.Sound;
import uet.oop.bomberman.loadMap;

public abstract class Item extends Entity {


    public Item(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (collision()) {
            power();
            remove(this);
        }
    }

    @Override
    public boolean collision() {
        boolean check = false;
        if (isActive()) {
            for (Entity o : loadMap.getMob()) {
                if (o instanceof Bomber && this.collision(o)) {
                    check = true;
                    PlayMusic(Sound.power_fx);
                    break;
                }
            }
        }
        return check;
    }

    public void power() {

    }

    private boolean isActive() {
        boolean active = false;
        int i = loadMap.getStillObjects().indexOf(this) + 1;
        if (loadMap.getStillObjects().get(i) == null) {
            active = true;
        }
        return active;
    }


}
