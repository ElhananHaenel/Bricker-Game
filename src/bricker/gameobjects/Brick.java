package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The Brick class represents a brick GameObject in the Brick Breaker game.
 * It extends the GameObject class and includes specific functionality for a brick.
 */
public class Brick extends GameObject {

    /**
     * the tag value for an active brick, meaning a brick which is in the game.
     */
    public static final String ACTIVE_TAG = "ACTIVE";
    /**
     * the tag value for an inactive brick, meaning a brick which is not in the game.
     */
    public static final String INACTIVE_TAG = "INACTIVE";
    private final CollisionStrategy collisionStrategy;  // Strategy for handling collisions with the brick.
    private boolean collided = false;

    /**
     * Constructs a new Brick instance.
     *
     * @param topLeftCorner      Position of the brick in window coordinates (pixels).
     *                           Note that (0,0) is the top-left corner of the window.
     * @param dimensions         Width and height of the brick in window coordinates.
     * @param renderable         The renderable representing the brick. Can be null if not rendered.
     * @param collisionStrategy  The strategy for handling collisions with the brick.
     */
    public Brick(Vector2 topLeftCorner,
                 Vector2 dimensions,
                 Renderable renderable,
                 CollisionStrategy collisionStrategy){
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.setTag(ACTIVE_TAG);
    }

    /**
     * Handles the logic when a collision occurs with another GameObject.
     * Delegates the collision handling to the specified collision strategy.
     *
     * @param other     The GameObject with which the brick collides.
     * @param collision Information about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (collided) return;
        collided = true;
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other);
    }
}
