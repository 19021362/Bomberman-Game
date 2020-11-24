package uet.oop.bomberman.entities;


import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadMap;

import java.util.List;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    public int status = 0;

    protected boolean canPass = true;

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
        loadMap.getStillObjects().set(loadMap.getStillObjects().indexOf(o),
                new Grass(o.x / Sprite.SCALED_SIZE,
                        o.y / Sprite.SCALED_SIZE,
                        Sprite.grass.getFxImage()));
    }

    public Rectangle getBound() {
        return new Rectangle(this.x + 1, this.y + 1, 24, 31);
    }

    public boolean collision(Entity o) {
        return this.getBound().intersects(o.getBound().getBoundsInParent());
    }

    public boolean collision() {
        boolean check = false;
        List<Entity> obj = loadMap.getStillObjects();
        for (Entity o : obj) {
            if (!o.canPass && this.collision(o)) {
                check = true;
                break;
            }
        }
        return check;
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

    public void kill() {}

    public void setCanPass(boolean canPass) {
        this.canPass = canPass;
    }

}
