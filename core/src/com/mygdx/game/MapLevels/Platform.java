package com.mygdx.game.MapLevels;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.game.DemonSlayer;
import com.mygdx.game.Scenes.Hud;

import com.mygdx.game.Sprites.Demons;
import com.mygdx.game.Sprites.Slayer;
import com.mygdx.game.Tools.B2WorldCreator;

public class Platform implements Screen {
    private DemonSlayer game;
    private TextureAtlas atlas;

    private OrthographicCamera gamecam;
    private Hud hud;
    private Viewport gamePort;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;

    private Slayer player;
    private Demons demon;

    /**
     * @param dt Method to move the player
     */


    /**
     * @param dt Method that runs constanly and follows the players movement setView(gamecam) shows what the gamecam sees
     */
    public void update(float dt) {


        world.step(1 / 60f, 6, 2);

        player.update(dt);
        demon.update(dt,player.playerBody.getPosition().x,player.playerBody.getPosition().y);

        gamecam.position.x = player.playerBody.getPosition().x;
        gamecam.position.y = player.playerBody.getPosition().y;

        gamecam.update();

            renderer.setView(gamecam);
        }


    /**
     * @param game Loads the world/platform which is the background and the collision boxes
     */
    public Platform(DemonSlayer game) {
        atlas = new TextureAtlas("Sprites.atlas");
        this.game = game;

        gamecam = new OrthographicCamera();

        gamePort = new FillViewport(DemonSlayer.V_WIDTH / DemonSlayer.PPM, DemonSlayer.V_HEIGHT / DemonSlayer.PPM, gamecam);

        hud = new Hud(game.batch);


        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Test.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / DemonSlayer.PPM);

        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, 0), true);

        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world, map);

        player = new Slayer(world, this);
        demon = new Demons(world,this);

    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        demon.draw(game.batch);
        player.draw(game.batch);


        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }
}