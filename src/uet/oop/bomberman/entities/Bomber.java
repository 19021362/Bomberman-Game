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
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadMap;

import java.util.List;

public class Bomber extends Entity {
    private int speed = 8;
    private int side_h = 1;              // Hướng chạy hiện tại. 1: Trái -> Phải. -1: Phải -> Trái
    private int side_v = 1;              // Hướng chạy hiện tại. 1: Trên -> Dưới. -1: Dưới -> Trên


    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        calculateDir();
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
                        side_v = 0;
                        if(side_h == 1){               //Không chuyển hướng: Giữ trạng thái
                            status++;
                        }
                        else{                          //Chuyển hướng: Đặt về trạng thái, hướng vừa ấn
                            side_h = 1;
                            status = 0;
                        }
                        if((x+24) % 32 == 0 ){
                            if(canPass(1,0, map)){
                                x += speed;
                            }
                            else{
                                System.out.println("cant pass");
                            }
                        }
                        else{
                            x += speed;
                        }
                        System.out.println(x +" " + y);
                        break;
                    case LEFT:
                        side_v = 0;
                        if(side_h == -1){
                            status++;
                        }
                        else{
                            side_h = -1;
                            status = 0;
                        }
                        if(x % 32 == 0 ){
                            if(canPass(-1,0, map)){
                                x -= speed;
                            }
                            else{
                                System.out.println("cant pass");
                            }
                        }
                        else{
                            x -= speed;
                        }
                        System.out.println(x +" " + y);
                        break;
                    case UP:
                        side_h = 0;
                        if(side_v == -1){ status++;}
                        else{
                            side_v = -1;
                            status = 0;
                        }
                        if(y % 32 == 0 ){
                            if(canPass(0,-1, map)){
                                y -= speed;
                            }
                            else{
                                System.out.println("cant pass");
                            }
                        }
                        else{
                            y -= speed;
                        }
                        System.out.println(x +" " + y);
                        break;
                    case DOWN:
                        side_h = 0;
                        if(side_v == 1){
                            status++;
                        }
                        else{
                            side_v = 1;
                            status = 0;
                        }
                        System.out.println(x +" " + y);
                        if(y % 32 == 0 ){
                            if(canPass(0,1, map)){
                                y += speed;
                            }
                            else{
                                System.out.println("cant pass");
                            }
                        }
                        else{
                            y += speed;
                        }
                        break;
                    case SPACE:
                        Entity bomb = new Bomb((x + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE,
                                (y + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE,
                                Sprite.bomb.getFxImage(), map);
                        map.add(bomb);
                        break;
                }
            }
        });
    }

    void calculateDir(){
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
     * Cái này dùng tọa độ hơi phức tạp 1 chút.
     * -Chiều ngang : nếu bomber đang đứng khít trong 1 ô, xét xem ô tiếp theo hoặc trước đó
     * (tùy theo phím), nếu là grass thì cho đi tiếp. Nếu bomber đứng ở giữa 2 ô thì phải xét 2 ô tiếp
     * -Chiều dọc : cũng tương tự như chiều ngang nhưng vì bomber rộng 24 nên nó đi up down được ở 2 vị trí
     * @param h horizontal~ theo chiều ngang
     * @param v vertical~ theo chiều dọc
     * @param map bản đồ
     * @return
     */
    boolean canPass(int h, int v, loadMap map){
        if(h==1){
            if(y%32==0){
                Entity e = map.getStillObjects().get((int)(x/32) + 1 + (y/32)* BombermanGame.WIDTH);
                if(e instanceof Grass)                          {return true; }
                else                                            {return false;}
            }
            else{
                Entity e1 = map.getStillObjects().get((int)(x/32) + 1 + (y/32)* BombermanGame.WIDTH);
                Entity e2 = map.getStillObjects().get((int)(x/32) + 1 + (y/32+1)* BombermanGame.WIDTH);
                if(e1 instanceof Grass && e2 instanceof Grass)  {return true; }
                else                                            {return false;}
            }
        }
        if(h==-1){
            if(y%32==0){
                Entity e = map.getStillObjects().get((int)(x/32) - 1 + (y/32)* BombermanGame.WIDTH);
                if(e instanceof Grass)                          {return true; }
                else                                            {return false;}
            }
            else{
                Entity e1 = map.getStillObjects().get((int)(x/32) -1 + (y/32)* BombermanGame.WIDTH);
                Entity e2 = map.getStillObjects().get((int)(x/32) - 1 + (y/32+1)* BombermanGame.WIDTH);
                if(e1 instanceof Grass && e2 instanceof Grass)  {return true; }
                else                                            {return false;}
            }
        }
        if(v==-1){
            if(x%32==0 || x%32==8){
                Entity e = map.getStillObjects().get((int)(x/32) + (y/32-1)* BombermanGame.WIDTH);
                if(e instanceof Grass)                          {return true; }
                else                                             {return false;}
            }
            else{
                Entity e1 = map.getStillObjects().get((int)(x/32) + 1 + (y/32-1)* BombermanGame.WIDTH);
                Entity e2 = map.getStillObjects().get((int)(x/32)  + (y/32-1)* BombermanGame.WIDTH);
                if(e1 instanceof Grass && e2 instanceof Grass)  {return true; }
                else                                            {return false;}
            }
        }
        if(v==1){
            if(x%32==0 || x%32==8){
                Entity e = map.getStillObjects().get((int)(x/32) + (y/32+1)* BombermanGame.WIDTH);
                if(e instanceof Grass)                          {return true; }
                else                                             {return false;}
            }
            else{
                Entity e1 = map.getStillObjects().get((int)(x/32) + 1 + (y/32+1)* BombermanGame.WIDTH);
                Entity e2 = map.getStillObjects().get((int)(x/32)  + (y/32+1)* BombermanGame.WIDTH);
                if(e1 instanceof Grass && e2 instanceof Grass)  {return true; }
                else                                            {return false;}
            }
        }
        return false;
    }
}
