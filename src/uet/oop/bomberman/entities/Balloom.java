package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadMap;
import java.util.Random;

public class Balloom extends Entity {
    private int speed = 1;
    private int dir = 0;
    private int side_h = 1;     // Hướng chạy hiện tại. 1: Trái -> Phải. -1: Phải -> Trái
    private loadMap _map;
    private Random rd = new Random();
    public Balloom(int x, int y, Image img, loadMap map) {
        super( x, y, img);

        _map = map;
    }

    @Override
    public void update() {
        calculateDir();
        move(_map);
    }
    
    public void move(loadMap map) {
        switch (dir){
            case 0:
                if(side_h == 1) {status++;}
                else{
                    side_h = 1;
                    status = 0;
                }
                if((x+32) % 32 == 0 ){
                    if(canPass(0, map)){
                        x += speed;
                    }
                   else {dir = rd.nextInt(4);}
                }
                else{
                    x += speed;
                }
                break;
            case 1:
                status++;
                if(y % 32 == 0 ){
                    if(canPass(1, map)){
                        y += speed;
                    }
                    else{
                        dir = rd.nextInt(4);
                    }
                }
                else{
                    y += speed;
                }
                break;
            case 2:
                if(side_h == -1) {status++;}
                else{
                    side_h = -1;
                    status = 0;
                }
                if(x % 32 == 0 ){
                    if(canPass(2, map)){
                        x -= speed;
                    }
                    else {dir = rd.nextInt(4);}
                }
                else{
                    x -= speed;
                }
                break;
            case 3:
                status++;
                if(y % 32 == 0 ){
                    if(canPass(3, map)){
                        y -= speed;
                    }
                    else{
                        dir = rd.nextInt(4);
                    }
                }
                else{
                    y -= speed;
                }
                break;
        }
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

    boolean canPass(int direct, loadMap map){
        if(direct==0){
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
        if(direct==2){
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
        if(direct==3){
            if(x%32==0){
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
        if(direct==1){
            if(x%32==0){
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
