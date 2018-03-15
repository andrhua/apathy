package com.aya.apathy.game.object;

import com.aya.apathy.core.Updatable;
import com.aya.apathy.game.tiled.core.MapObject;

abstract public class DynamicGameObject extends StaticGameObject implements Updatable, Resetable {
    public DynamicGameObject(int id, MapObject mapObject, int zOrder) {
        super(id, mapObject, zOrder);
    }


}
