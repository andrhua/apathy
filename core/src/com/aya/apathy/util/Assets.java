package com.aya.apathy.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Assets {
    public static final String OTHER_LOCKED="1", OTHER_CHECKED="0", OTHER_ARROW_LEFT= "2", OTHER_ARROW_RIGHT="3";
    public static TextureAtlas other, backgrounds_preview;
    public static BitmapFont main10, main20, main15, main4;
    private final static String FONT_CHARS = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";

    public Assets(){
        FreeTypeFontGenerator generator=new FreeTypeFontGenerator(Gdx.files.internal("font/main.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter=new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.color= Color.BLACK;
        parameter.characters=FONT_CHARS;
        parameter.size=Constants.HEIGHT/20;
        main20=generator.generateFont(parameter);
        parameter.size=Constants.HEIGHT/10;
        main10=generator.generateFont(parameter);
        parameter.size= Constants.HEIGHT/4;
        main4=generator.generateFont(parameter);
        generator.dispose();
        other=new TextureAtlas(Gdx.files.internal("atlas/other.atlas"));
        backgrounds_preview=new TextureAtlas(Gdx.files.internal("atlas/backgrounds_preview.atlas"));
    }


    public static class Game{
        public static Color wall, background;
        public static TextureAtlas backgrounds, game_objects;
        public static Sprite joe;
        public static BitmapFont main;
        public enum Object {
            PREQUARK, COIN, WOOMBA, CLOUD, TELEPORT, DRAGON, MUK,
            SHURIKEN, PANHANDLER, OCTOPUS, KEY, TANGELO, MACHAMP,
            LAIR, AMPHIBIAN, FISH, SPIDER, BIRD, BANANA, GHOST,
            GRAVITY_SWITCHER, PACMAN_GHOST, BLASTOISE, SPIKE
        }

        public Game(){
            backgrounds=new TextureAtlas(Gdx.files.internal("atlas/backgrounds.atlas"));
            game_objects=new TextureAtlas(Gdx.files.internal("atlas/game_objects.atlas"));
            joe=new Sprite(new Texture(Gdx.files.internal("atlas/joe.png")));
            FreeTypeFontGenerator generator=new FreeTypeFontGenerator(Gdx.files.internal("font/main.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter=new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.color= Color.BLACK;
            parameter.characters=FONT_CHARS;
            parameter.size=10;
            main=generator.generateFont(parameter);
            main.setUseIntegerPositions(false);
            main.getData().setScale(.15f);
            generator.dispose();
        }

    }
}
