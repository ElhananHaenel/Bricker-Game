package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

/**
 * The BasicBall class represents a ball GameObject in the Bricker game.
 * It extends the GameObject class and includes specific functionality for a ball.
 */
public class BasicBall extends GameObject {

    private static final float BALL_SPEED = 250;
    private final Vector2 windowDimension;   // The dimensions of the game window.
    private final Sound onCollisionSound;    // The sound played on collision with other GameObjects.
    private final GameObjectCollection gameObjects;  // Collection of GameObjects in the game.

    /**
     * Constructs a new Ball instance.
     *
     * @param topLeftCorner      Position of the ball in window coordinates (pixels).
     *                           Note that (0,0) is the top-left corner of the window.
     * @param dimensions         Width and height of the ball in window coordinates.
     * @param renderable         The renderable representing the ball. Can be null if not rendered.
     * @param windowDimension    Dimensions of the game window.
     * @param onCollisionSound   The sound played on collision with other GameObjects.
     * @param gameObjects        Collection of GameObjects in the game.
     */
    public BasicBall(Vector2 topLeftCorner,
                     Vector2 dimensions,
                     Renderable renderable,
                     Vector2 windowDimension,
                     Sound onCollisionSound,
                     GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable);
        this.windowDimension = windowDimension;
        this.onCollisionSound = onCollisionSound;
        this.gameObjects = gameObjects;
    }

    /**
     * Handles the logic when a collision occurs with another GameObject.
     * Reverses the ball's velocity and plays the collision sound.
     *
     * @param other     The GameObject with which the ball collides.
     * @param collision Information about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        // Reverse the ball's velocity based on the collision normal
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        this.setVelocity(newVel);
        // Play the collision sound
        onCollisionSound.play();
    }

    /**
     * Updates the ball's position and checks if it has fallen below the game window.
     * If so, removes the ball from the GameObject collection.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // Check if the ball has fallen below the game window
        if (this.getCenter().y() > windowDimension.y()){
            // Remove the ball from the GameObject collection
            gameObjects.removeGameObject(this);
        }
    }

    /**
     * Sets a random velocity for the ball.
     * The velocity can be positive or negative in both x and y directions.
     */
    public void setRandomVelocity() {
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random random = new Random();
        // Randomly reverse the velocity in x direction
        if (random.nextBoolean()) {
            ballVelX *= -1;
        }
        // Randomly reverse the velocity in y direction
        if (random.nextBoolean()) {
            ballVelY *= -1;
        }
        // Set the random velocity for the ball
        this.setVelocity(new Vector2(ballVelX, ballVelY));
    }
}
