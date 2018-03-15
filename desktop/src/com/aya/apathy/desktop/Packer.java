package com.aya.apathy.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
public class Packer {
    public static void main (String[] args) throws Exception {
        TexturePacker.Settings settings=new TexturePacker.Settings();
        settings.maxWidth=2048;
        settings.maxHeight=2048;
        TexturePacker.process(settings, "b", "a", "game_objects");
    }
}