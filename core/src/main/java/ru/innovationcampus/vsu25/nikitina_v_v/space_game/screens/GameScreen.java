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

import ru.innovationcampus.vsu25.nikitina_v_v.space_game.ContactManager;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameResources;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSession;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.MyGdxGame;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.objects.BulletObject;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.objects.ShipObject;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.objects.TrashObject;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    ShipObject shipObject;
    GameSession gameSession;
    ArrayList<TrashObject> trashArray;
    ArrayList<BulletObject> bulletArray;
    ContactManager contactManager;
    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        trashArray = new ArrayList<>();
        bulletArray = new ArrayList<>();
        gameSession = new GameSession();
        contactManager = new ContactManager(myGdxGame.world);
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
    private void updateTrash() {
        for (int i = 0; i < trashArray.size(); i++) {
            if (!trashArray.get(i).isInFrame() || !trashArray.get(i).isAlive()) {
                myGdxGame.world.destroyBody(trashArray.get(i).body);
                trashArray.remove(i--);
            }
        }
    }
    private void updateBullet() {
        System.out.println("size:" + bulletArray.size());
        for (int i = 0; i < bulletArray.size(); i++) {
            if (bulletArray.get(i).hasToBeDestroyed()) {
                myGdxGame.world.destroyBody(bulletArray.get(i).body);
                bulletArray.remove(i--);
            }
        }
    }

    @Override
    public void render(float delta) {
        myGdxGame.stepWorld();
        handleInput();

        if (gameSession.shouldSpawnTrash()) {
            TrashObject trashObject = new TrashObject(GameResources.TRASH_IMG_PATH,
                GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT, myGdxGame.world);
            trashArray.add(trashObject);
        }
        if (shipObject.needToShoot()) {
            BulletObject laserBullet = new BulletObject(GameResources.BULLET_IMG_PATH,shipObject.getX(), shipObject.getY() + shipObject.height/2,
                GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEiGHT, myGdxGame.world);
            bulletArray.add(laserBullet);
        }
        if (!shipObject.isAlive()) {
            System.out.println("Game over!");
        }
        updateBullet();
        updateTrash();
        draw();

    }
    public void draw() {
        ScreenUtils.clear(0.5f, 0.8f, 1, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();

        shipObject.draw(myGdxGame.batch);
        for (TrashObject trash : trashArray) trash.draw(myGdxGame.batch);
        for (BulletObject bullet : bulletArray) bullet.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    @Override
    public void dispose() {
        shipObject.dispose();
    }
}
