package com.aya.apathy.core;

import com.aya.apathy.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

public class Language {
    public String locale;
    public I18NBundle bundle;

    public Language(){
        locale=Game.playerData.language;
        FileHandle baseFileHandle = Gdx.files.internal("i18n/bundle");
        Locale locale = new Locale(Game.playerData.language);
        bundle = I18NBundle.createBundle(baseFileHandle, locale);
    }

    public void setLanguage(String language){
        locale=language;
        FileHandle baseFileHandle = Gdx.files.internal("i18n/bundle");
        Locale locale = new Locale(language);
        bundle = I18NBundle.createBundle(baseFileHandle, locale);
    }
}
