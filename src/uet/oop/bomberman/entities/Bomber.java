package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.entities.explosion.Bomb;
import uet.oop.bomberman.entities.explosion.Explosion;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadMap;

import java.util.List;

public class Bomber extends Entity {

    private int speed = 4;
                                         // Lấy Status % 3 để luân phiên lấy 3 giá trị 0 1 2, mỗi giá trị ứng với một tư thế chạy (1 ảnh)
    private int side = 1;                // Hướng chạy hiện tại. 1: Trái -> Phải. -1: Phải -> Trái
    //private int status = -1;              // Lấy Status % 3 để luân phiên lấy 3 giá trị 0 1 2, mỗi giá trị ứng với một tư thế chạy (1 ảnh)

    private int side_h = 1;              // Hướng chạy hiện tại. 1: Trái -> Phải. -1: Phải -> Trái
    private int side_v = 1;              // Hướng chạy hiện tại. 1: Trên -> Dưới. -1: Dưới -> Trên


    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if(side_h == 1) {                       //Nếu đang chạy trái sang phải thì dùng 3 ảnh dưới
            if (status % 9 == 0) {              //%9 thay vì %3 và status == 0 3 6 để giảm tốc độ cử động, muốn chậm hơn có thể % 27, ...( vẩy tay vẩy chân quá nhanh trong khi di chuyển chậm, nên sửa test status % 3 == 0, 1, 2 để hiểu hơn)
                this.img = Sprite.player_right.getFxImage();
            } else if (status % 9 == 3) {
                this.img = Sprite.player_right_1.getFxImage();
            } else if (status % 9 == 6){
                this.img = Sprite.player_right_2.getFxImage();
            }
        }

        if(side_h == -1) {                         //Nếu đang chạy phải sang trái thì dùng 3 ảnh dưới
            if (status % 9 == 0) {
                this.img = Sprite.player_left.getFxImage();
            } else if (status % 9 == 3) {
                this.img = Sprite.player_left_1.getFxImage();
            } else if (status % 9 == 6){
                this.img = Sprite.player_left_2.getFxImage();
            }
        }
        if(side_v == 1) {                       //Nếu đang chạy trên xuống dưới thì dùng 3 ảnh dưới
            if (status % 9 == 0) {              //%9 thay vì %3 và status == 0 3 6 để giảm tốc độ cử động, muốn chậm hơn có thể % 27, ...( vẩy tay vẩy chân quá nhanh trong khi di chuyển chậm, nên sửa test status % 3 == 0, 1, 2 để hiểu hơn)
                this.img = Sprite.player_down.getFxImage();
            } else if (status % 9 == 3) {
                this.img = Sprite.player_down_1.getFxImage();
            } else if (status % 9 == 6){
                this.img = Sprite.player_down_2.getFxImage();
            }
        }
        if(side_v == -1) {                         //Nếu đang chạy dưới lên trên thì dùng 3 ảnh dưới
            if (status % 9 == 0) {
                this.img = Sprite.player_up.getFxImage();
            } else if (status % 9 == 3) {
                this.img = Sprite.player_up_1.getFxImage();
            } else if (status % 9 == 6){
                this.img = Sprite.player_up_2.getFxImage();
            }
        }
    }

    /**
     * Bắt xự kiện từ bàn phím
     * @param scene màn hình.
     */
    @Override
    public void move(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                    KeyCode keyCode = event.getCode();
                    switch (keyCode) {
                        case RIGHT:
                            side_v = 0;
                            if (side_h == 1) {               //Không chuyển hướng: Giữ trạng thái
                                status++;
                            } else {                          //Chuyển hướng: Đặt về trạng thái, hướng vừa ấn
                                side_h = 1;
                                status = 0;
                            }
                            x += speed;
                            if (collision()) {
                                x -= speed;
                            }
                            break;
                        case LEFT:
                            side_v = 0;
                            if (side_h == -1) {
                                status++;
                            } else {
                                side_h = -1;
                                status = 0;
                            }
                            x -= speed;
                            if (collision()) {
                                x += speed;
                            }
                            break;
                        case UP:
                            side_h = 0;
                            if (side_v == -1) {
                                status++;
                            } else {
                                side_v = -1;
                                status = 0;
                            }
                            y -= speed;
                            if (collision()) {
                                y += speed;
                            }
                            break;
                        case DOWN:
                            side_h = 0;
                            if (side_v == 1) {
                                status++;
                            } else {
                                side_v = 1;
                                status = 0;
                            }
                            y += speed;
                            if (collision()) {
                                y -= speed;
                            }
                            break;
                        case SPACE:
                            Entity bomb = new Bomb((x + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE,
                                    (y + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE,
                                    Sprite.bomb.getFxImage());
                            Entity explosion = new Explosion((x + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE,
                            (y + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE,
                                    null);
                            loadMap.add(bomb);
                            loadMap.add(explosion);

                            break;
                        default:
                            check();
                    }
            }
        });
    }

    private void check() {
        if (collision()) {
            speed = 0;
        }
    }

    @Override
    public void kill() {

    }
}
