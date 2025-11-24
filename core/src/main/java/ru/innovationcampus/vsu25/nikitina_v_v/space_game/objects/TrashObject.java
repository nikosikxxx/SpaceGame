package ru.innovationcampus.vsu25.nikitina_v_v.space_game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings;

public class TrashObject extends GameObject{
    private static final int paddingHorizontal = 30;
    public TrashObject(String texturePath, int width, int height, World world) {
        super(texturePath, 100, 100, width, height, world);
        body.setLinearVelocity(new Vector2(0, -GameSettings.TRASH_VELOCITY));
    }
    public boolean isInFrame() {
     return getY() + height/2 >0;
    }
}
