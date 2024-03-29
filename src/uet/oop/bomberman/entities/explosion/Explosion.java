package uet.oop.bomberman.entities.explosion;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Mob;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadMap;

import java.util.List;


public class Explosion extends Entity{
    private Sprite[] sprites = new Sprite[3];
    private boolean explode_rl = false;

    public Explosion(int x, int y, Image img) {
        super(x, y, img);
        setCanPass(true);
    }

    public Explosion(int x, int y, Image img, int dir, boolean last) {
        super(x, y, img);
        setCanPass(true);
        switch (dir) {
            case 6:
                if(!last) {
                    sprites[0] = Sprite.explosion_horizontal;
                    sprites[1] = Sprite.explosion_horizontal1;
                    sprites[2] = Sprite.explosion_horizontal2;
                } else {
                    sprites[0] = Sprite.explosion_horizontal_right_last;
                    sprites[1] = Sprite.explosion_horizontal_right_last1;
                    sprites[2] = Sprite.explosion_horizontal_right_last2;
                }
                break;
            case 4:
                if(!last) {
                    sprites[0] = Sprite.explosion_horizontal;
                    sprites[1] = Sprite.explosion_horizontal1;
                    sprites[2] = Sprite.explosion_horizontal2;
                } else {
                    sprites[0] = Sprite.explosion_horizontal_left_last;
                    sprites[1] = Sprite.explosion_horizontal_left_last1;
                    sprites[2] = Sprite.explosion_horizontal_left_last2;
                }
                break;
            case 2:
                if(!last) {
                    sprites[0] = Sprite.explosion_vertical;
                    sprites[1] = Sprite.explosion_vertical1;
                    sprites[2] = Sprite.explosion_vertical2;
                } else {
                    sprites[0] = Sprite.explosion_vertical_down_last;
                    sprites[1] = Sprite.explosion_vertical_down_last1;
                    sprites[2] = Sprite.explosion_vertical_down_last2;
                }
                break;
            case 8:
                if(!last) {
                    sprites[0] = Sprite.explosion_vertical;
                    sprites[1] = Sprite.explosion_vertical1;
                    sprites[2] = Sprite.explosion_vertical2;
                } else {
                    sprites[0] = Sprite.explosion_vertical_top_last;
                    sprites[1] = Sprite.explosion_vertical_top_last1;
                    sprites[2] = Sprite.explosion_vertical_top_last2;
                }
                break;
        }
    }


    @Override
    public void update() {
        if (status == 150) {
            remove(this);
        } else {
            status++;
            if (status > 120) {
                explode_rl = true;
                this.img = sprites[0].getFxImage();
            }
            if (status > 130) {
                this.img = sprites[1].getFxImage();
            }
            if (status > 140) {
                this.img = sprites[2].getFxImage();
            }
            exp_collision();
        }
    }

    public boolean isExplode_rl() {
        return explode_rl;
    }

    public void exp_collision() {
        for (Entity o : loadMap.getMob()) {
            if (explode_rl) {
                if (o instanceof Mob && this.collision(o)) {
                    ((Mob) o).setLive(false);
                }
            } else {
                break;
            }
        }
    }


}
