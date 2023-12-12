package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Gun;
import com.mygdx.game.Weapon;

public class SlayerNew extends Entity {

    private Gun weapon;

    public SlayerNew(float posX, float posY, TextureAtlas texture, String locTexture) {
        super(posX, posY, texture, locTexture);
        weapon = new Gun();
    }

    public void update(float delta) {
        super.update(delta);
        handleInput(delta);
        weapon.update();
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
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            weapon.attack(body.getPosition().x, body.getPosition().y);
            System.out.println(body.getPosition().x + ", " + body.getPosition().y + "; " + Gdx.input.getX() / 100f + ", " + Gdx.input.getY() / 100f);
        }
    }


}
