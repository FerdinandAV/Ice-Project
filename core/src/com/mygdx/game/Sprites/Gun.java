package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Gun extends Weapon {

    private ArrayList<Bullet> bullets = new ArrayList<>();
    private ArrayList<Bullet> deadBullets = new ArrayList<Bullet>();
    private SpriteBatch batch;
    private float timeFromLastShot;
    private float attackSpeed = 0.1f; // 1.0 equals 1 per second
    private float damage = 20f; // Bullets damage

    public void attack(float posX, float posY) {
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.input.getY();
        //bullets.add(new BulletPhysics((posX + -(((-mouseX + 960)*2.6f) / 1000)),(posY + -(((mouseY - 500)*2.6f) / 1000)),new Vector2(posX, posY)));

        bullets.add(new Bullet((posX + -(((-mouseX + (Gdx.graphics.getWidth()/2f))*2.6f) / 1000)),(posY + -(((mouseY - ((Gdx.graphics.getHeight()-80)/2f))*2.6f) / 1000)),new Vector2(posX, posY)));


        for (Bullet bullet : bullets) {
            bullet.update(Gdx.graphics.getDeltaTime());
            if (bullet.isDead()) {
                this.deadBullets.add(bullet);
            }
        }

        while (deadBullets.size() != 0) {
            bullets.remove(deadBullets.get(0));
            deadBullets.remove(0);
        }

    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public ArrayList<Bullet> getDeadList() {
        return deadBullets;
    }

    public float getDamage() {
        return damage;
    }

    public void update(float delta) {
        for (Bullet bullet : bullets) {
            bullet.update(delta);

        }
        timeFromLastShot += delta;
    }

    public ArrayList<Bullet> render() {
        return bullets;
    }

    public boolean canAttack() {

        if (timeFromLastShot < attackSpeed) {
            return false;
        }
        else {
            timeFromLastShot = 0;
            return true;
        }
    }

}
