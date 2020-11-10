package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadMap;

public class Bomber extends Entity {

    private final int speed = 5;
    private int status = 0;              // Lấy Status % 3 để luân phiên lấy 3 giá trị 0 1 2, mỗi giá trị ứng với một tư thế chạy (1 ảnh)
    private int side = 1;                // Hướng chạy hiện tại. 1: Trái -> Phải. -1: Phải -> Trái

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if(side == 1) {                         //Nếu đang chạy trái sang phải thì dùng 3 ảnh dưới
            if (status % 45 == 0) {              //%9 thay vì %3 và status == 0 3 6 để giảm tốc độ cử động, muốn chậm hơn có thể % 27, ...( vẩy tay vẩy chân quá nhanh trong khi di chuyển chậm, nên sửa test status % 3 == 0, 1, 2 để hiểu hơn)
                this.img = Sprite.player_right.getFxImage();
            } else if (status % 45 == 15) {
                this.img = Sprite.player_right_1.getFxImage();
            } else if (status % 45 == 30){
                this.img = Sprite.player_right_2.getFxImage();
            }
        }

        if(side == -1) {                         //Nếu đang chạy phải sang trái thì dùng 3 ảnh dưới
            if (status % 45 == 0) {
                this.img = Sprite.player_left.getFxImage();
            } else if (status % 45 == 15) {
                this.img = Sprite.player_left_1.getFxImage();
            } else if (status % 45 == 30){
                this.img = Sprite.player_left_2.getFxImage();
            }
        }

        status += 1;
    }

    /**
     * Bắt xự kiện từ bàn phím
     * @param scene màn hình.
     * @param map bản đồ để khi ấn SPACE thì đặt bom.
     */
    @Override
    public void move(Scene scene, loadMap map) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode keyCode = event.getCode();
                switch (keyCode) {
                    case RIGHT:
                        side = 1;
                        x += speed;
                        check();
                        break;
                    case LEFT:
                        side = -1;
                        x -= speed;
                        check();
                        break;
                    case UP:
                        y -= speed;
                        check();
                        break;
                    case DOWN:
                        y += speed;
                        check();
                        break;
                    case SPACE:
                        Entity bomb = new Bomb(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE,
                                Sprite.bomb.getFxImage());
                        map.add(bomb);
                        break;
                    default:
                        check();
                }
            }
        });
    }

    /**
     * check không cho vật thể đi ra ngoài màn hình.
     */
    private void check() {
        if (x >= (640 - 64)) {
            x = 640 - 64;
        }
        if (x <= 32) {
            x = 32;
        }
        if (y >= 480 - 64) {
            y = 480 - 64;
        }
        if (y <= 32) {
            y = 32;
        }
    }
}
