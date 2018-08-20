package com.krustenkaese.bumhunter.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.krustenkaese.bumhunter.BumHunter;

/**
 * Created by Krustenkäse on 19.08.2018
 */
public class Hud implements Disposable{
    public Stage stage;
    private Viewport viewport;



    private Integer score;

    private FreeTypeFontGenerator generator;


    Label scoreLabel;
    Label HellLabel;
    Label levelPointLabel;
    Label levelLabel;
    Label hunterLabel;
    Label hellPointLabel;

    public Hud(SpriteBatch sb){
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Down With The Sickness V2.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size= 20;
        BitmapFont hudFont = generator.generateFont(parameter);




        score = 0;

        viewport = new FitViewport(BumHunter.V_WIDTH, BumHunter.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        hellPointLabel = new Label("rampage", new Label.LabelStyle(hudFont, Color.WHITE));
        scoreLabel = new Label(String.format("score %06d", score), new Label.LabelStyle(hudFont, Color.WHITE));
        HellLabel = new Label("HellLevel", new Label.LabelStyle(hudFont, Color.WHITE));
        levelPointLabel = new Label("1", new Label.LabelStyle(hudFont, Color.WHITE));
        levelLabel = new Label("Level", new Label.LabelStyle(hudFont, Color.WHITE));
        hunterLabel = new Label("Bum Hunter", new Label.LabelStyle(hudFont, Color.WHITE));

        table.add(hunterLabel).expandX().padTop(10);
        table.add(levelLabel).expandX().padTop(10);
        table.add(HellLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelPointLabel).expandX();
        table.add(hellPointLabel).expandX();

        stage.addActor(table);




    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
