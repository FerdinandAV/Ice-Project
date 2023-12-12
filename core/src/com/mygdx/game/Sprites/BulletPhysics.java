package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.awt.*;

public class BulletPhysics extends Sprite {

       Rectangle hitbox;
       float x, y;
       float a, time;
       int Speed;
       public Texture text;

       public BulletPhysics(float x, float y, float angle ){
              time = 2;
              Speed = 50;
              this.x = x;
              this.y = y;
              hitbox = new Rectangle(x, y, 50,50);
              text = new Texture("badlogic.jpg");
       }

       public Rectangle getHitBox(){
              return hitbox;
       }

       public void update(float delta){
              hitbox.x += Speed * (float)Math.cos(a) * delta;
              hitbox.y += Speed * (float)Math.sin(a) * delta;
              time -= delta;
       }

        public boolean isDead(){
            if (time < 0) {
                    return true;
            }
            else {
                   return false;
            }
       }

       public void draw(SpriteBatch batch){
              batch.draw(text, hitbox.x, hitbox.y, 100000f,100000f);
       }
}
