package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.entities.mob.Mob;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;

    private GraphicsContext gc;
    private Canvas canvas;
    //private List<Entity> entities = new ArrayList<>();
    //private List<Entity> stillObjects = new ArrayList<>();

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        loadMap.load();

        Mob bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        //Entity balloon = new Balloon(1, 2, Sprite.balloom_left1.getFxImage());

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        loadMap.getMob().add(bomberman);

        for (int i = 0; i < loadMap.getMob().size(); i++) {
            if (loadMap.getMob().get(i) != null) {
                loadMap.getMob().get(i).move(scene);
            }
        }
    }


    public void update() {
        for (int i = 0; i < loadMap.getMob().size(); i++) {
            if (loadMap.getMob().get(i) != null) {
                loadMap.getMob().get(i).update();
            }
        }
        loadMap.getStillObjects().forEach(Entity::update);
    }


    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        loadMap.getStillObjects().forEach(g -> g.render(gc));
        for (int i = 0; i < loadMap.getMob().size(); i++) {
            if (loadMap.getMob().get(i) != null) {
                loadMap.getMob().get(i).render(gc);
            }
        }
    }
}