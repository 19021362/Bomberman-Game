package uet.oop.bomberman.entities.explosion;

import com.sun.prism.Graphics;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadMap;

public class Bomb extends Entity {

    private int delay = 30;

    private GraphicsContext graphicsContext;

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        setCanPass(true);
    }



    @Override
    public void update() {
        if (status == 120) {
            remove(this);
        } else {
            status++;
            //System.out.println(status);
            if (status % delay == 0) {
                this.img = Sprite.bomb.getFxImage();
            }
            if (status % delay == 10) {
                this.img = Sprite.bomb_1.getFxImage();
            }
            if (status % delay == 20) {
                this.img = Sprite.bomb_2.getFxImage();
            }
        }

    }

}
