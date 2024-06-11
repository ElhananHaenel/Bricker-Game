package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The GraphicLifeCounter class represents a visual representation of the player's life counter
 * in the Brick Breaker game. It extends the GameObject class and includes specific functionality
 * for a graphical life counter.
 */
public class GraphicLifeCounter extends GameObject {

    /**
     * Constructs a new GraphicLifeCounter instance.
     *
     * @param topLeftCorner Position of the life counter in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height of the life counter in window coordinates.
     * @param renderable    The renderable representing the life counter.
     *                      Can be null if the life counter is not rendered.
     */
    public GraphicLifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
    }

}
