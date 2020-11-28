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

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
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

        loadMap.load(2);


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };

        //PlayMusic();

        timer.start();


        for (int i = 0; i < loadMap.getMob().size(); i++) {
            if (loadMap.getMob().get(i) != null) {
                loadMap.getMob().get(i).move(scene);
            }
        }

        PlayMusic();
    }


    public void update() {
        for (int i = 0; i < loadMap.getMob().size(); i++) {
            if (loadMap.getMob().get(i) != null) {
                loadMap.getMob().get(i).update();
            }
        }
        for (int i = 0; i < loadMap.getStillObjects().size(); i++) {
            if (loadMap.getStillObjects().get(i) != null) {
                loadMap.getStillObjects().get(i).update();
            }
        }
    }


    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < loadMap.getStillObjects().size(); i++) {
            if (loadMap.getStillObjects().get(i) != null) {
                loadMap.getStillObjects().get(i).render(gc);
            }
        }
        for (int i = 0; i < loadMap.getMob().size(); i++) {
            if (loadMap.getMob().get(i) != null) {
                loadMap.getMob().get(i).render(gc);
            }
        }
    }

    private static void PlayMusic() {
        String filename = "C:\\Users\\ASUS\\Documents\\GitHub\\Bomberman-Game\\res\\sounds\\background.WAV";
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength()/1000000);
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }
}