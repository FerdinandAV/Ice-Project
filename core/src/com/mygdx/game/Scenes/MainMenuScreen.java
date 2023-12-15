package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Sprites.Gun;
import com.mygdx.game.Launcher;
import com.mygdx.game.Tools.Assets;

public class MainMenuScreen extends ScreenAdapter {

    private Stage stage;
    private Viewport viewport;
    private AssetManager assetManager;
    private Skin skin;

    private Table mainTable;
    private Launcher ds;
    private boolean gamePlaying;


    public MainMenuScreen(AssetManager assetManager, Launcher ds) {
        this.assetManager = assetManager;
        skin = assetManager.get(Assets.SKIN);
        this.ds = ds;
        gamePlaying = false;
        System.out.println(gamePlaying);
    }

    @Override
    public void show() {

        viewport = new ExtendViewport(1920, 1080);
        stage = new Stage(viewport);

        mainTable = new Table();
        mainTable.setFillParent(true);

        stage.addActor(mainTable);

        addButton("Play").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!gamePlaying) {
                    gamePlaying = true;
                    ds.startGame();
                }
            }
        });
        addButton("Settings");
        addButton("Credits").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("This game was made by Ferdinand, Mahdi, Benjamin and Claus");
            }
        });
        addButton("Exit").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!gamePlaying) {
                    Gdx.app.exit();
                }
            }
        });
        Gdx.input.setInputProcessor(stage);
        ds.playAudio("Theme");
    }

    private TextButton addButton(String name) {
        TextButton button = new TextButton(name, skin);
        mainTable.add(button).fillX().padBottom(5);
        mainTable.row();
        return button;
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}