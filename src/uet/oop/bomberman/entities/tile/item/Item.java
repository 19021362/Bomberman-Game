package uet.oop.bomberman.entities.tile.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.loadMap;

public abstract class Item extends Entity {

    private String power_fx = "C:\\Users\\ASUS\\Documents\\GitHub\\Bomberman-Game\\res\\sounds\\item.WAV";

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
        for (Entity o : loadMap.getMob()) {
            if (o instanceof Bomber && this.collision(o)) {
                check = true;
                PlayMusic(power_fx);
                break;
            }
        }
        return check;
    }

    public void power() {

    }


}
