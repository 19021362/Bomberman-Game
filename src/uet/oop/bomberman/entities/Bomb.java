package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadMap;

public class Bomb extends Entity {

    private int delay = 100;

    public Bomb(int x, int y, Image img, loadMap map) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if (status == 200) {
            remove(this);
        } else {
            status++;
            System.out.println(status);
            if (status % delay == 0) {
                this.img = Sprite.bomb.getFxImage();
            }
            if (status % delay == 20) {
                this.img = Sprite.bomb_1.getFxImage();
            }
            if (status % delay == 40) {
                this.img = Sprite.bomb_2.getFxImage();
            }
            if (status % delay == 60) {
                this.img = Sprite.bomb_1.getFxImage();
            }
            if (status % delay == 80) {
                this.img = Sprite.bomb.getFxImage();
            }
            if (status == 170) {
                this.img = Sprite.bomb_exploded.getFxImage();
            }
            if (status == 180) {
                this.img = Sprite.bomb_exploded1.getFxImage();
            }
            if (status == 190) {
                this.img = Sprite.bomb_exploded2.getFxImage();
            }
        }

    }

    protected void remove(Entity o) {
        this.img = Sprite.grass.getFxImage();
    }
}
