package com.mygdx.game.Tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class Camera {

    public OrthographicCamera camera;

    public Camera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
    }

    public void update(float posx, float posy) {
        camera.position.set(new Vector2(posx, posy), 0);
        camera.update();

    }

}
