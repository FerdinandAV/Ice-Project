package com.mygdx.game.Tools;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    private AssetManager assetManager = new AssetManager();

    public static final AssetDescriptor<TextureAtlas> TILESET_01 = new AssetDescriptor<TextureAtlas>("Sprites.atlas",TextureAtlas.class);
    public static final AssetDescriptor<Skin> SKIN = new AssetDescriptor<Skin>("pixthulhu-ui.json",Skin.class, new SkinLoader.SkinParameter("pixthulhu-ui.atlas"));

    public void loadAll(){
        assetManager.load(TILESET_01);
        assetManager.load(SKIN) ;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
