package ru.innovationcampus.vsu25.nikitina_v_v.space_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameResources;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.MyGdxGame;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.views.ButtonView;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.views.ImageView;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.views.MovingBackgroundView;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.views.TextView;

public class SettingsScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    MovingBackgroundView backgroundView;
    TextView tittleView;
    TextView musicView;
    TextView soundsView;
    TextView clearView;
    ButtonView returnButton;
    ImageView blackoutImageView;


    public SettingsScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        tittleView = new TextView(myGdxGame.pauseWhiteFont, 256, 956, "Settings");
        musicView = new TextView(myGdxGame.commonWhiteFont, 173, 717, "music: " + "ON");
        soundsView = new TextView(myGdxGame.commonWhiteFont, 173, 658, "sounds: " + "ON");
        clearView = new TextView(myGdxGame.commonWhiteFont, 173, 599, "clear records");
        returnButton = new ButtonView(280, 447, 160, 70, myGdxGame.pauseButtonFont, GameResources.BUTTON_IMG_PATH, "return");
        blackoutImageView = new ImageView(85, 365, GameResources.BLACKOUT_MIDDLE_IMG_PATH);
    }

    @Override
    public void render(float delta) {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();
        handleInput();

        backgroundView.draw(myGdxGame.batch);
        blackoutImageView.draw(myGdxGame.batch);
        tittleView.draw(myGdxGame.batch);
        returnButton.draw(myGdxGame.batch);
        musicView.draw(myGdxGame.batch);
        soundsView.draw(myGdxGame.batch);
        clearView.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }
    private String translateStateToText(boolean state) {
        return state ? "ON" : "OFF";
    }
    private void handleInput() {
        if (Gdx.input.justTouched()) {
        myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        if (returnButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
            myGdxGame.setScreen(myGdxGame.menuScreen);
        }
        if (clearView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
            clearView.setText("clear records (cleared)");
        }
        if (musicView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
            myGdxGame.audioManager.isMusicOn = !myGdxGame.audioManager.isMusicOn;
            musicView.setText("music: " + translateStateToText(myGdxGame.audioManager.isMusicOn));
            myGdxGame.audioManager.updateMusicFlag();
        }
        if (soundsView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
            myGdxGame.audioManager.isSoundOn = !myGdxGame.audioManager.isSoundOn;
            soundsView.setText("sound: " + translateStateToText(myGdxGame.audioManager.isSoundOn));
        }
    }
    }
}
