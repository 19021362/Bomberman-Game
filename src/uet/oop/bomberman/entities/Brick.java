package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.explosion.Direction;
import uet.oop.bomberman.entities.explosion.Explosion;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadMap;

import java.util.List;

public class Brick extends Entity {

    public Brick(int x, int y, Image img) {
        super(x, y, img);
        setCanPass(false);
    }

    @Override
    public void update() {
        if (collision()) {
            if (status == 150) {
                remove(this);
            } else {
                status++;
                if (status > 120) {
                    this.img = Sprite.brick_exploded.getFxImage();
                }
                if (status > 130) {
                    this.img = Sprite.brick_exploded1.getFxImage();
                }
                if (status > 140) {
                    this.img = Sprite.brick_exploded2.getFxImage();
                }
            }
        }
    }

    @Override
    public boolean collision() {
        boolean check = false;
        List<Entity> obj = loadMap.getStillObjects();
        for (Entity o : obj) {
            if (o instanceof Explosion && this.collision(o)) {
                check = true;
                break;
            }
        }
        return check;
    }
}
