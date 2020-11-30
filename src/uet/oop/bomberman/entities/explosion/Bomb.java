package uet.oop.bomberman.entities.explosion;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Bomb extends Entity {

    private int delay = 30;
    private double diffX = 0;
    private double diffY = 0;
    private final String path = "C:\\Users\\ASUS\\Documents\\GitHub\\Bomberman-Game\\res\\sounds\\explosion.WAV";

    private GraphicsContext graphicsContext;

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        setCanPass(false);
    }

    public Bomb(int xUnit, int yUnit, Image img, double diffX, double diffY) {
        super(xUnit, yUnit, img);
        this.diffX = diffX;
        this.diffY = diffY;
        setCanPass(false);
    }

    @Override
    public void update() {
        if (status == 150) {
            Bomber.recentBomb--;
            remove(this);
        } else {
            status++;
            //System.out.println(status);
            if (status % delay == 0) {
                this.img = Sprite.bomb.getFxImage();
            }
            if (status % delay == 10) {
                this.img = Sprite.bomb_1.getFxImage();
            }
            if (status % delay == 20) {
                this.img = Sprite.bomb_2.getFxImage();
            }
            if (status == 120) {
                PlayMusic(path);
            }
            if (status > 120) {
                this.img = Sprite.bomb_exploded.getFxImage();
            }
            if (status > 130) {
                this.img = Sprite.bomb_exploded1.getFxImage();
            }
            if (status > 140) {
                this.img = Sprite.bomb_exploded2.getFxImage();
            }
        }

    }




}
