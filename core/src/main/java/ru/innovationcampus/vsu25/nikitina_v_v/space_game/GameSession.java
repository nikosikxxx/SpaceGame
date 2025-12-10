package ru.innovationcampus.vsu25.nikitina_v_v.space_game;

import com.badlogic.gdx.utils.TimeUtils;

public class GameSession {
    long nextTrashSpawnTime;
    long sessionStartTime;
    long pauseStartTime;
    public GameState state;

    public GameSession() {}
    public void startGame() {
        sessionStartTime = TimeUtils.millis();
        state = GameState.PLAYING;
        nextTrashSpawnTime = sessionStartTime + (long)
            (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN * getTrashPeriodCoolDown());
    }
    public void pauseGame(){
        state = GameState.PAUSED;
        pauseStartTime = TimeUtils.millis();
    }
    public void resumeGame() {
        state = GameState.PLAYING;
        sessionStartTime += TimeUtils.millis() - pauseStartTime;
    }

    public boolean shouldSpawnTrash() {
        if (nextTrashSpawnTime <= TimeUtils.millis()) {
            nextTrashSpawnTime = TimeUtils.millis() + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN * getTrashPeriodCoolDown());
            return true;
        }
        return false;
    }

    private float getTrashPeriodCoolDown() {
        return (float) Math.exp(-0.001 * (TimeUtils.millis() - sessionStartTime) / 1000);
    }


}
