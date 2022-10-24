/******************************************
 *Project-------Engine3D-LWJGL
 *File----------Component.java
 *Author--------Justin Kachele
 *Date----------10/3/2022
 *License-------MIT License
 ******************************************/
package com.jkachele.game.components;

import com.jkachele.game.engine.GameObject;

public abstract class Component {
    private static int ID_COUNTER = 0;
    private int uid = -1;

    public transient GameObject gameObject = null;

    public void update(float dt) {

    }

    public void start() {

    }

    public void generateID() {
        if (this.uid == -1) {
            this.uid = ID_COUNTER;
            ID_COUNTER++;
        }
    }

    public int getUid() {
        return this.uid;
    }

    public static void init(int maxID) {
        ID_COUNTER = maxID;
    }
}
