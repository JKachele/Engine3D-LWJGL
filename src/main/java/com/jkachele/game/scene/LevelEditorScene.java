/******************************************
 *Project-------Learn-LWJGL
 *File----------LevelEditorScene.java
 *Author--------Justin Kachele
 *Date----------9/25/2022
 *License-------GNU GENERAL PUBLIC LICENSE
 ******************************************/
package com.jkachele.game.scene;

import com.jkachele.game.components.MouseControls;
import com.jkachele.game.components.SpriteRenderer;
import com.jkachele.game.components.Spritesheet;
import com.jkachele.game.engine.Camera;
import com.jkachele.game.engine.GameObject;
import com.jkachele.game.engine.Transform;
import com.jkachele.game.util.AssetPool;
import org.joml.Vector2f;

public class LevelEditorScene extends Scene {

    private GameObject obj1;
    private GameObject obj2;
    private GameObject obj3;
    Spritesheet sprites;
    Spritesheet marioSprites;
    GameObject levelEditorComponents;

    @Override
    public void init(boolean reset) {
        loadResources();
        this.camera = new Camera(new Vector2f());
        levelEditorComponents = new GameObject("LevelEditor", new Transform(), 0);
        levelEditorComponents.addComponent(new MouseControls());
        //sprites = AssetPool.getSpritesheet("assets/images/spritesheets/decorationsAndBlocks.png");
        // marioSprites = AssetPool.getSpritesheet("assets/images/spritesheets/characters.png");
        if (levelLoaded && !reset) {
            if (!gameObjects.isEmpty()) {
                this.currentGameObject = gameObjects.get(0);
            }
            return;
        }

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(-128, -128),
                new Vector2f(256, 256)), 0);
        SpriteRenderer obj1Sprite = new SpriteRenderer();
        obj1Sprite.setTexture(AssetPool.getTexture("assets/images/TestImage.png"));
        obj1.addComponent(obj1Sprite);
        this.addGameObject(obj1);
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

//        AssetPool.addSpritesheet("assets/images/spritesheets/decorationsAndBlocks.png",
//                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/decorationsAndBlocks.png"),
//                        16, 16, 81, 0));
        AssetPool.addSpritesheet("assets/images/spritesheets/characters.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/characters.png"),
                        16, 16, 26, 0));
        AssetPool.getTexture("assets/images/blendImageG.png");
        AssetPool.getTexture("assets/images/TestImage.png");

        for (GameObject gameObject : gameObjects) {
            if(gameObject.getComponent(SpriteRenderer.class) != null) {
                SpriteRenderer sprite = gameObject.getComponent(SpriteRenderer.class);
                if (sprite.getTexture() != null) {
                    sprite.setTexture((AssetPool.getTexture(sprite.getTexture().getFilepath())));
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        levelEditorComponents.update(dt);

        // Update all game objects in the scene
        for (GameObject gameObject : this.gameObjects) {
            gameObject.update(dt);
        }

        // Render the scene
        this.renderer.render();
    }
}
