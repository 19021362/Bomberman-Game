package uet.oop.bomberman.entities.tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.graphics.Sound;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadMap;


public class Portal extends Entity {
    private boolean isOpen = false;
    private int level = 1;

    public Portal(int x, int y, Image img) {
        super(x, y, img);
        setCanPass(true);
    }

    @Override
    public void update() {
        if (loadMap.getNumEnemy() == 0) {
            img = Sprite.portal.getFxImage();
            isOpen = true;
        }
        if (collision()) {
            loadMap.setNextLevel(true);
            PlayMusic(Sound.nextLevel);
            remove(this);
        }
    }

    @Override
    public boolean collision() {
        boolean check = false;
        if (isOpen) {
            for (Entity o : loadMap.getMob()) {
                if (o instanceof Bomber && this.collision(o)) {
                    check = true;
                }
            }
        }
        return check;
    }

}
