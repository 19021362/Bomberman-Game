package uet.oop.bomberman.entities.mob;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadMap;

import java.util.Random;

public class Oneal extends Mob {

    public Oneal(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if (blood > 0) {
            calculateDir();
            move();
            if (status % 50 == 0) {
                speed = Math.abs(rd.nextInt()) % 3 + 2;
                //dir = Math.abs(rd.nextInt() % 4);
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
        if(side_h == 1) {                       //Nếu đang chạy trái sang phải thì dùng 3 ảnh dưới
            if (status % 27 == 0) {              //%9 thay vì %3 và status == 0 3 6 để giảm tốc độ cử động, muốn chậm hơn có thể % 27, ...( vẩy tay vẩy chân quá nhanh trong khi di chuyển chậm, nên sửa test status % 3 == 0, 1, 2 để hiểu hơn)
                this.img = Sprite.oneal_right1.getFxImage();
            } else if (status % 27 == 9) {
                this.img = Sprite.oneal_right2.getFxImage();
            } else if (status % 27 == 18){
                //this.img = Sprite.oneal_right3.getFxImage();
            }
        }
        if(side_h == -1) {                       //Nếu đang chạy trái sang phải thì dùng 3 ảnh dưới
            if (status % 27 == 0) {              //%9 thay vì %3 và status == 0 3 6 để giảm tốc độ cử động, muốn chậm hơn có thể % 27, ...( vẩy tay vẩy chân quá nhanh trong khi di chuyển chậm, nên sửa test status % 3 == 0, 1, 2 để hiểu hơn)
                this.img = Sprite.oneal_left1.getFxImage();
            } else if (status % 27 == 9) {
                this.img = Sprite.oneal_left2.getFxImage();
            } else if (status % 27 == 18){
                this.img = Sprite.oneal_left3.getFxImage();
            }
        }
    }

    public void move() {
        int distanceX = loadMap.getBomber1().getX() - x;
        int distanceY = loadMap.getBomber1().getY() - y;

        if (Math.abs(distanceX) < 300 && Math.abs(distanceY) <= 4) {
            x += signOf(distanceX) * speed;
            status++;
            if (collision()) {
                x -= signOf(distanceX) * speed;
                randomMove();
            }
        } else if (Math.abs(distanceY) < 300 && Math.abs(distanceX) <= 4) {
            y += signOf(distanceY) * speed;
            status++;
            if (collision()) {
                y -= signOf(distanceY) * speed;
                randomMove();
            }
        } else {
            randomMove();
        }
    }

    @Override
    public void kill() {
        img = Sprite.movingSprite(Sprite.oneal_dead, Sprite.mob_dead2, Sprite.mob_dead3, animation, 50).getFxImage();
    }

    public int signOf (int inputInt) {
        return Integer.compare(inputInt, 0);
    }

}

