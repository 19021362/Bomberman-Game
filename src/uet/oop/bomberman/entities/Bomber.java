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

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if(x == 52) {
            a = 1;
            this.img = Sprite.player_right_1.getFxImage();
        }

        if(x == 412) {
            a = -1;
            this.img = Sprite.player_right.getFxImage();
        }

        x += a;
        System.out.println(x);

    }
}
