package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.DemonSlayer;
import com.mygdx.game.MapLevels.Platform;


public class Slayer extends Sprite {
    public enum State {IDLING, RUNNING, STANDING}

    public State currentState;
    public State previousState;
    public World world;
    public Body playerBody;
    private Animation<TextureRegion> playerRun;
    private Animation<TextureRegion> playerIdle;

    private TextureRegion playerStand;

    private float stateTimer;
    private boolean runningRight;

    public Slayer(World world, Platform screen) {
        super(screen.getAtlas().findRegion("Knight_Animation"));

        this.world = world;
        currentState = State.IDLING;
        previousState = State.IDLING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 4; i < 8; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 28));
        playerRun = new Animation(0.25f, frames);
        frames.clear();


        for (int i = 1; i < 4; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 28));
        playerIdle = new Animation(0.5f, frames);

        playerStand = new TextureRegion(getTexture(), 0, 0, 16, 28);

        definePlayer();
        setBounds(0, 0, 16 / DemonSlayer.PPM, 28 / DemonSlayer.PPM);
        setRegion(playerStand);

    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && playerBody.getLinearVelocity().x <= 1)
            playerBody.applyLinearImpulse(new Vector2(0.1f, 0), playerBody.getWorldCenter(), true);
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && playerBody.getLinearVelocity().x >= -1)
            playerBody.applyLinearImpulse(new Vector2(-0.1f, 0), playerBody.getWorldCenter(), true);
        else if (Gdx.input.isKeyPressed(Input.Keys.UP) && playerBody.getLinearVelocity().y <= 1)
            playerBody.applyLinearImpulse(new Vector2(0, 0.1f), playerBody.getWorldCenter(), true);
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && playerBody.getLinearVelocity().y >= -1)
            playerBody.applyLinearImpulse(new Vector2(0, -0.1f), playerBody.getWorldCenter(), true);


        else if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
            playerBody.setLinearVelocity(0, 0);
    }

    /**
     * Creates the player in the shape of a circle
     */


    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(DemonSlayer.V_HEIGHT / DemonSlayer.PPM, DemonSlayer.V_HEIGHT / DemonSlayer.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        playerBody = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(0f);
        fdef.shape = shape;
        playerBody.createFixture(fdef);
    }


    public void Bullet(int Startx,int Starty){

    }







    public void update(float dt) {
        setPosition(playerBody.getPosition().x - getWidth() / 2, playerBody.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
        handleInput(dt);
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case RUNNING:
                region = (TextureRegion) playerRun.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
            case IDLING:
                region = (TextureRegion) playerIdle.getKeyFrame(stateTimer, true);
                break;
            default:
                region = playerStand;
                break;
        }

        if ((playerBody.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((playerBody.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;

    }

    public State getState() {
        if (playerBody.getLinearVelocity().x != 0 || playerBody.getLinearVelocity().y != 0) {
            return State.RUNNING;
        } else {
            return State.IDLING;
        }
    }
}
