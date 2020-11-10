package uet.oop.bomberman;

import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class loadMap {
    private String input_map = "";
    private List<Entity> stillObjects = new ArrayList<>();

    public void load() {
        try {
            FileReader input =
                    new FileReader("C:\\Users\\ASUS\\Documents\\GitHub\\Bomberman-Game\\res\\levels\\1.txt");
            Scanner scanner = new Scanner(input);
            int j = 0;
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                //System.out.println(s);
                for (int  i = 0; i < s.length(); i++) {
                    Entity object;
                    if (s.charAt(i) == '#') {
                        object = new Wall(i, j, Sprite.wall.getFxImage());
                    } else if (s.charAt(i) == '*') {
                        object = new Brick(i, j, Sprite.brick.getFxImage());
                    } else if (s.charAt(i) == 'x') {
                        object = new Portal(i, j, Sprite.portal.getFxImage());
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

    public List<Entity> getStillObjects() {
        return stillObjects;
    }
}
