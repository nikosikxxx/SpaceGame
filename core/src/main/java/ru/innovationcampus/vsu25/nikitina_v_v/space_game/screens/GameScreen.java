package ru.innovationcampus.vsu25.nikitina_v_v.space_game.screens;

import static ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameResources.SHIP_IMG_PATH;
import static ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings.SCREEN_WIDTH;
import static ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings.SHIP_HEIGHT;
import static ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings.SHIP_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

import ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameResources;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSession;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.MyGdxGame;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.objects.ShipObject;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.objects.TrashObject;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    ShipObject shipObject;
    GameSession gameSession;
    ArrayList<TrashObject> trashArray;
    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        trashArray = new ArrayList<>();
        gameSession = new GameSession();
        shipObject = new ShipObject(SHIP_IMG_PATH,SCREEN_WIDTH / 2,150, SHIP_WIDTH, SHIP_HEIGHT, myGdxGame.world);
    }
    public void show() {
        gameSession.startGame();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            shipObject.move(myGdxGame.touch);
        }
    }

    @Override
    public void render(float delta) {
        if (gameSession.shouldSpawnTrash()) {
            TrashObject trashObject = new TrashObject(GameResources.TRASH_IMG_PATH,
                GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT, myGdxGame.world);
            trashArray.add(trashObject);

        }
        myGdxGame.stepWorld();
        handleInput();
        draw();

    }
    public void draw() {
        ScreenUtils.clear(0.5f, 0.8f, 1, 1);
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
