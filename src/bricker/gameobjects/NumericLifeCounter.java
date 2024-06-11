package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;


/**
 * The NumericLifeCounter class represents a numeric life counter GameObject in the Brick Breaker game.
 * It extends the GameObject class and includes specific functionality for a numeric life counter.
 */
public class NumericLifeCounter extends GameObject {


    /**
     * Constructs a new NumericLifeCounter instance.
     *
     * @param topLeftCorner Position of the numeric life counter in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height of the numeric life counter in window coordinates.
     * @param renderable    The renderable representing the numeric life counter.
     *                      Can be null if the numeric life counter is not rendered.
     */
    public NumericLifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
    }

    /**
     * Updates the numeric life counter. No specific logic implemented in this method.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // Additional update logic can be added here if needed.
    }
}
