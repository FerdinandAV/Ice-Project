package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.game.Tools.Camera;
import com.mygdx.game.Launcher;

import com.mygdx.game.Sprites.*;
import com.mygdx.game.Tools.TileObjectCreater;

import java.util.ArrayList;

public class PlayScreen implements Screen {
    private Launcher game;
    private TextureAtlas impAtlas;
    private TextureAtlas chortAtlas;
    private TextureAtlas playerAtlas;

    private OrthographicCamera gamecam;
    public Camera gameCamera;
    private Viewport gamePort;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;

    private Player player;
    ArrayList<Enemy> enemies;

    /**
     * @param dt Method to move the player
     */


    /**
     * @param game Loads the world/platform which is the background and the collision boxes
     */
    public PlayScreen(Launcher game) {
        enemies = new ArrayList<>();

        playerAtlas = new TextureAtlas("Sprites.atlas");
        impAtlas = new TextureAtlas("Demon_imp.atlas");
        chortAtlas = new TextureAtlas("Demon_Chort.atlas");
        this.game = game;

        gameCamera = new Camera();

        gamePort = new FillViewport(Launcher.V_WIDTH / Launcher.PixelsPerMeter, Launcher.V_HEIGHT / Launcher.PixelsPerMeter, gameCamera.camera);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Test2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Launcher.PixelsPerMeter);

        world = new World(new Vector2(0, 0), true);

        b2dr = new Box2DDebugRenderer();

        new TileObjectCreater(world, map);

        player = new Player(1000, 1000, 100, playerAtlas, "Knight_Animation");

        player.createCollision(world);

    }

    /**
     * @param dt Method that runs constanly and follows the players movement setView(gamecam) shows what the gamecam sees
     */

    public void update(float dt) {

        if (player.getHealth() <= 0f) {
            game.endGame();
        }


        world.step(1 / 60f, 6, 2);

        player.update(dt);


        if (enemies.size() < 80) {
            enemies.add(new Enemy(MathUtils.random(100, 2000), MathUtils.random(100, 2000), 20, playerAtlas, "Knight_Animation"));
        }

        for (int i = 0; i < player.getWeapon().getBullets().size(); i++) {
            for (Enemy enemy : enemies) {
                if (player.getWeapon().getBullets().get(i).doesHit(enemy.getCollison())) {
                    player.getWeapon().getDeadList().add(player.getWeapon().getBullets().get(i));
                    enemy.takeDamage(player.getWeapon().getDamage());
                }
            }
        }

        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isAlive()) {
                if (enemies.get(i).body == null) {
                    enemies.get(i).createCollision(world);
                }
                enemies.get(i).update(dt, player.body.getPosition().x, player.body.getPosition().y);
            } else {
                world.destroyBody(enemies.get(i).body);
                enemies.remove(i);
            }
        }

        int numContacts = world.getContactCount();
        if (numContacts > 0) {
            for (Contact contact : world.getContactList()) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
                if (fixtureA.getBody() == player.body) {
                    player.takeDamage(0.1f);
                }
            }
        }


        gameCamera.update(player.body.getPosition().x, player.body.getPosition().y);
        renderer.setView(gameCamera.camera);


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

        b2dr.render(world, gameCamera.camera.combined);

        game.batch.setProjectionMatrix(gameCamera.camera.combined);

        game.batch.begin();

        for (Enemy enemy : enemies) {
            enemy.draw(game.batch);
        }
        player.draw(game.batch);

        for (Bullet bullets : player.getWeapon().render()) {
            bullets.render(game.batch);
        }

        game.batch.end();

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
    }
}