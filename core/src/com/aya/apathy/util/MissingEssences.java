package com.aya.apathy.util;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class MissingEssences {
    public static final float DEG2RAD=0.017453292F;
    private static Matrix4 transform;

    public static Vector2 abs(Vector2 vector2){
        return new Vector2(Math.abs(vector2.x), Math.abs(vector2.y));
    }

    public static Vector2 negate(Vector2 vector2) {
        return new Vector2(-vector2.x, -vector2.y);
    }


}
