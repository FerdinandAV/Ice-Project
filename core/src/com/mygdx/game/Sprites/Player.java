package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {

    private Gun weapon;


    public Player(float posX, float posY, float health, TextureAtlas texture, String locTexture) {
        super(posX, posY, health, texture, locTexture, "Player");
        weapon = new Gun();
    }

    public void update(float delta) {
        super.update(delta);
        handleInput(delta);
        weapon.update(delta);



    }

    public Gun getWeapon() {
        return weapon;
    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && body.getLinearVelocity().x <= 1) {
            body.applyLinearImpulse(new Vector2(0.1f, 0), body.getWorldCenter(), true);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && body.getLinearVelocity().x >= -1) {
            body.applyLinearImpulse(new Vector2(-0.1f, 0), body.getWorldCenter(), true);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.UP) && body.getLinearVelocity().y <= 1) {
            body.applyLinearImpulse(new Vector2(0, 0.1f), body.getWorldCenter(), true);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && body.getLinearVelocity().y >= -1) {
            body.applyLinearImpulse(new Vector2(0, -0.1f), body.getWorldCenter(), true);
        }
        else if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            body.setLinearVelocity(0, 0);
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && weapon.canAttack()) {
            attack();
        }
    }

    public void takeDamage(float damage) {
        health -= damage;
        if (health % 10 <= 1) {
            System.out.println("The player now has: " + health + " hp left!");
        }
    }

    public void attack() {
        weapon.attack(body.getPosition().x, body.getPosition().y);
    }


}
