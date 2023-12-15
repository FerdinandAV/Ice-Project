package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Launcher;

public abstract class Entity extends Sprite{

    public float posX, posY;
    public Body body;
    public float health;

    public enum State {IDLING, RUNNING};
    public State currentState, previousState;

    public Animation<TextureRegion> runAnimationTexture, idleAnimationTexture;

    private float stateTimer;
    private boolean runningRight;
    String type;

    public Entity(float posX, float posY, float health, TextureAtlas texture, String locTexture, String type) {
        super(texture.findRegion(locTexture));
        this.posX = posX;
        this.posY = posY;
        this.health = health;

        currentState = State.IDLING;
        previousState = State.IDLING;

        stateTimer = 0;
        runningRight = true;

        if (type.equals("Player")) {
            idleAnimationTexture = animation(0,3, 0.25f);
            runAnimationTexture = animation(3,7,0.2f);
        }
        else if (type.equals("Enemy")) {
            runAnimationTexture = animation(8,11,0.1f);
            idleAnimationTexture = animation(8,11,0.1f);
        }


        setBounds(0, 0, 16 / Launcher.PixelsPerMeter, 28 / Launcher.PixelsPerMeter);
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
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 4);
        setRegion(getFrame(delta));
    }

    public void createCollision(World world) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(posX / Launcher.PixelsPerMeter, posY / Launcher.PixelsPerMeter);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(0.01f);
        fdef.shape = shape;
        body.createFixture(fdef);


    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();

        TextureRegion region = null;
        switch (currentState) {
            case RUNNING:
                region = (TextureRegion) runAnimationTexture.getKeyFrame(stateTimer, true);
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

    public abstract void takeDamage(float damage);

    public abstract void attack();

    public float getHealth() {
        return health;
    }

}
