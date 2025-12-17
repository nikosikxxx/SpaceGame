package ru.innovationcampus.vsu25.nikitina_v_v.space_game.screens;

import static ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameResources.SHIP_IMG_PATH;
import static ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings.SCREEN_WIDTH;
import static ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings.SHIP_HEIGHT;
import static ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings.SHIP_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

import ru.innovationcampus.vsu25.nikitina_v_v.space_game.managers.ContactManager;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameResources;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSession;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameState;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.MyGdxGame;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.managers.MemoryManager;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.objects.BulletObject;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.objects.ShipObject;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.objects.TrashObject;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.views.ButtonView;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.views.ImageView;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.views.LiveView;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.views.MovingBackgroundView;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.views.RecordsListView;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.views.TextView;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    ImageView topBalckoutView;
    ImageView fullBlackoutView;
    ButtonView homeButton;
    ButtonView continueButton;
    ShipObject shipObject;
    GameSession gameSession;
    ArrayList<TrashObject> trashArray;
    ArrayList<BulletObject> bulletArray;
    ContactManager contactManager;
    MovingBackgroundView backgroundView;
    LiveView liveView;
    TextView scoretextView;
    TextView pauseTextView;
    TextView recordsTextView;
    ButtonView pauseButton;
    ButtonView homeButton2;
    RecordsListView recordsListView;
    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        trashArray = new ArrayList<>();
        bulletArray = new ArrayList<>();
        gameSession = new GameSession();
        recordsListView = new RecordsListView(myGdxGame.commonWhiteFont, 690, 742);
        scoretextView = new TextView(myGdxGame.commonWhiteFont, 50, 1215);
        pauseTextView = new TextView(myGdxGame.pauseWhiteFont, 282, 880, "Pause");
        recordsTextView = new TextView(myGdxGame.pauseWhiteFont, 206, 842, "Last records");
        contactManager = new ContactManager(myGdxGame.world);
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        shipObject = new ShipObject(SHIP_IMG_PATH,SCREEN_WIDTH / 2,150, SHIP_WIDTH, SHIP_HEIGHT, myGdxGame.world);
        topBalckoutView = new ImageView(0, 1180, GameResources.BLACKOUT_TOP_IMG_PATH);
        fullBlackoutView = new ImageView(0,0,GameResources.FULL_BLACKOUT_IMG_PATH);
        homeButton = new ButtonView(140, 650, 200, 75, myGdxGame.pauseButtonFont, GameResources.BUTTON_IMG_PATH, "Home");
        homeButton2 = new ButtonView(280, 365, 160, 70, myGdxGame.pauseButtonFont, GameResources.BUTTON_IMG_PATH, "Home");
        continueButton = new ButtonView(380, 650, 200, 75, myGdxGame.pauseButtonFont, GameResources.BUTTON_IMG_PATH, "Continue");
        liveView = new LiveView(305, 1215);
        pauseButton = new ButtonView(605, 1200, 46, 54, GameResources.PAUSE_IMG_PATH);
    }
    public void show() {
        restarGame();
    }

    private void restarGame() {
        for (int i = 0; i< trashArray.size(); i++) {
            myGdxGame.world.destroyBody(trashArray.get(i).body);
            trashArray.remove(i--);
        }
        if (shipObject != null) {
            myGdxGame.world.destroyBody(shipObject.body);
        }

        shipObject = new ShipObject(SHIP_IMG_PATH, SCREEN_WIDTH / 2, 150, SHIP_WIDTH, SHIP_HEIGHT, myGdxGame.world);
        bulletArray.clear();
        gameSession.startGame();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            switch (gameSession.state) {
                case PLAYING:
                    if (pauseButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.pauseGame();
                    }
                    shipObject.move(myGdxGame.touch);
                    break;
                case PAUSED:
                    if (continueButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.resumeGame();
                    }
                    if (homeButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }
                case ENDED:
                    if (homeButton2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }
                    break;
            }
        }


    }
    private void updateTrash() {
        for (int i = 0; i < trashArray.size(); i++) {

            boolean hasToBeDestroyed = !trashArray.get(i).isAlive() || !trashArray.get(i).isInFrame();

            if (!trashArray.get(i).isAlive()) {
                gameSession.destructionRegistration();
                if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.explosionSound.play(0.2f);
            }

            if (hasToBeDestroyed) {
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
        handleInput();
        if (gameSession.state == GameState.PLAYING){
            if (gameSession.shouldSpawnTrash()) {
                TrashObject trashObject = new TrashObject(GameResources.TRASH_IMG_PATH,
                    GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT, myGdxGame.world);
                trashArray.add(trashObject);
            }
            if (shipObject.needToShoot()) {
                BulletObject laserBullet = new BulletObject(GameResources.BULLET_IMG_PATH,shipObject.getX(), shipObject.getY() + shipObject.height/2,
                    GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEiGHT, myGdxGame.world);
                bulletArray.add(laserBullet);
                myGdxGame.audioManager.shootSound.play();
            }
            if (!shipObject.isAlive()) {
                gameSession.endGame();
                recordsListView.setRecords(MemoryManager.loadRecordsTable());
            }

            if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.shootSound.play();
            updateBullet();
            updateTrash();
            backgroundView.move();
            if (gameSession.state == GameState.PLAYING) {
                gameSession.updateScore();
                scoretextView.setText("Score: " + gameSession.getScore());
            }
            liveView.setLeftLives(shipObject.getLivesLeft());
            myGdxGame.stepWorld();
        }
        draw();
    }
    public void draw() {
        ScreenUtils.clear(0.5f, 0.8f, 1, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();

        backgroundView.draw(myGdxGame.batch);
        shipObject.draw(myGdxGame.batch);
        for (TrashObject trash : trashArray) trash.draw(myGdxGame.batch);
        for (BulletObject bullet : bulletArray) bullet.draw(myGdxGame.batch);
        topBalckoutView.draw(myGdxGame.batch);
        liveView.draw(myGdxGame.batch);
        topBalckoutView.draw(myGdxGame.batch);
        scoretextView.draw(myGdxGame.batch);
        liveView.draw(myGdxGame.batch);
        pauseButton.draw(myGdxGame.batch);

        if (gameSession.state == GameState.PAUSED) {
            fullBlackoutView.draw(myGdxGame.batch);
            pauseTextView.draw(myGdxGame.batch);
            homeButton.draw(myGdxGame.batch);
            continueButton.draw(myGdxGame.batch);
        } else if (gameSession.state == GameState.ENDED) {
            fullBlackoutView.draw(myGdxGame.batch);
            recordsTextView.draw(myGdxGame.batch);
            recordsListView.draw(myGdxGame.batch);
            homeButton2.draw(myGdxGame.batch);

        }

        myGdxGame.batch.end();
    }

    @Override
    public void dispose() {
        shipObject.dispose();
    }
}
