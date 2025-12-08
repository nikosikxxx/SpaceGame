package ru.innovationcampus.vsu25.nikitina_v_v.space_game.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ImageView extends View{
    Texture texture;

    public ImageView(float x, float y, String ImagePath) {
        super(x,y);
        texture = new Texture(ImagePath);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }
    @Override
    public void dispose() {
        texture.dispose();
    }
}
