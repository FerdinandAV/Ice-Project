package com.mygdx.game.Sprites;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.DemonSlayer;
import com.mygdx.game.MapLevels.Platform;


import java.util.ArrayList;

import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class BulletExample extends ApplicationAdapter {

    SpriteBatch batch;
    Texture img;
    ArrayList<block> objectList = new ArrayList<block>();
    ArrayList<BulletPhysics> bulletList = new ArrayList<BulletPhysics>();
    ArrayList<BulletPhysics> deadList = new ArrayList<BulletPhysics>();


    public void create() {

        batch = new SpriteBatch();
        objectList.add(new block(0, 0));
        objectList.add(new block(0, 50));
        objectList.add(new block(600, 0));
        objectList.add(new block(600, 50));
        objectList.add(new block(600, 100));
        objectList.add(new block(600, 150));

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for (block b : objectList) {
            b.draw(batch);
        }

        /*for (BulletPhysics bullet : bulletList) {
            bullet.render(batch);
        }*/

        batch.end();

        for (BulletPhysics bullet : bulletList) {
            bullet.update(Gdx.graphics.getDeltaTime());
            if (bullet.isDead()) this.deadList.add(bullet);
        for (block b : objectList) {
             if(b.doesHit(bullet.getHitBox())) {
              b.hitEffect();
              deadList.add(bullet);
             }
        }

        }


        while (deadList.size() != 0) {
            bulletList.remove(deadList.get(0));
            deadList.remove(0);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            bulletList.add(new BulletPhysics(Gdx.input.getX(), 500 - Gdx.input.getY(), 90 * (float) Math.PI / 180));
        }




    }

}




