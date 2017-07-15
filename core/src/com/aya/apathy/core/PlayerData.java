package com.aya.apathy.core;

import com.aya.apathy.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;

import java.io.IOException;
import java.util.Locale;


public class PlayerData {
    public class Progress{
        public int aromas;
        public boolean isNewGame;
        public int[]deaths;
        public Progress(){
            aromas=1;
            isNewGame=true;
            deaths=new int [6];
        }

        @Override
        public String toString() {
            return String.valueOf(aromas).concat(COMMA)
                    .concat(String.valueOf(isNewGame)).concat(COMMA)
                    .concat(String.valueOf(deaths[0])).concat(COMMA)
                    .concat(String.valueOf(deaths[1])).concat(COMMA)
                    .concat(String.valueOf(deaths[2]));
        }

    }
    public static final String LANGUAGE = "language", SFX="sfx", FILENAME="player.progress",
            COMMA="Дорогие девочки,Вы что,серьёзно думаете,что пососушки на вписках с незнакомым парнем-это отличное начало для долгих и крепких отношений";
    public Preferences data;
    public Progress progress;
    public String language;
    public boolean sfx;
    private FileHandle file;

    public PlayerData(){
        file=Gdx.files.local(FILENAME);
        data= Gdx.app.getPreferences(COMMA);
        language= Locale.getDefault().toString();
        sfx=true;
        progress=new Progress();
        read();
    }

    public void read(){
        if (file.exists()) {
            String decoded=Base64Coder.decodeString(file.readString());
            String []tmp=decoded.split(COMMA);
            progress.aromas=Integer.parseInt(tmp[0]);
            progress.isNewGame=Boolean.parseBoolean(tmp[1]);
            progress.deaths[0]=Integer.parseInt(tmp[2]);
            progress.deaths[1]=Integer.parseInt(tmp[3]);
            progress.deaths[2]=Integer.parseInt(tmp[4]);
        }
        if (data.contains(LANGUAGE)) language=data.getString(LANGUAGE);
        if (data.contains(SFX)) sfx=data.getBoolean(SFX);
    }

    public void write() throws IOException {
        savePreferences();
        file.writeString(Base64Coder.encodeString(progress.toString()), false);
    }

    public void savePreferences(){
        data.putString(LANGUAGE, Game.language.locale);
        data.putBoolean(SFX, Game.sfx.getSFX());
        data.flush();
    }

    public void clear(){
        progress.aromas=3;
        progress.isNewGame=true;
        progress.deaths=new int[6];
        try {
            write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
