package uet.oop.bomberman.entities;


import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadMap;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    protected int status = 0;

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

    public void move(Scene scene, loadMap map) {}

    protected void remove(Entity o) {
        this.img = Sprite.grass.getFxImage();
    }

    public Rectangle getBound() {
        return new Rectangle(this.x + 2, this.y + 2, Sprite.SCALED_SIZE - 2, Sprite.SCALED_SIZE -2);
    }

    public boolean collision(Entity o) {
        return this.getBound().intersects(o.getBound().getBoundsInParent());
    }
}
