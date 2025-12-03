package ru.innovationcampus.vsu25.nikitina_v_v.space_game.views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class View implements Disposable {
    float x;
    float y;
    float width;
    float height;

    public View  (float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean isHit (float tx, float ty) {
        return true;
    }

    public void draw(SpriteBatch batch) {}
    @Override
    public void dispose() {

    }
}
