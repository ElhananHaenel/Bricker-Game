package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * The SecondPaddle class represents the second paddle GameObject in the Brick Breaker game.
 * It extends the Paddle class and includes additional functionality specific to the second paddle.
 */
public class SecondPaddle extends Paddle {

    private static final int MAX_HITS = 4;   // Maximum number of hits before the second paddle is removed.
    private static boolean active = false;
    private final Counter hitsCounter = new Counter();   // Counter to track the number of hits.
    private final GameObjectCollection objectCollection;  // Collection of GameObjects in the game.

    /**
     * Constructs a new SecondPaddle instance.
     *
     * @param topLeftCorner        Position of the second paddle in window coordinates (pixels).
     *                             Note that (0,0) is the top-left corner of the window.
     * @param dimensions           Width and height of the second paddle in window coordinates.
     * @param renderable           The renderable representing the second paddle.
     *                             Can be null if the second paddle is not rendered.
     * @param inputListener        Input listener for keyboard events.
     * @param windowXDimension     Width of the game window.
     * @param objectCollection     Collection of GameObjects in the game.
     */
    public SecondPaddle(Vector2 topLeftCorner,
                        Vector2 dimensions,
                        Renderable renderable,
                        UserInputListener inputListener,
                        float windowXDimension,
                        GameObjectCollection objectCollection) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowXDimension);
        this.objectCollection = objectCollection;
    }

    /**
     * Handles the logic when a collision occurs with another GameObject.
     * Increments the hits counter and removes the second paddle if the maximum hits are reached.
     *
     * @param other     The GameObject with which the second paddle collides.
     * @param collision Information about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        hitsCounter.increment();
        if (hitsCounter.value() == MAX_HITS){
            objectCollection.removeGameObject(this);
            active = false;
//            addPaddleStrategy.setSecondPaddle(false);
        }
    }

    /**
     * return true if secondPaddle is active, otherwise - false.
     * @return true if secondPaddle is active, otherwise - false.
     */
    public static boolean isActive(){
        return active;
    }

    /**
     * mark secondPaddle as active.
     */
    public static void activate(){
        active = true;
    }
}
