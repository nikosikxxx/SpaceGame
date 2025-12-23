package ru.innovationcampus.vsu25.nikitina_v_v.space_game.objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

import ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings;

public class SuperTrashObject extends GameObject {
    int livesLeft;
    private static final int paddingHorizontal = 60;
    public SuperTrashObject(String texturePath, int width, int height, World world) {
        super(texturePath, width / 2 + paddingHorizontal + (new Random()).nextInt((GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - width)),
            GameSettings.SCREEN_HEIGHT + height / 2, width, height, GameSettings.TRASH_BIT, world);
        body.setLinearVelocity(new Vector2(0, -GameSettings.SUPER_TRASH_VELOCITY));
        livesLeft = 2;
    }
    public boolean isInFrame() {
        return getY() + height/2 >0;
    }
//    public boolean isOutOfBounds() {
//        return getY() < 0;
//    }

    @Override
    public void hit() {
        livesLeft -= 1;
    }
    public boolean isAlive() {
        return livesLeft > 0;
    }

}
