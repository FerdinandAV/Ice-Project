package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.mygdx.game.Sprites.BulletPhysics;

import java.util.ArrayList;

public class Gun extends Weapon{

    ArrayList<BulletPhysics> bullets = new ArrayList<>();
    ArrayList<BulletPhysics> deadList = new ArrayList<BulletPhysics>();
    SpriteBatch batch;


    public void attack(float posX, float posY) {
        bullets.add(new BulletPhysics(Gdx.input.getX() / 100f, Gdx.input.getY() / 100f, 90 * (float) Math.PI / 180));

        for (BulletPhysics bullet : bullets) {
            bullet.update(Gdx.graphics.getDeltaTime());
            if (bullet.isDead()) {
                this.deadList.add(bullet);
            }
        }

        while (deadList.size() != 0) {
            bullets.remove(deadList.get(0));
            deadList.remove(0);
        }
    }

    public void hitEffect() {
        System.out.println("You hit me!");
    }

    public void update() {
    }

    public ArrayList<BulletPhysics> render() {
        return bullets;
    }

}
