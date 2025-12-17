package ru.innovationcampus.vsu25.nikitina_v_v.space_game.views;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import java.util.ArrayList;

import ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSession;
import ru.innovationcampus.vsu25.nikitina_v_v.space_game.GameSettings;

public class RecordsListView extends TextView{
    public RecordsListView(BitmapFont font, float x, float y) {
        super(font, 0, y, "");
    }

    public void setRecords(ArrayList<Integer> recordsList) {
        text = "";
        int countOfRows = Math.min(recordsList.size(), 5);
        for (int i = 0; i < countOfRows; i++) {
            System.out.println(recordsList.get(i));
            text += (i+1) + ". - " + recordsList.get(i) + "\n";
        }

        GlyphLayout glyphLayout = new GlyphLayout(font, text);
        x = (GameSettings.SCREEN_WIDTH - glyphLayout.width) / 2;
    }
}
