package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Enemy extends Entity{

    Rectangle collison;
    Texture text;

    public Enemy(float posX, float posY, float health, TextureAtlas texture, String locTexture) {
        super(posX,posY,health,texture,locTexture, "Enemy");
        collison = new Rectangle(posX, posY, 0.1f, 0.1f);
    }

    public void update(float delta, float playerPosX, float playerPosY) {
        super.update(delta);
        move(playerPosX, playerPosY);
        isAlive();
        collison.setPosition(body.getPosition().x, body.getPosition().y);
    }

    public void move(float posX, float posY) {
        Vector2 direction = new Vector2(posX - body.getPosition().x, posY - body.getPosition().y);

        if (Math.abs(direction.x) + Math.abs(direction.y) < 0.2) {
            body.setLinearVelocity(0, 0);

        }

        else if ((Math.abs(body.getLinearVelocity().x) <= 1/2 || Math.abs(body.getLinearVelocity().y) <= 1/2)) {
            direction = direction.nor();
            body.applyLinearImpulse(new Vector2(direction.x, direction.y), body.getWorldCenter(), true);
        }

        else {
            direction = direction.nor();
            body.setLinearVelocity(direction.x, direction.y);
        }
    }

    public void takeDamage(float damage) {
        health -= damage;
    }

    public void attack() {

    }

    public boolean isAlive() {
        if (health <= 0f) {
            return false;
        }
        else {
            return true;
        }
    }

    public Rectangle getCollison() {
        return collison;
    }
}
