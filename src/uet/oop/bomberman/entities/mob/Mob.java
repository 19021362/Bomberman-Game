package uet.oop.bomberman.entities.mob;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.explosion.Explosion;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadMap;

import java.util.List;
import java.util.Random;

public abstract class Mob extends Entity {
    protected int animation = 0;
    protected int blood = 1;
    protected boolean live = true;

    protected int speed = 2;
    protected int dir = 0;
    protected int side_h = 1;     // Hướng chạy hiện tại. 1: Trái -> Phải. -1: Phải -> Trái.
    protected final Random rd = new Random();

    public Mob(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void move(Scene scene) {}

    protected void randomMove() {
        switch (dir){
            case 0:
                if(side_h == 1) {status++;}
                else{
                    side_h = 1;
                    status = 0;
                }
                x += speed;
                if (collision()) {
                    x -= speed;
                    dir = Math.abs(rd.nextInt() % 4);
                }
                break;
            case 1:
                status++;
                y += speed;
                if (collision()) {
                    y -= speed;
                    dir = Math.abs(rd.nextInt() % 4);
                }
                break;
            case 2:
                if(side_h == -1) {status++;}
                else{
                    side_h = -1;
                    status = 0;
                }
                x -= speed;
                if (collision()) {
                    x += speed;
                    dir = Math.abs(rd.nextInt() % 4);
                }
                break;
            case 3:
                status++;
                y -= speed;
                if (collision()) {
                    y += speed;
                    dir = Math.abs(rd.nextInt() % 4);
                }
                break;
        }
    }

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
