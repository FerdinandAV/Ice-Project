package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Entity{


    public Enemy(float posX, float posY, TextureAtlas texture, String locTexture) {
        super(posX,posY,texture,locTexture);

    }

    public void update(float delta, float playerPosX, float playerPosY) {
        super.update(delta);
        move(playerPosX, playerPosY);
    }

    public void move(float posX, float posY) {
        Vector2 direction = new Vector2(posX - body.getPosition().x, posY - body.getPosition().y);

        if (Math.abs(direction.x) + Math.abs(direction.y) < 0.5) {
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
}
