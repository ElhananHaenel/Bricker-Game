package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * The MainBall class represents the main ball GameObject in the Brick Breaker game.
 * It extends the MainBall class and includes additional functionality specific to the main ball.
 */
public class Ball extends BasicBall {

    private final Counter hitsCounter = new Counter();   // Counter to track the number of hits.

    /**
     * Constructs a new MainBall instance.
     *
     * @param topLeftCorner      Position of the main ball in window coordinates (pixels).
     *                           Note that (0,0) is the top-left corner of the window.
     * @param dimensions         Width and height of the main ball in window coordinates.
     * @param renderable         The renderable representing the main ball.
     *                           Can be null if the main ball is not rendered.
     * @param collisionSound     The sound played on collision with other GameObjects.
     * @param windowDimensions   Dimensions of the game window.
     * @param gameObjects        Collection of GameObjects in the game.
     */
    public Ball(Vector2 topLeftCorner,
                    Vector2 dimensions,
                    Renderable renderable,
                    Sound collisionSound,
                    Vector2 windowDimensions,
                    GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable, windowDimensions, collisionSound, gameObjects);
        // Sound played on collision with other GameObjects.
    }

    /**
     * Handles the logic when a collision occurs with another GameObject.
     * Increments the hits counter and performs the default ball collision handling.
     *
     * @param other     The GameObject with which the main ball collides.
     * @param collision Information about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        hitsCounter.increment();
    }

    /**
     * Retrieves the number of hits the main ball has encountered.
     *
     * @return The number of hits the main ball has encountered.
     */
    public int getCollisionCounter() {
        return hitsCounter.value();
    }
}



