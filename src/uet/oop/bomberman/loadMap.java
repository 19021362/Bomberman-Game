package uet.oop.bomberman;

import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.mob.Balloom;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class loadMap {
    private String input_map = "";
    private static List<Entity> stillObjects = new ArrayList<>();
    private static List<Entity> mob = new ArrayList<>();


    /**
     * ĐỌc bản đồ từ file.
     */
    public static void load() {
        try {
            FileReader input =
                    new FileReader("C:\\Users\\ASUS\\Documents\\GitHub\\Bomberman-Game\\res\\levels\\1.txt");
            Scanner scanner = new Scanner(input);
            int j = 0;
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                for (int  i = 0; i < s.length(); i++) {
                    Entity object;
                    if (s.charAt(i) == '#') {
                        object = new Wall(i, j, Sprite.wall.getFxImage());
                    } else if (s.charAt(i) == '*') {
                        stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                        object = new Brick(i, j, Sprite.brick.getFxImage());
                    } else if (s.charAt(i) == 'x') {
                        object = new Portal(i, j, Sprite.portal.getFxImage());
                    } else if (s.charAt(i) == 'b') {
                        object = new Grass(i, j, Sprite.grass.getFxImage());
                        mob.add(new Balloom(i, j, Sprite.balloom_left1.getFxImage()));
                    } else {
                        object = new Grass(i, j, Sprite.grass.getFxImage());
                    }
                    stillObjects.add(object);
                }
                j++;

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static List<Entity> getStillObjects() {
        return stillObjects;
    }

    public static List<Entity> getMob() {
        return mob;
    }

    public static void add(Entity o) {
        stillObjects.add(o);
    }

    public static void remove(Entity o) {
        stillObjects.remove(o);
    }
}
