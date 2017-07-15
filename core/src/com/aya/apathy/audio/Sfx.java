package com.aya.apathy.audio;

import com.aya.apathy.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sfx {
    private Sound gameWin, pickUpCoin, pickUpPrequark, jump, jumpOverEnemy, teleport, dragon, johnCena, clickPositive, clickNegative;
    public enum SoundType{GAME_WIN, PICK_UP_COIN, PICK_UP_PREQUARK, JUMP, JUMP_OVER_ENEMY, TELEPORT, DRAGON, JOHN_CENA, CLICK_POSITIVE, CLICK_NEGATIVE}
    private boolean enabled;

    public Sfx(){
        loadUISfx();
        enabled = Game.playerData.sfx;
    }

    private void loadUISfx(){
        clickNegative=Gdx.audio.newSound(Gdx.files.internal("sfx/click_negative.wav"));
        clickPositive=Gdx.audio.newSound(Gdx.files.internal("sfx/click_positive.mp3"));
    }

    public void loadGameSfx(){
        gameWin=Gdx.audio.newSound(Gdx.files.internal("sfx/game_win.mp3"));
        pickUpCoin=Gdx.audio.newSound(Gdx.files.internal("sfx/pick_up_coin.wav"));
        pickUpPrequark=Gdx.audio.newSound(Gdx.files.internal("sfx/pick_up_prequark.mp3"));
        jump=Gdx.audio.newSound(Gdx.files.internal("sfx/jump.wav"));
        jumpOverEnemy=Gdx.audio.newSound(Gdx.files.internal("sfx/jump_over_enemy.wav"));
        teleport=Gdx.audio.newSound(Gdx.files.internal("sfx/teleport.mp3"));
        dragon=Gdx.audio.newSound(Gdx.files.internal("sfx/dragon.ogg"));
        johnCena=Gdx.audio.newSound(Gdx.files.internal("sfx/john_cena.mp3"));
    }

    public void play(SoundType soundType){
        if (enabled) {
            switch (soundType) {
                case CLICK_NEGATIVE:clickNegative.play();break;
                case CLICK_POSITIVE:clickPositive.play();break;
                case PICK_UP_COIN:pickUpCoin.play();break;
                case PICK_UP_PREQUARK:pickUpPrequark.play();break;
                case DRAGON:dragon.play();break;
                case TELEPORT:teleport.play();break;
                case JUMP:jump.play();break;
                case JUMP_OVER_ENEMY:jumpOverEnemy.play();break;
                case JOHN_CENA:johnCena.play();break;
                case GAME_WIN:gameWin.play();break;
            }
        }
    }

    public void setEnabled(boolean enabled){
        this.enabled =enabled;
    }

    public void disposeGameSfx(){
        gameWin.dispose();
        pickUpCoin.dispose();
        pickUpPrequark.dispose();
        dragon.dispose();
        teleport.dispose();
        jump.dispose();
        jumpOverEnemy.dispose();
        johnCena.dispose();
    }

    public boolean getSFX() {
        return enabled;
    }
}
