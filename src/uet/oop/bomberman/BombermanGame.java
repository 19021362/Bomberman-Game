package uet.oop.bomberman;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.entities.mob.Mob;
import uet.oop.bomberman.graphics.Sound;
import uet.oop.bomberman.graphics.Sprite;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.*;
import java.sql.Time;
import java.time.Clock;
import java.util.*;
import java.util.stream.IntStream;

import static javafx.scene.layout.BackgroundSize.*;
import static javafx.scene.paint.Color.YELLOW;

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

    public static Label labelBlood = new Label("BLOOD: ");
    public static Label labelLevel = new Label("LEVEL: ");
    public static Label labelTime = new Label("TIME: ");

    private boolean nextLevel = false;
    public static int level = 1;
    public static final int levelMax = 3;
    //private List<Entity> stillObjects = new ArrayList<>();
    private int time = 0;

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

        setButton();
        setLabel();

        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameLoop(scene);
                start.setVisible(false);
                start.setDisable(true);
                exit.setVisible(false);
                exit.setDisable(true);
                labelBlood.setVisible(true);
                labelLevel.setVisible(true);
                labelTime.setVisible(true);
            }
        });


        again.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                level = 1;
                time = 0;
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

        root.getChildren().addAll(start, exit, again, labelBlood, labelLevel, labelTime);
        stage.setScene(scene);
        stage.show();



        PlayMusic(true);
    }

    private void gameLoop(Scene scene) {
        loadMap.load(level);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                time++;
                render();
                update();
                labelLevel.setText("LEVEL: " + level);
                labelTime.setText("TIME: " + (time / 60));
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
            clip.open(AudioSystem.getAudioInputStream(new File("C:\\Users\\ASUS\\Downloads\\chrismas.WAV")));
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


    private void setButton() {
        exit.setFont(Font.font(24));
        exit.setMinSize(width_b * 2, height_b * 2);
        exit.setLayoutX(((WIDTH * Sprite.SCALED_SIZE) / 2) - width_b);
        exit.setLayoutY(400);

        start.setFont(Font.font(24));
        start.setMinSize(width_b * 2, height_b * 2);
        start.setLayoutX(((WIDTH * Sprite.SCALED_SIZE) / 2) - width_b);
        start.setLayoutY(320);

        again.setFont(Font.font(24));
        again.setMinSize(width_b * 2, height_b * 2);
        again.setLayoutX(((WIDTH * Sprite.SCALED_SIZE) / 2) - width_b);
        again.setLayoutY(320);
        again.setDisable(true);
        again.setVisible(false);

    }

    private void setLabel() {
        labelBlood.setFont(Font.font("Cambria", 14));
        labelBlood.setLayoutX(5);
        labelBlood.setLayoutY(Sprite.SCALED_SIZE * HEIGHT);
        labelBlood.setVisible(false);

        labelLevel.setFont(Font.font("Cambria", 14));
        labelLevel.setLayoutX(((WIDTH * Sprite.SCALED_SIZE) / 2) - 30);
        labelLevel.setLayoutY(Sprite.SCALED_SIZE * HEIGHT);
        labelLevel.setVisible(false);

        labelTime.setFont(Font.font("Cambria", 14));
        labelTime.setLayoutX((WIDTH * Sprite.SCALED_SIZE) - 150);
        labelTime.setLayoutY(Sprite.SCALED_SIZE * HEIGHT);
        labelTime.setVisible(false);

    }

    public boolean isNextLevel() {
        return nextLevel;
    }

}