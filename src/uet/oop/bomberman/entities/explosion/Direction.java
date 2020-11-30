package uet.oop.bomberman.entities.explosion;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadMap;

import java.util.ArrayList;
import java.util.List;

public class Direction extends Entity {

    protected int _length;
    protected int dir;
    private List<Explosion> list_exp = new ArrayList<>();

    public Direction(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public Direction(int xUnit, int yUnit, Image img, int length, int dir) {
        super(xUnit, yUnit, img);
        this.dir = dir;
        this._length = CalculateLength(length);
    }

    @Override
    public void update() {
        if (status == 150) {
            remove(this);
        } else {
            status++;
        }
    }

    private int CalculateLength (int length) {
        Entity exp;
        boolean last = false;

        for (int i = 1; i <= length; i++) {
            if (i == length) {
                last = true;
            }
            int _x = (x / 32), _y = (y / 32);
            switch (dir) {
                case 6:
                    _x += i;
                    break;
                case 4:
                    _x -= i;
                    break;
                case 2:
                    _y += i;
                    break;
                case 8:
                    _y -= i;
                    break;
            }
            exp = new Explosion(_x, _y, null, dir, last);
            int num = Destroyable(exp);
            if (num == 2) {
                loadMap.getStillObjects().add(exp);
                list_exp.add((Explosion) exp);
            } else if (num == 1) {
                exp = new Explosion(_x, _y, null, dir, true);
                loadMap.getStillObjects().add(exp);
                list_exp.add((Explosion) exp);
                break;
            } else {
                break;
            }
        }
        return list_exp.size();
    }

    private int Destroyable(Entity e) {
        int num = 2;
        List<Entity> obj = loadMap.getStillObjects();
        for (Entity o : obj) {
            if (o instanceof Wall && e.collision(o)) {
                num = 0;
                break;
            }
            if (o instanceof Brick && e.collision(o)) {
                num = 1;
                break;
            }
        }
        return num;
    }


    public List<Explosion> getList_exp() {
        return list_exp;
    }



}
