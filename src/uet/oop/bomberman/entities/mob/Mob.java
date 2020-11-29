package uet.oop.bomberman.entities.mob;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.explosion.Explosion;
import uet.oop.bomberman.loadMap;

import java.util.List;

public abstract class Mob extends Entity {
    protected int animation = 0;
    protected int blood = 1;
    protected boolean live = true;

    public Mob(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void move(Scene scene) {}

    @Override
    public boolean collision() {
        boolean check = false;
        for (Entity e : loadMap.getMob()) {
            if (e instanceof Bomber && this.collision(e)) {
                ((Bomber) e).setLive(false);
            }
        }

        for (Entity o : loadMap.getStillObjects()) {
            if (o != null && !o.canPass && this.collision(o)) {
                check = true;
                break;
            }
        }
        return check;
    }

    public void kill() {

    }

    public void setBlood(int blood) {
        this.blood += blood;
    }

    public int getBlood() {
        return blood;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean getLive() {
        return this.live;
    }

}
