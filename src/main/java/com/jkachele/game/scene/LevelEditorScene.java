/******************************************
 *Project-------Learn-LWJGL
 *File----------LevelEditorScene.java
 *Author--------Justin Kachele
 *Date----------9/25/2022
 *License-------GNU GENERAL PUBLIC LICENSE
 ******************************************/
package com.jkachele.game.scene;

import com.jkachele.game.components.*;
import com.jkachele.game.engine.Camera;
import com.jkachele.game.engine.GameObject;
import com.jkachele.game.engine.Prefabs;
import com.jkachele.game.engine.Transform;
import com.jkachele.game.util.AssetPool;
import com.jkachele.game.util.Constants;
import imgui.ImGui;
import imgui.ImVec2;
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
        levelEditorComponents.addComponent(new GridLines());
        sprites = AssetPool.getSpritesheet("assets/images/spritesheets/decorationsAndBlocks.png");
        marioSprites = AssetPool.getSpritesheet("assets/images/spritesheets/characters.png");
        if (levelLoaded && !reset) {
            if (!gameObjects.isEmpty()) {
                this.currentGameObject = gameObjects.get(0);
            }
            return;
        }
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/spritesheets/decorationsAndBlocks.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/decorationsAndBlocks.png"),
                        16, 16, 81, 0));
        AssetPool.addSpritesheet("assets/images/spritesheets/characters.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/characters.png"),
                        16, 16, 26, 0));
        AssetPool.getTexture("assets/images/blendImageG.png");

        for (GameObject gameObject : gameObjects) {
            if(gameObject.getComponent(SpriteRenderer.class) != null) {
                SpriteRenderer sprite = gameObject.getComponent(SpriteRenderer.class);
                if (sprite.getTexture() != null) {
                    sprite.setTexture((AssetPool.getTexture(sprite.getTexture().getFilepath())));
                }
            }
        }
    }

    float x = 0.0f;
    float y = 0.0f;
    float angle = 0.0f;
    @Override
    public void update(float dt) {
        levelEditorComponents.update(dt);

//        DebugDraw.addBox2D(new Vector2f(200, 200), new Vector2f(128, 64), angle, Color.BLUE.toVector(), 1);
//        angle += 30.0f * dt;
//
//        DebugDraw.addCircle(new Vector2f(x, y), 64, Color.GREEN.toVector(), 32, 1);
//        x += 50.0f * dt;
//        y += 50.0f * dt;

        // Update all game objects in the scene
        for (GameObject gameObject : this.gameObjects) {
            gameObject.update(dt);
        }

        // Render the scene
        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.begin("Test Window");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.getSprite(i);
            float spriteWidth = sprite.getWidth() * 4;
            float spriteHeight = sprite.getHeight() * 4;
            int id = sprite.getTexID();
            Vector2f[] uvCoords = sprite.getUvCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth, spriteHeight,
                    uvCoords[2].x, uvCoords[0].y, uvCoords[0].x, uvCoords[2].y)) {
                GameObject object = Prefabs.generateSpriteObject(sprite, Constants.GRID_WIDTH, Constants.GRID_HEIGHT);
                // Attach this to the mouse cursor
                levelEditorComponents.getComponent(MouseControls.class).pickupObject(object);
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if (i + 1 < sprites.size() && nextButtonX2 < windowX2) {
                ImGui.sameLine();
            }

        }

        ImGui.end();
    }
}
