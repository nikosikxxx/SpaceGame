package ru.innovationcampus.vsu25.nikitina_v_v.space_game.objects;

import com.badlogic.gdx.physics.box2d.World;

public class TrashObject extends GameObject{
    public TrashObject(String texturePath, int x, int y, int width, int height, World world) {
        super(texturePath, x, y, width, height, world);
    }
}
