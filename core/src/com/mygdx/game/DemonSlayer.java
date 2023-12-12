package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.MapLevels.Platform;
import com.mygdx.game.Sprites.BulletExample;
import com.mygdx.game.Sprites.BulletPhysics;


public class DemonSlayer extends Game {
    public static final int V_WIDTH = 800;
    public static final int V_HEIGHT = 400;
    public static final float PPM = 100;
    public SpriteBatch batch;
    private TextureAtlas tileset;
    public Audio audio;
    enum audios {Theme, Boss}

    @Override
    public void create() {
        Assets assets = new Assets();
        assets.loadAll();
        assets.getAssetManager().finishLoading();
        audio = new Audio();

        setScreen(new MainMenuScreen(assets.getAssetManager()));
        //startGame();
        playAudio("Theme");
        tileset = assets.getAssetManager().get(Assets.TILESET_01);
    }

    public void startGame() {
        batch = new SpriteBatch();
        setScreen(new Platform(this));
        playAudio("Theme");
    }

    public void playAudio(String choice) {
        switch(choice) {
            case "Menu":
                audio.MenuSound();
                break;
           case "Theme":
                audio.ThemeSound();
                break;
            case "PlayerHit":
                audio.PlayerGettingHitSound();
                break;
            case "PlaterDeath":
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