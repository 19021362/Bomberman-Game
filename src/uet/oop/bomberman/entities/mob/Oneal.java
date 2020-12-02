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

public class Oneal extends Mob {
    private int speed = 1;
    private int dir = 0;
    private int side_h = 1;     // Hướng chạy hiện tại. 1: Trái -> Phải. -1: Phải -> Trái.
    private Random rd = new Random();
    public Oneal(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if (blood > 0) {
            calculateDir();
            move();
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


    void calculateDir(){
        if(side_h == 1) {                       //Nếu đang chạy trái sang phải thì dùng 3 ảnh dưới
            if (status % 9 == 0) {              //%9 thay vì %3 và status == 0 3 6 để giảm tốc độ cử động, muốn chậm hơn có thể % 27, ...( vẩy tay vẩy chân quá nhanh trong khi di chuyển chậm, nên sửa test status % 3 == 0, 1, 2 để hiểu hơn)
                this.img = Sprite.oneal_right1.getFxImage();
            } else if (status % 9 == 3) {
                this.img = Sprite.oneal_right2.getFxImage();
            } else if (status % 9 == 6){
                this.img = Sprite.oneal_right3.getFxImage();
            }
        }
        if(side_h == -1) {                       //Nếu đang chạy trái sang phải thì dùng 3 ảnh dưới
            if (status % 9 == 0) {              //%9 thay vì %3 và status == 0 3 6 để giảm tốc độ cử động, muốn chậm hơn có thể % 27, ...( vẩy tay vẩy chân quá nhanh trong khi di chuyển chậm, nên sửa test status % 3 == 0, 1, 2 để hiểu hơn)
                this.img = Sprite.oneal_left1.getFxImage();
            } else if (status % 9 == 3) {
                this.img = Sprite.oneal_left2.getFxImage();
            } else if (status % 9 == 6){
                this.img = Sprite.oneal_left3.getFxImage();
            }
        }
    }

    public void move() {
        // Đuổi theo
        if(status % 20 == 0) {
            System.out.println(loadMap.mob.get(0).getX() + " " + x);
            System.out.println(loadMap.mob.get(0).getY() + " " + y);
            System.out.println("\n");
        }

        boolean chasingBomber = chaseTheBomber();

        if (chasingBomber == false) {
            switch (dir){       // Khi va cham wall, brick, bomb thi chuyen huong di chuyen ngau nhien
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

        if (status % 50 == 0) {  // Tăng tốc chạy bất thường
            speed = Math.abs(rd.nextInt()) % 3 + 1;
        }
    }

    @Override
    public void kill() {
        img = Sprite.movingSprite(Sprite.oneal_dead, Sprite.mob_dead2, Sprite.mob_dead3, animation, 50).getFxImage();
    }

    public boolean chaseTheBomber() {
        status++;
        boolean chasing = false;
        if (Math.abs(loadMap.mob.get(0).getX() - x) < 35) { // Nếu cùng hàng ngang với bomber; 35: Chiều rộng tìm kiếm bomber
            chasing = true;
            if (loadMap.mob.get(0).getY() > y) { // Nếu bomber ở bên phải ( so với Oneal
                y += speed;
                if (collision()) {
                    y -= speed;
                }
            } else if (loadMap.mob.get(0).getY() < y) { // Nếu bomber ở bên trái
                y -= speed;
                if (collision()) {
                    y += speed;
                }
            }
        }
        if (Math.abs(loadMap.mob.get(0).getY() - y) < 35) { // Nếu cùng hàng dọc với bomber
            chasing = true;
            if (loadMap.mob.get(0).getX() > x) { // Nếu bomber ở bên trên
                x += speed;
                if (collision()) {
                    x -= speed;
                }
            } else if (loadMap.mob.get(0).getX() < x) { // Nếu bomber ở bên dưới
                x -= speed;
                if (collision()) {
                    x += speed;
                }
            }
        }
        return chasing;
    }



}
