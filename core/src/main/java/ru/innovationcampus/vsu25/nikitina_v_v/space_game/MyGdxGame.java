package ru.innovationcampus.vsu25.nikitina_v_v.space_game;

import static ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings.POSITION_ITERATIONS;
import static ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings.SCREEN_HEIGHT;
import static ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings.SCREEN_WIDTH;
import static ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings.STEP_TIME;
import static ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings.VELOCITY_ITERATIONS;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.innovationcampus.vsu25.nikitina_v_v.space_game.managers.AudioManager;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.screens.GameScreen;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.screens.MenuScreen;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.screens.SettingsScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MyGdxGame extends Game {
    public SpriteBatch batch;
    public OrthographicCamera camera;
    public GameScreen gameScreen;
    public MenuScreen menuScreen;
    public World world;
    public Vector3 touch;
    public BitmapFont commonWhiteFont;
    public BitmapFont pauseWhiteFont;
    public BitmapFont pauseButtonFont;
    public AudioManager audioManager;
    public SettingsScreen settingsScreen;

    float accumulator = 0;

    public void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += delta;

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

    @Override
    public void create() {

            Box2D.init();
        world = new World(new Vector2(0, 0), true);
        commonWhiteFont = FontBuilder.generate(27, Color.WHITE, GameResources.FONTS);
        pauseWhiteFont = FontBuilder.generate(50, Color.WHITE, GameResources.FONTS);
        pauseButtonFont = FontBuilder.generate(30, Color.BLACK, GameResources.FONTS);
        audioManager = new AudioManager();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

        gameScreen = new GameScreen(this);
        menuScreen = new MenuScreen(this);
        settingsScreen = new SettingsScreen(this);

        setScreen(menuScreen);
    }


    @Override
    public void dispose() {
        batch.dispose();
    }
}
