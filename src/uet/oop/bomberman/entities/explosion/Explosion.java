package uet.oop.bomberman.entities.explosion;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadMap;

public class Explosion extends Bomb{
    protected int length = 2;

    public Explosion(int x, int y, Image img) {
        super(x, y, img);
        setCanPass(true);
    }

    public Explosion(int x, int y, Image img, int length) {
        super(x, y, img);
        this.length = length;
        setCanPass(true);
    }


    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        if (status == 150 ) {
            remove(this);
        } else {
            status++;
            for (int i = 1; i < length; i++) {
                if (status >= 120) {
                    gc.drawImage(Sprite.explosion_horizontal.getFxImage(), x + i * 32, y);
                    gc.drawImage(Sprite.explosion_horizontal.getFxImage(), x - i * 32, y);
                    gc.drawImage(Sprite.explosion_vertical.getFxImage(), x, y + i * 32);
                    gc.drawImage(Sprite.explosion_vertical.getFxImage(), x, y - i * 32);
                }
                if (status >= 130) {
                    gc.drawImage(Sprite.explosion_horizontal1.getFxImage(), x + i * 32, y);
                    gc.drawImage(Sprite.explosion_horizontal1.getFxImage(), x - i * 32, y);
                    gc.drawImage(Sprite.explosion_vertical1.getFxImage(), x, y + i * 32);
                    gc.drawImage(Sprite.explosion_vertical1.getFxImage(), x, y - i * 32);
                }
                if (status >= 140) {
                    gc.drawImage(Sprite.explosion_horizontal2.getFxImage(), x + i * 32, y);
                    gc.drawImage(Sprite.explosion_horizontal2.getFxImage(), x - i * 32, y);
                    gc.drawImage(Sprite.explosion_vertical2.getFxImage(), x, y + i * 32);
                    gc.drawImage(Sprite.explosion_vertical2.getFxImage(), x, y - i * 32);
                }
            }
            if (status >= 120) {
                gc.drawImage(Sprite.bomb_exploded.getFxImage(), x, y);
                gc.drawImage(Sprite.explosion_horizontal_right_last.getFxImage(), x + length * 32, y);
                gc.drawImage(Sprite.explosion_horizontal_left_last.getFxImage(), x - length * 32, y);
                gc.drawImage(Sprite.explosion_vertical_down_last.getFxImage(), x, y + length * 32);
                gc.drawImage(Sprite.explosion_vertical_top_last.getFxImage(), x, y - length * 32);
            }
            if (status >= 130) {
                gc.drawImage(Sprite.bomb_exploded1.getFxImage(), x, y);
                gc.drawImage(Sprite.explosion_horizontal_right_last1.getFxImage(), x + length * 32, y);
                gc.drawImage(Sprite.explosion_horizontal_left_last1.getFxImage(), x - length * 32, y);
                gc.drawImage(Sprite.explosion_vertical_down_last1.getFxImage(), x, y + length * 32);
                gc.drawImage(Sprite.explosion_vertical_top_last1.getFxImage(), x, y - length * 32);
            }
            if (status >= 140) {
                gc.drawImage(Sprite.bomb_exploded2.getFxImage(), x, y);
                gc.drawImage(Sprite.explosion_horizontal_right_last2.getFxImage(), x + length * 32, y);
                gc.drawImage(Sprite.explosion_horizontal_left_last2.getFxImage(), x - length * 32, y);
                gc.drawImage(Sprite.explosion_vertical_down_last2.getFxImage(), x, y + length * 32);
                gc.drawImage(Sprite.explosion_vertical_top_last2.getFxImage(), x, y - length * 32);
            }

        }
    }


    @Override
    public boolean collision(Entity o) {
        boolean check = false;
        if (o.getBound().intersects((x - length * 32), y, (length * 2 * 32 + 32), 32)) {
            check = true;
        }
        if (o.getBound().intersects(x, (y - length * 32), 32, (length * 32 * 2 + 32))) {
            check = true;
        }
        return check;
    }
}
