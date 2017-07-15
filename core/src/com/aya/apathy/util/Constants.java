package com.aya.apathy.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class Constants {

    public static int WIDTH, HEIGHT, PPM, TPX,
            CAMERA_WIDTH, CAMERA_HEIGHT, CAMERA_WIDTH_50, CAMERA_HEIGHT_50;
    public static Color COLOR[], BG[];
    public static float BUTTON_SCALE, SCALE_DURATION;

    public Constants(){
        BUTTON_SCALE=.88f;
        SCALE_DURATION=.005f;
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        CAMERA_WIDTH =WIDTH;
        CAMERA_HEIGHT =HEIGHT;
        CAMERA_WIDTH_50=WIDTH/2;
        CAMERA_HEIGHT_50=HEIGHT/2;
        TPX=32;
        PPM=WIDTH/25;
        COLOR=new Color[6];
        COLOR[0]=Color.valueOf("#b3e8bd");
        COLOR[1]=Color.valueOf("#fbab1e");
        COLOR[2]=Color.valueOf("#1fcecb");
        COLOR[3]=Color.valueOf("#415065");
        COLOR[4]=Color.valueOf("#011e1a");
        COLOR[5]=Color.valueOf("#f5fba8");
        BG=new Color[3];
        BG[0]=Color.valueOf("#4CAF50");
        BG[1]=Color.valueOf("#d76e00");
        BG[2]=Color.valueOf("#415065");
    }
}
