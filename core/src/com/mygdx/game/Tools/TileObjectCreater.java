package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Launcher;

public class TileObjectCreater {
    public TileObjectCreater(World world, TiledMap map) {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Launcher.PixelsPerMeter, (rect.getY() + rect.getHeight() / 2) / Launcher.PixelsPerMeter);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Launcher.PixelsPerMeter, rect.getHeight() / 2 / Launcher.PixelsPerMeter);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Launcher.PixelsPerMeter, (rect.getY() + rect.getHeight() / 2) / Launcher.PixelsPerMeter);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Launcher.PixelsPerMeter, rect.getHeight() / 2 / Launcher.PixelsPerMeter);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }
}
