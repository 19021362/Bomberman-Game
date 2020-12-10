package uet.oop.bomberman.entities.mob;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadMap;

import java.util.Random;

public class Oneal extends Mob {
    private int speed = 2;
    private int dir = 0;
    private int side_h = 1;     // Hướng chạy hiện tại. 1: Trái -> Phải. -1: Phải -> Trái.
    private int checkCollision = 0;
    private final Random rd = new Random();


    public Oneal(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if (blood > 0) {
            calculateDir();
            move();
            if (status % 50 == 0) {
                speed = Math.abs(rd.nextInt()) % 3 + 1;
                dir = Math.abs(rd.nextInt() % 3 +1);
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


    void calculateDir(){
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

        if (Math.abs(distanceX) < 1000 && Math.abs(distanceY) <= 4) {
            x += signOf(distanceX) * speed;
            status++;
            if (collision()) {
                x -= signOf(distanceX) * speed;
                checkCollision++;
            }
        } else if (Math.abs(distanceY) < 1000 && Math.abs(distanceX) <= 4) {
            y += signOf(distanceY) * speed;
            status++;
            if (collision()) {
                y -= signOf(distanceY) * speed;
                checkCollision++;
            }
        } else {
            randomMove();
        }
    }

    @Override
    public void kill() {
        img = Sprite.movingSprite(Sprite.oneal_dead, Sprite.mob_dead2, Sprite.mob_dead3, animation, 50).getFxImage();
    }

    public void randomMove() {
        //System.out.println(dir);
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

    public int signOf (int inputInt) {
        return Integer.compare(inputInt, 0);
    }
    
}

