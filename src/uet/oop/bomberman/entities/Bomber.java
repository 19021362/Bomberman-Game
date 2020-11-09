package uet.oop.bomberman.entities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Scanner;

public class Bomber extends Entity{

    int a = 20;                   
    int status = 0;              // Lấy Status % 3 để luân phiên lấy 3 giá trị 0 1 2, mỗi giá trị ứng với một tư thế chạy (1 ảnh)
    int side = 1;                // Hướng chạy hiện tại. 1: Trái -> Phải. -1: Phải -> Trái

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if(x == 52) {                   // Chạy trái sang phải
            a = 1;                      // Khoảng cách bước di chuyển
            side = 1;
        }

        if(x == 412) {                  // Chạy được đến đây thì quay đầu chạy phải sang trái
            a = -1;													
            side = -1;
        }

        if(side == 1) {                         //Nếu đang chạy trái sang phải thì dùng 3 ảnh dưới
            if (status % 9 == 0) {              //%9 thay vì %3 và status == 0 3 6 để giảm tốc độ cử động, muốn chậm hơn có thể % 27, ...( vẩy tay vẩy chân quá nhanh trong khi di chuyển chậm, nên sửa test status % 3 == 0, 1, 2 để hiểu hơn)
                this.img = Sprite.player_right.getFxImage();
                System.out.println("A");
            } else if (status % 9 == 3) {
                this.img = Sprite.player_right_1.getFxImage();
                System.out.println("B");
            } else if (status % 9 == 6){
                this.img = Sprite.player_right_2.getFxImage();
                System.out.println("C");
            }
        }

        if(side == -1) {                         //Nếu đang chạy phải sang trái thì dùng 3 ảnh dưới
            if (status % 9 == 0) {
                this.img = Sprite.player_left.getFxImage();
            } else if (status % 9 == 3) {
                this.img = Sprite.player_left_1.getFxImage();
            } else if (status % 9 == 6){
                this.img = Sprite.player_left_2.getFxImage();
            }
        }
        
        x += a;
        status += 1;                          
        System.out.println(status);

    }
}
