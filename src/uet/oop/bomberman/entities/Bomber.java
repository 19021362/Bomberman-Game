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
    int status = 0;
    int side = 1;

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if(x == 52) {
            a = 1;
            side = 1;
        }

        if(x == 412) {
            a = -1;
            side = -1;
        }

        if(side == 1) {
            if (status % 9 == 0) {
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

        if(side == -1) {
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
