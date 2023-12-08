package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.DemonSlayer;
import com.mygdx.game.MapLevels.Platform;

public class Demons extends Sprite {
    public enum State {IDLING, RUNNING, STANDING}

    public State currentState;
    public State previousState;
    public World world;
    public Body demonBody;
    private Animation<TextureRegion> DemonRun;
    private Animation<TextureRegion> DemonIdle;

    private TextureRegion DemonStand;

    private float stateTimer;
    private boolean runningRight;

    public Demons(World world, Platform screen) {
        super(screen.getAtlas().findRegion("Demon_Imp"));

        this.world = world;
        currentState = State.RUNNING;
        previousState = State.RUNNING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 8; i < 12; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 16, 16, 16));
        DemonRun = new Animation(1f, frames);
        frames.clear();


        DemonStand = new TextureRegion(getTexture(), 130, 16, 16, 16);

        defineDemon();
        setBounds(0, 0, 16 / DemonSlayer.PPM, 16 / DemonSlayer.PPM);
        setRegion(DemonStand);

    }

    /**
     * Creates the Demon in the shape of a circle
     */

/*
     Demon(PlayScreen, Float x, Float y) {
     this.world = screen.getWorld();
     this.screen = screen;
     defineDemon();
     velocity = new vector2(-1,-2);
     demonbody.setActive(false);
    */
    public void defineDemon() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(16 / DemonSlayer.PPM, 16 / DemonSlayer.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        demonBody = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(0.1f / DemonSlayer.PPM);
        fdef.shape = shape;
        demonBody.createFixture(fdef);
    }


    public void update(float dt, float posX, float posY) {
        setPosition(demonBody.getPosition().x - getWidth() / 2, demonBody.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
        move(posX, posY);

    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case RUNNING:
                region = (TextureRegion) DemonRun.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
            default:
                region = DemonStand;
                break;
        }

        if ((demonBody.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((demonBody.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;

    }

    public State getState() {
        if (demonBody.getLinearVelocity().x != 0 || demonBody.getLinearVelocity().y != 0) {
            return State.RUNNING;
        } else {
            return State.IDLING;
        }
    }

    public void move(float playerPosX, float playerPosY) {
        Vector2 direction = new Vector2(playerPosX - demonBody.getPosition().x, playerPosY - demonBody.getPosition().y);
        /*if (direction.x > 20 && direction.y > 20) {
            demonBody.applyLinearImpulse(new Vector2(direction.x, direction.y), demonBody.getWorldCenter(), true);
        }*/
        System.out.println(Math.abs(direction.x + direction.y));

        if (Math.abs(direction.x) + Math.abs(direction.y) < 0.5) {
            demonBody.setLinearVelocity(0, 0);
        }

        else if ((Math.abs(demonBody.getLinearVelocity().x) <= 1 || Math.abs(demonBody.getLinearVelocity().y) <= 1)) {
            direction = direction.nor();
            demonBody.applyLinearImpulse(new Vector2(direction.x, direction.y), demonBody.getWorldCenter(), true);
        }


        //demonBody.applyLinearImpulse(new Vector2(direction.x, direction.y), demonBody.getWorldCenter(), true);
    }
}


