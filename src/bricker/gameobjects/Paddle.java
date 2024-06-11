package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Paddle class represents the player-controlled paddle in the game.
 * It extends GameObject and adds user input functionality for paddle movement.
 */
public class Paddle extends GameObject {

    // Minimum distance of the paddle from the screen edge
    private static final float MIN_DISTANCE_FROM_SCREEN_EDGE = 7;

    // Input listener to handle user input
    private final UserInputListener inputListener;

    // Width of the window
    private final float sizeX;

    // Movement speed of the paddle
    private static final float MOVEMENT_SPEED = 300;

    /**
     * Constructs a new Paddle instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param inputListener The input listener for handling user input.
     * @param sizeX         The width of the window.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, float sizeX) {
        super(topLeftCorner, dimensions, renderable);

        this.inputListener = inputListener;
        this.sizeX = sizeX;
    }

    /**
     * Update method to handle paddle movement based on user input.
     *
     * @param deltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Vector representing the movement direction
        Vector2 movementDirection = Vector2.ZERO;

        // Check if the left arrow key is pressed
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDirection = movementDirection.add(Vector2.LEFT);
        }

        // Check if the right arrow key is pressed
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDirection = movementDirection.add(Vector2.RIGHT);
        }

        // Set velocity based on the movement direction and speed
        setVelocity(movementDirection.mult(MOVEMENT_SPEED));

        // Ensure the paddle stays within the screen bounds
        if (getTopLeftCorner().x() < MIN_DISTANCE_FROM_SCREEN_EDGE) {
            transform().setTopLeftCornerX(MIN_DISTANCE_FROM_SCREEN_EDGE);
        }

        if (sizeX - MIN_DISTANCE_FROM_SCREEN_EDGE - getDimensions().x() < getTopLeftCorner().x()) {
            transform().setTopLeftCornerX(sizeX - MIN_DISTANCE_FROM_SCREEN_EDGE - getDimensions().x());
        }
    }
}

