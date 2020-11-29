package uet.oop.bomberman.entities;


import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.mob.Mob;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.item.Item;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadMap;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.List;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    public int status = 0;

    public boolean canPass = true;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();

    public void move(Scene scene) {}

    protected void remove(Entity o) {
        if (o instanceof Mob) {
            loadMap.getMob().set(loadMap.getMob().indexOf(o),
                    null);
        } else {
            loadMap.getStillObjects().set(loadMap.getStillObjects().indexOf(o),
                    null);
        }
    }

    public Rectangle getBound() {
        return new Rectangle(this.x + 1, this.y + 1, 24, 28);
    }

    public boolean collision(Entity o) {
        return this.getBound().intersects(o.getBound().getBoundsInParent());
    }

    public boolean collision() {
        return false;
    }

    public void setCanPass(boolean canPass) {
        this.canPass = canPass;
    }

    public boolean destroyable() {
        boolean check = true;
        List<Entity> obj = loadMap.getStillObjects();
        for (Entity o : obj) {
            if (o instanceof Wall && this.collision(o)) {
                check = false;
                break;
            }
        }
        return check;
    }

    public void PlayMusic(String path) {
        String filename = path;
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            clip.start();
            Thread.sleep(clip.getMicrosecondLength()/100000);
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }

    public void setPosition(int x, int y) {
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
    }
}
