package uet.oop.bomberman.entities.mob;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.explosion.Bomb;
import uet.oop.bomberman.entities.explosion.Direction;
import uet.oop.bomberman.graphics.Sound;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadMap;

import java.util.List;

public class Bomber extends Mob {

    private final int maxSpeed = 8;
    private int speed = 2;
    private int dx = 0;
    private int dy = 0;

    private final int maxLength = 5;
    private int length_bomb = 1;
    private final int side = 1;                // Hướng chạy hiện tại. 1: Trái -> Phải. -1: Phải -> Trái

    private final int maxBomb = 5;
    private final int numBomb = 1;
    public static int recentBomb = 0;

    private final int maxBlood = 5;

    private int side_h = 1;              // Hướng chạy hiện tại. 1: Trái -> Phải. -1: Phải -> Trái
    private int side_v = 1;             // Hướng chạy hiện tại. 1: Trên -> Dưới. -1: Dưới -> Trên

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        setBlood(2);
    }

    @Override
    public void update() {
        if (blood > 0) {
            if (!collision()) {
                x += dx;
                y += dy;
            }
            if (side_h == 1) {                       //Nếu đang chạy trái sang phải thì dùng 3 ảnh dưới
                if (status % 9 == 0) {              //%9 thay vì %3 và status == 0 3 6 để giảm tốc độ cử động, muốn chậm hơn có thể % 27, ...( vẩy tay vẩy chân quá nhanh trong khi di chuyển chậm, nên sửa test status % 3 == 0, 1, 2 để hiểu hơn)
                    this.img = Sprite.player_right.getFxImage();
                } else if (status % 9 == 3) {
                    this.img = Sprite.player_right_1.getFxImage();
                } else if (status % 9 == 6) {
                    this.img = Sprite.player_right_2.getFxImage();
                }
            }

            if (side_h == -1) {                         //Nếu đang chạy phải sang trái thì dùng 3 ảnh dưới
                if (status % 9 == 0) {
                    this.img = Sprite.player_left.getFxImage();
                } else if (status % 9 == 3) {
                    this.img = Sprite.player_left_1.getFxImage();
                } else if (status % 9 == 6) {
                    this.img = Sprite.player_left_2.getFxImage();
                }
            }
            if (side_v == 1) {                       //Nếu đang chạy trên xuống dưới thì dùng 3 ảnh dưới
                if (status % 9 == 0) {              //%9 thay vì %3 và status == 0 3 6 để giảm tốc độ cử động, muốn chậm hơn có thể % 27, ...( vẩy tay vẩy chân quá nhanh trong khi di chuyển chậm, nên sửa test status % 3 == 0, 1, 2 để hiểu hơn)
                    this.img = Sprite.player_down.getFxImage();
                } else if (status % 9 == 3) {
                    this.img = Sprite.player_down_1.getFxImage();
                } else if (status % 9 == 6) {
                    this.img = Sprite.player_down_2.getFxImage();
                }
            }
            if (side_v == -1) {                         //Nếu đang chạy dưới lên trên thì dùng 3 ảnh dưới
                if (status % 9 == 0) {
                    this.img = Sprite.player_up.getFxImage();
                } else if (status % 9 == 3) {
                    this.img = Sprite.player_up_1.getFxImage();
                } else if (status % 9 == 6) {
                    this.img = Sprite.player_up_2.getFxImage();
                }
            }
            if (!live) {
                PlayMusic(Sound.new_fx);
                blood--;
                if (blood > 0) {
                    setLive(true);
                    setPosition(1, 1);
                }
            }
        } else {
            if (animation == 30) {
                PlayMusic(Sound.dead_fx);
                remove(this);
            } else {
                animation++;
                kill();
            }
        }

    }

    /**
     * Bắt xự kiện từ bàn phím
     * @param scene màn hình.
     */
    @Override
    public void move(Scene scene) {
        if (blood > 0) {
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    key_handle(event, speed);
                }
            });
            scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    KeyCode keyCode = event.getCode();
                    switch (keyCode) {
                        case RIGHT:
                        case LEFT:
                            status++;
                            dx = 0; break;
                        case UP:
                        case DOWN:
                            status++;
                            dy = 0; break;
                        default:
                            dx = 0;
                            dy = 0;
                    }
                }
            });
        }
    }

    private void key_handle(KeyEvent event, int speed) {
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
                dx = speed;
                break;
            case LEFT:
                side_v = 0;
                if (side_h == -1) {
                    status++;
                } else {
                    side_h = -1;
                    status = 0;
                }
                dx = -speed;
                break;
            case UP:
                side_h = 0;
                if (side_v == -1) {
                    status++;
                } else {
                    side_v = -1;
                    status = 0;
                }
                dy = -speed;
                break;
            case DOWN:
                side_h = 0;
                if (side_v == 1) {
                    status++;
                } else {
                    side_v = 1;
                    status = 0;
                }
                dy = speed;
                break;
            case SPACE:
                if(recentBomb < maxBomb){
                    recentBomb++;
                    creatBomb();
                    break;
                }
            default:
                check();
        }
    }

    private void check() {
        if (collision()) {
            speed = 0;
        }
    }

    private void creatBomb() {
        int _x = (x + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE;
        int _y = (y + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE;
        Entity bomb = new Bomb(_x, _y, Sprite.bomb.getFxImage(), this.x, this.y);
        Entity right = new Direction(_x, _y, null, length_bomb, 6);
        Entity left = new Direction(_x, _y, null, length_bomb, 4);
        Entity down = new Direction(_x, _y, null, length_bomb, 2);
        Entity top = new Direction(_x, _y, null, length_bomb, 8);
        loadMap.getStillObjects().add(bomb);
        loadMap.getStillObjects().add(right);
        loadMap.getStillObjects().add(left);
        loadMap.getStillObjects().add(down);
        loadMap.getStillObjects().add(top);
    }

    @Override
    public void kill() {
        img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, animation, 30).getFxImage();
    }

    @Override
    public boolean collision() {
        Rectangle rect = new Rectangle((this.x + 1 + dx), (this.y + 1 + dy), 20, 24);
        boolean check = false;
        for (Entity o : loadMap.getStillObjects()) {
            if (o instanceof Bomb) {
                check = false;
                break;
            } else {
                if (o!= null && !o.canPass && rect.intersects(o.getBound().getBoundsInParent())) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    public void boostLength_bomb() {
        if (this.length_bomb < maxLength) {
            this.length_bomb++;
        }
    }

    public void boostSpeed() {
        if (this.speed < maxSpeed) {
            this.speed += 1;
        }
    }

    @Override
    public void setBlood(int blood) {
        if (this.blood < maxBlood) {
            this.blood += blood;
        }
    }
}