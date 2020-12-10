package uet.oop.bomberman;

import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.mob.Balloom;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.entities.mob.Oneal;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.item.Bomb_item;
import uet.oop.bomberman.entities.tile.item.Live_item;
import uet.oop.bomberman.entities.tile.item.Speed_item;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.Format;
import java.util.*;

public class loadMap {
    /*private static String input_level1 = "F:\\GitHub\\Bomberman-Game\\res\\levels\\1.txt"; //F:\
    private static String input_Level2 = "F:\\GitHub\\Bomberman-Game\\res\\levels\\2.txt";
    private static String input_Level3 = "F:\\GitHub\\Bomberman-Game\\res\\levels\\3.txt";*/

    private static String input_level1 = "C:\\Users\\ASUS\\Documents\\GitHub\\Bomberman-Game\\res\\levels\\1.txt";
    private static String input_Level2 = "C:\\Users\\ASUS\\Documents\\GitHub\\Bomberman-Game\\res\\levels\\2.txt";
    private static String input_Level3 = "C:\\Users\\ASUS\\Documents\\GitHub\\Bomberman-Game\\res\\levels\\3.txt";
    private static String input = "";
    private static boolean nextLevel = false;
    private static List<Entity> stillObjects = new ArrayList<>();
    private static List<Entity> mob = new ArrayList<>();
    private static Bomber bomber1;

    private static int numEnemy = 0;

    /**
     * ĐỌc bản đồ từ file.
     */
    public static void load(int level) {
        Random rd = new Random();
        try {
            FileReader input = new FileReader(getInput(level));
            Scanner scanner = new Scanner(input);
            int j = 0;
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                for (int  i = 0; i < s.length(); i++) {
                    int k = Math.abs(rd.nextInt() % 20);
                    Entity object;
                    if (s.charAt(i) == '#') {
                        object = new Wall(i, j, Sprite.wall.getFxImage());
                    } else if (s.charAt(i) == '*') {
                        stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                        if (k == 0) {
                            stillObjects.add(new Bomb_item(i, j, Sprite.powerup_bombs.getFxImage()));
                        }
                        if (k == 1) {
                            stillObjects.add(new Speed_item(i, j, Sprite.powerup_speed.getFxImage()));
                        }
                        if (k == 2) {
                            stillObjects.add(new Live_item(i, j, Sprite.powerup_bombpass.getFxImage()));
                        }
                        object = new Brick(i, j, Sprite.brick.getFxImage());
                    } else if (s.charAt(i) == 'x') {
                        object = new Portal(i, j, Sprite.grass.getFxImage());
                    } else if (s.charAt(i) == 'p') {
                        object = new Grass(i, j, Sprite.grass.getFxImage());
                        bomber1 = new Bomber(i, j, Sprite.player_right_2.getFxImage());
                        mob.add(bomber1);
                    } else if (s.charAt(i) == 'b') {
                        object = new Grass(i, j, Sprite.grass.getFxImage());
                        mob.add(new Balloom(i, j, Sprite.balloom_left1.getFxImage()));
                        numEnemy++;
                    } else if (s.charAt(i) == 'o') {
                        object = new Grass(i, j, Sprite.grass.getFxImage());
                        mob.add(new Oneal(i, j, Sprite.oneal_left1.getFxImage()));
                        numEnemy++;
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

    public static Bomber getBomber1() {
        return bomber1;
    }

    public static void remove(Entity o) {
        stillObjects.remove(o);
    }

    private static String getInput(int level) {
        if (level == 1) {
            return input_level1;
        } else if (level == 2) {
            return input_Level2;
        } else {
            return input_Level3;
        }
    }

    public static void clear() {
        stillObjects.clear();
        mob.removeIf(o -> !(o instanceof Bomber));

        mob.stream().filter(o -> o instanceof Bomber).forEach(o -> o.setPosition(1, 1));
    }

    public static void setNumEnemy(int numEnemy) {
        loadMap.numEnemy += numEnemy;
    }

    public static int getNumEnemy() {
        return numEnemy;
    }

    public static boolean gameOn() {
        return mob.stream().anyMatch(o -> o instanceof Bomber);
    }

    public static void resetMap() {
        mob.stream().filter(Objects::nonNull).forEach(o -> o.setPosition(o.getFirstX(), o.getFirstY()));
    }

    public static boolean isNextLevel() {
        return nextLevel;
    }

    public static void setNextLevel(boolean nextLevel) {
        loadMap.nextLevel = nextLevel;
    }
}