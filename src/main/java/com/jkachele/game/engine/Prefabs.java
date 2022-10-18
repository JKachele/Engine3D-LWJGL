/******************************************
 *Project-------Learn-LWJGL
 *File----------Prefabs.java
 *Author--------Justin Kachele
 *Date----------10/8/2022
 *License-------GNU GENERAL PUBLIC LICENSE
 ******************************************/
package com.jkachele.game.engine;

import com.jkachele.game.components.Sprite;
import com.jkachele.game.components.SpriteRenderer;
import org.joml.Vector3f;

public class Prefabs {
    public static GameObject generateSpriteObject(Sprite sprites, float sizeX, float sizeY, float sizeZ) {
        GameObject block = new GameObject("Sprite_Object_Gen",
                new Transform(new Vector3f(), new Vector3f(sizeX, sizeY, sizeZ)), 0);
        SpriteRenderer renderer = new SpriteRenderer();
        renderer.setSprite(sprites);
        block.addComponent(renderer);
        return block;
    }
}
