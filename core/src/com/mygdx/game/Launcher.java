package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.Scenes.PlayScreen;
import com.mygdx.game.Scenes.MainMenuScreen;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.Audio;


public class Launcher extends Game {
    public static final int V_WIDTH = 500;
    public static final int V_HEIGHT = 500;
    public static final float PixelsPerMeter = 100;
    public SpriteBatch batch;
    private TextureAtlas tileset;
    private Assets assets;

    public Audio audio;
    //enum audios {THEME, BOSS, MENU, PLAYERHIT};

    @Override
    public void create() {
        assets = new Assets();
        assets.loadAll();
        assets.getAssetManager().finishLoading();
        audio = new Audio();

        setScreen(new MainMenuScreen(assets.getAssetManager(), this));
        tileset = assets.getAssetManager().get(Assets.TILESET_01);
    }

    public void startGame() {
        batch = new SpriteBatch();
        setScreen(new PlayScreen(this));
        audio.StopSound();
        playAudio("Theme");
    }

    public void endGame() {
        audio.StopSound();
        setScreen(new MainMenuScreen(assets.getAssetManager(), this));
    }

    public void playAudio(String choice) {
        switch (choice) {
            case "Menu":
                audio.MenuSound();
                audio.StopSound();
                break;
            case "Theme":
                audio.ThemeSound();
                break;
            case "PlayerHit":
                audio.PlayerGettingHitSound();
                break;
            case "PlayerDeath":
                audio.PlayerDeathSound();
                break;
            case "Boss":
                audio.BossSound();
                break;
            case "BossChase":
                audio.BossChaseSound();
                break;
        }
    }
}