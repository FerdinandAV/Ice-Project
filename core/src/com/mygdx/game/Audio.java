package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Audio extends ApplicationAdapter {

    Sound sound;


    public void ThemeSound() {
        sound = Gdx.audio.newSound(Gdx.files.internal("712303__universfield__horror-background-atmosphere-wandering-soul.mp3"));
        long ID = sound.play();

        sound.setVolume(ID,0.3f);
        sound.setLooping(ID, true);
        }



    public void MenuSound(){
        sound = Gdx.audio.newSound(Gdx.files.internal("108907__elanhickler__gates-of-heaven.flac"));
        long id = sound.play();

        sound.setVolume(id,0.7f);
        sound.setLooping(id, true);
    }




    public void PlayerGettingHitSound(){
        sound = Gdx.audio.newSound(Gdx.files.internal("163441__under7dude__man-getting-hit.wav"));
        long id = sound.play();

        sound.setVolume(id,0.7f);

    }




    public void PlayerDeathSound(){
        sound = Gdx.audio.newSound(Gdx.files.internal("202037__thestigmata__man-die.wav"));
        long id = sound.play();

        sound.setVolume(id,0.7f);


    }



    public void BossSound(){
        sound = Gdx.audio.newSound(Gdx.files.internal("249686__cylon8472__cthulhu-growl.wav"));
        long id = sound.play();

        sound.setVolume(id,0.7f);

    }



    public void BossChaseSound(){
        sound = Gdx.audio.newSound(Gdx.files.internal("317385__satanen__the-killer-is-coming-for-you.wav"));
        long id = sound.play();

        sound.setVolume(id,0.7f);


    }






    @Override
    public void render() {

    }
}