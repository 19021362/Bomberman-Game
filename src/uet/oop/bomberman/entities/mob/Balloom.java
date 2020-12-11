package uet.oop.bomberman.entities.mob;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.explosion.Bomb;
import uet.oop.bomberman.entities.explosion.Explosion;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadMap;

import java.util.List;
import java.util.Random;

public class Balloom extends Mob {

    public Balloom(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if (blood > 0) {
            calculateDir();
            randomMove();
            if (status % 50 == 0) {
                speed = Math.abs(rd.nextInt()) % 3 + 1;
            }
            if (!live) {
                blood--;
                setLive(true);
            }
        } else {
            if (animation == 30) {
                remove(this);
                loadMap.setNumEnemy(-1);
            } else {
                animation++;
                kill();
            }
        }
    }



    private void calculateDir(){
        if (side_h == 1) {                       //Nếu đang chạy trái sang phải thì dùng 3 ảnh dưới
            if (status % 9 == 0) {              //%9 thay vì %3 và status == 0 3 6 để giảm tốc độ cử động, muốn chậm hơn có thể % 27, ...( vẩy tay vẩy chân quá nhanh trong khi di chuyển chậm, nên sửa test status % 3 == 0, 1, 2 để hiểu hơn)
                this.img = Sprite.balloom_right1.getFxImage();
            } else if (status % 9 == 3) {
                this.img = Sprite.balloom_right2.getFxImage();
            } else if (status % 9 == 6){
                this.img = Sprite.balloom_right3.getFxImage();
            }
        }
        if (side_h == -1) {                       //Nếu đang chạy trái sang phải thì dùng 3 ảnh dưới
            if (status % 9 == 0) {              //%9 thay vì %3 và status == 0 3 6 để giảm tốc độ cử động, muốn chậm hơn có thể % 27, ...( vẩy tay vẩy chân quá nhanh trong khi di chuyển chậm, nên sửa test status % 3 == 0, 1, 2 để hiểu hơn)
                this.img = Sprite.balloom_left1.getFxImage();
            } else if (status % 9 == 3) {
                this.img = Sprite.balloom_left2.getFxImage();
            } else if (status % 9 == 6){
                this.img = Sprite.balloom_left3.getFxImage();
            }
        }
    }



    @Override
    public void kill() {
        img = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animation, 30).getFxImage();
    }
}