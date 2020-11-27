package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.explosion.Bomb;
import uet.oop.bomberman.entities.explosion.Direction;
import uet.oop.bomberman.entities.explosion.Explosion;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadMap;

import java.util.List;
import java.util.Random;

public class Balloom extends Entity {
    private int speed = 2;
    private int dir = 0;
    private int side_h = 1;     // Hướng chạy hiện tại. 1: Trái -> Phải. -1: Phải -> Trái.
    private Random rd = new Random();
    public Balloom(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if(kill()){
            rendering();
            //loadMap.removeMob(this);
        }
        calculateDir();
        move();
    }


    void calculateDir(){
        if(side_h == 1) {                       //Nếu đang chạy trái sang phải thì dùng 3 ảnh dưới
            if (status % 9 == 0) {              //%9 thay vì %3 và status == 0 3 6 để giảm tốc độ cử động, muốn chậm hơn có thể % 27, ...( vẩy tay vẩy chân quá nhanh trong khi di chuyển chậm, nên sửa test status % 3 == 0, 1, 2 để hiểu hơn)
                this.img = Sprite.balloom_right1.getFxImage();
            } else if (status % 9 == 3) {
                this.img = Sprite.balloom_right2.getFxImage();
            } else if (status % 9 == 6){
                this.img = Sprite.balloom_right3.getFxImage();
            }
        }
        if(side_h == -1) {                       //Nếu đang chạy trái sang phải thì dùng 3 ảnh dưới
            if (status % 9 == 0) {              //%9 thay vì %3 và status == 0 3 6 để giảm tốc độ cử động, muốn chậm hơn có thể % 27, ...( vẩy tay vẩy chân quá nhanh trong khi di chuyển chậm, nên sửa test status % 3 == 0, 1, 2 để hiểu hơn)
                this.img = Sprite.balloom_left1.getFxImage();
            } else if (status % 9 == 3) {
                this.img = Sprite.balloom_left2.getFxImage();
            } else if (status % 9 == 6){
                this.img = Sprite.balloom_left3.getFxImage();
            }
        }
    }

    public void move() {
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
    public boolean kill(){
        for(Entity e:loadMap.getStillObjects()){
            if(e instanceof Direction && this.collision(e)){
                return true;
            }
        }
        return false;
    }
    public void rendering(){
        if (status == 150) {
            loadMap.removeMob(this);
        } else {
            status++;
            if(status>60){
                img = Sprite.balloom_dead.getFxImage();
            }
            }
    }
}