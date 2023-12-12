

package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class block {
    Rectangle hitBox;
    Texture text;

    public block(int x, int y) {
        hitBox = new Rectangle(x,y,50,50);
        text = new Texture("badlogic.jpg");
    }

    public boolean doesHit(Rectangle r){
        return r.overlaps(hitBox);
    }

     public void draw(SpriteBatch batch){
             batch.draw(text, hitBox.x, hitBox.y, 50,50 );
     }

     public void hitEffect() {
     System.out.println("You hit me!");
     }

}

