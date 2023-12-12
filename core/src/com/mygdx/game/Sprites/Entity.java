package com.mygdx.game.Sprites;

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

public class Entity extends Sprite{

    float posX, posY;
    public Body body;

    public enum State {IDLING, RUNNING};
    public State currentState, previousState;

    private Animation<TextureRegion> runAnimationTexture, idleAnimationTexture;

    private float stateTimer;
    private boolean runningRight;

    public Entity(float posX, float posY, TextureAtlas texture, String locTexture) {
        super(texture.findRegion(locTexture));
        this.posX = posX;
        this.posY = posY;

        currentState = State.IDLING;
        previousState = State.IDLING;

        stateTimer = 0;
        runningRight = true;

        idleAnimationTexture = animation(0,3, 0.25f);
        runAnimationTexture = animation(3,7,0.5f);

        setBounds(0, 0, 16 / DemonSlayer.PPM, 28 / DemonSlayer.PPM);
        setRegion(new TextureRegion(getTexture(), 0, 0, 16, 28));
    }

    public Animation animation(int start, int end, float duration) {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = start; i < end; i++) {
            frames.add(new TextureRegion(getTexture(), (i+1) * 16, 0, 16, 28));
        }
        return new Animation(duration, frames);
    }

    public void update(float delta) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(delta));
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();

        TextureRegion region = null;
        switch (currentState) {
            case RUNNING:
                region = (TextureRegion) idleAnimationTexture.getKeyFrame(stateTimer, true);
                break;
            case IDLING:
                region = (TextureRegion) idleAnimationTexture.getKeyFrame(stateTimer, true);
                break;
        }

        if ((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }

        else if ((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;

    }

    public State getState() {
        if (body.getLinearVelocity().x != 0 || body.getLinearVelocity().y != 0) {
            return State.RUNNING;
        }

        else {
            return State.IDLING;
        }
    }

}
