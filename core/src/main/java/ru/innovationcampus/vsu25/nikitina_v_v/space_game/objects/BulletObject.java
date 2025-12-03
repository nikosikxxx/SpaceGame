package ru.innovationcampus.vsu25.nikitina_v_v.space_game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings;

public class BulletObject extends GameObject{
    public BulletObject(String texturePath, int x, int y, int width, int height, World world) {
        super(texturePath, x, y, width, height, GameSettings.BULLET_BIT, world);
        body.setLinearVelocity(new Vector2(0, GameSettings.BULLET_VELOCITY));
        body.setBullet(true);
    }
    public boolean hasToBeDestroyed() {
        return getY() < height/2 - GameSettings.SCREEN_HEIGHT;
    }
}
