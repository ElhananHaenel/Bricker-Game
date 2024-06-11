package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.utils.LivesManager;

/**
 * The Heart class represents a heart GameObject in the Brick Breaker game.
 * It extends the GameObject class and includes specific functionality for a heart.
 */
public class Heart extends GameObject {

    private final GameObjectCollection gameObjects;  // Collection of GameObjects in the game.
    private final Vector2 windowDimensions;          // Dimensions of the game window.
    private final LivesManager livesManager;         // Manages the player's lives.
    private final String mainPaddleTag;

    /**
     * Constructs a new Heart instance.
     *
     * @param topLeftCorner      Position of the heart in window coordinates (pixels).
     *                           Note that (0,0) is the top-left corner of the window.
     * @param dimensions         Width and height of the heart in window coordinates.
     * @param renderable         The renderable representing the heart.
     *                           Can be null if the heart is not rendered.
     * @param gameObjects        Collection of GameObjects in the game.
     * @param windowDimensions   Dimensions of the game window.
     * @param livesManager       Manages the player's lives.
     * @param mainPaddleTag      Tag of mainPaddle object type.
     */
    public Heart(Vector2 topLeftCorner,
                 Vector2 dimensions,
                 Renderable renderable,
                 GameObjectCollection gameObjects,
                 Vector2 windowDimensions,
                 LivesManager livesManager,
                 String mainPaddleTag) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjects = gameObjects;
        this.windowDimensions = windowDimensions;
        this.livesManager = livesManager;
        this.mainPaddleTag = mainPaddleTag;
    }

    /**
     * Determines whether the heart should collide with another GameObject.
     * Hearts collide with Paddles but not with SecondPaddles.
     *
     * @param other The GameObject to check for collision.
     * @return True if the heart should collide with the specified GameObject, false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(mainPaddleTag);
    }

    /**
     * Handles the logic when a collision occurs with another GameObject.
     * Removes the heart from the game and increments the player's life count.
     *
     * @param other     The GameObject with which the heart collides.
     * @param collision Information about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        gameObjects.removeGameObject(this);
        livesManager.incrementLife();
    }

    /**
     * Updates the heart's position and checks if it has fallen below the game window.
     * If so, removes the heart from the GameObject collection.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.getCenter().y() > windowDimensions.y()){
            gameObjects.removeGameObject(this);
        }
    }
}

