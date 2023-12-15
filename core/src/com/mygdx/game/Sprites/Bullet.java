package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Sprite {

    private Rectangle hitbox;
    private float x, y, time, speed;
    private Vector2 playerPos;
    public Texture text;
    private int width, height;

    public Bullet(float x, float y, Vector2 playerPos) {
        time = 2;
        speed = 0.8f;
        hitbox = new Rectangle(playerPos.x, playerPos.y, 0.05f, 0.05f);
        text = new Texture("Bullet.png");
        this.x = x;
        this.y = y;
        this.playerPos = playerPos;
    }

    public Rectangle getHitBox() {
        return hitbox;
    }

    public boolean doesHit(Rectangle r){
        //return r.overlaps(hitbox);
        return hitbox.overlaps(r);
    }


    public void update(float delta) {
        Vector2 direction = new Vector2(x - playerPos.x, y - playerPos.y);
        direction = direction.nor();
        hitbox.x += (direction.x*delta) / speed;
        hitbox.y += (direction.y*delta) / speed;

        time -= delta;
    }

    public boolean isDead() {
        if (time < 0) {
            return true;
        } else {
            return false;
        }
    }


    public void render(SpriteBatch batch) {
        batch.draw(text, hitbox.x - (0.1f/2), hitbox.y - (0.1f/2), 0.1f,0.1f );
    }
}
