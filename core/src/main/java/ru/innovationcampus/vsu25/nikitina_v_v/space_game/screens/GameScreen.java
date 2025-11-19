package ru.innovationcampus.vsu25.nikitina_v_v.space_game.screens;

import static ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameResources.SHIP_IMG_PATH;
import static ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings.SCREEN_WIDTH;
import static ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings.SHIP_HEIGHT;
import static ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings.SHIP_WIDTH;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.innovationcampus.vsu25.nikitina_v_v.space_game.MyGdxGame;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.objects.ShipObject;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    ShipObject shipObject;
    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        shipObject = new ShipObject(SHIP_IMG_PATH,SCREEN_WIDTH / 2,150, SHIP_WIDTH, SHIP_HEIGHT, myGdxGame.world);
    }

    @Override
    public void render(float delta) {
        //myGdxGame.stepWorld();
        ScreenUtils.clear(0.5f, 0.3f, 1, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();

        shipObject.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    @Override
    public void dispose() {
        shipObject.dispose();
    }
}
