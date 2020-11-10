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
import uet.oop.bomberman.loadMap;

public class Bomber extends Entity {

    private final int speed = 5;

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {

    }

    @Override
    public void move(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode keyCode = event.getCode();
                switch (keyCode) {
                    case RIGHT:
                        x += speed;
                        check();
                        break;
                    case LEFT:
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
                    default:
                        check();
                }
            }
        });
    }

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
