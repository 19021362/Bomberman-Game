package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.graphics.Sound;
import uet.oop.bomberman.graphics.Sprite;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static javafx.scene.paint.Color.*;

public class BombermanGame extends Application {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;
    private final int width_b = 50;
    private final int height_b = 25;

    private GraphicsContext gc;
    private Canvas canvas;
    private Button exit = new Button("EXIT");
    private Button start = new Button("START");
    private Button again = new Button("AGAIN");


    private boolean nextLevel = false;
    public static int level = 1;
    public static final int levelMax = 3;
    //private List<Entity> entities = new ArrayList<>();
    //private List<Entity> stillObjects = new ArrayList<>();



    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT + 20);
        gc = canvas.getGraphicsContext2D();

        initBGD(gc, Sprite.bgd);

        // Tao root container
        Group root = new Group();

        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        initBGD(gc, Sprite.bgd);

        exit.setFont(Font.font(24));
        exit.setMinSize(width_b * 2, height_b * 2);
        exit.setLayoutX(((WIDTH * Sprite.SCALED_SIZE) / 2) - width_b);
        exit.setLayoutY(400);
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        start.setFont(Font.font(24));
        start.setMinSize(width_b * 2, height_b * 2);
        start.setLayoutX(((WIDTH * Sprite.SCALED_SIZE) / 2) - width_b);
        start.setLayoutY(320);
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameLoop(scene);
                start.setVisible(false);
                start.setDisable(true);
                exit.setVisible(false);
                exit.setDisable(true);
            }
        });

        again.setFont(Font.font(24));
        again.setMinSize(width_b * 2, height_b * 2);
        again.setLayoutX(((WIDTH * Sprite.SCALED_SIZE) / 2) - width_b);
        again.setLayoutY(320);
        again.setDisable(true);
        again.setVisible(false);
        again.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                level = 1;
                again.setVisible(false);
                again.setDisable(true);
                exit.setVisible(false);
                exit.setDisable(true);
                Bomber.recentBomb = 0;
                loadMap.clear();
                loadMap.setNumEnemy(-loadMap.getNumEnemy());
                gameLoop(scene);
            }
        });


        // Them scene vao stage

        root.getChildren().addAll(start, exit, again);
        stage.setScene(scene);
        stage.show();



        PlayMusic(true);
    }

    private void gameLoop(Scene scene) {
        loadMap.load(level);
        Entity bomber = null;
        for (int i = 0; i < loadMap.getMob().size(); i++) {
            if (loadMap.getMob().get(i) instanceof Bomber) {
                bomber = loadMap.getMob().get(i);
            }
        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
                if (loadMap.isNextLevel()) {
                    loadMap.clear();
                    loadMap.setNextLevel(false);
                    level++;
                    if (level <= levelMax) {
                        Bomber.recentBomb = 0;
                        loadMap.load(level);
                    } else {
                        try {
                            initBGD(gc, Sprite.gWin);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        this.stop();
                    }

                }
                if (!loadMap.gameOn()) {
                    this.stop();
                    try {
                        initBGD(gc, Sprite.gOver);
                        again.setDisable(false);
                        again.setVisible(true);
                        exit.setVisible(true);
                        exit.setDisable(false);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        move(scene);
        timer.start();

    }

    private void move(Scene scene) {
        for (Entity o : loadMap.getMob()) {
            if (o instanceof Bomber) {
                o.move(scene);
            }
        }
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

    private static void PlayMusic(boolean play) {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("F:\\Github\\Bomberman-Game\\res\\sounds\\2016-World-Championship-Login-Screen-League-of-Legends.wav")));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            if (play) {
                clip.start();
                Thread.sleep(clip.getMicrosecondLength() / 1000000);
            } else {
                clip.close();
            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }

    private void initBGD(GraphicsContext gc, String filePath) throws FileNotFoundException {
        Image image = new javafx.scene.image.Image(new FileInputStream(filePath),
                Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT + 20, false, false);

        gc.drawImage(image, 0, 0);
    }

    public boolean isNextLevel() {
        return nextLevel;
    }

}