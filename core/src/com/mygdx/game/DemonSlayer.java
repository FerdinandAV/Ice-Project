package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MapLevels.Platform;

public class DemonSlayer extends Game {
    public static final int V_WIDTH = 800;
    public static final int V_HEIGHT = 400;
    public static final float PPM = 100;
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new Platform(this));
    }
    @Override
    public void render() {
        super.render();
    }
}