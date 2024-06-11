package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.gameobjects.Paddle;
import bricker.gameobjects.SecondPaddle;

/**
 * The AddPaddleStrategy class implements the CollisionStrategy interface
 * and defines the strategy for handling collisions that add a second paddle
 * in the Brick Breaker game. It also serves as a communication point to inform
 * about the existence of the second paddle.
 */
class AddPaddleStrategy implements CollisionStrategy {

    private final GameObjectCollection gameObjects;  // Collection of GameObjects in the game.
//    private static boolean secondPaddle;            // Flag indicating the existence of the second paddle.
    private final Vector2 windowDimensions;          // Dimensions of the game window.
    private final Renderable paddleImage;            // Renderable representing the paddle image.
    private final UserInputListener inputListener;   // Input listener for keyboard events.
    private final Vector2 paddleSize;                // Size of the paddle.
    private final BasicCollisionStrategy basicCollisionStrategy;

    /**
     * Constructs a new AddPaddleStrategy instance.
     *
     * @param gameObjects       Collection of GameObjects in the game.
     * @param bricksCounter     Counter for tracking the number of bricks.
     * @param windowDimensions  Dimensions of the game window.
     * @param paddleImage       Renderable representing the paddle image.
     * @param inputListener     Input listener for keyboard events.
     * @param paddleSize        Size of the paddle.
     */
    public AddPaddleStrategy(GameObjectCollection gameObjects,
                             Counter bricksCounter,
                             Vector2 windowDimensions,
                             Renderable paddleImage,
                             UserInputListener inputListener,
                             Vector2 paddleSize) {
        basicCollisionStrategy = new BasicCollisionStrategy(gameObjects, bricksCounter);
        this.gameObjects = gameObjects;
        this.windowDimensions = windowDimensions;
        this.paddleImage = paddleImage;
        this.inputListener = inputListener;
        this.paddleSize = paddleSize;
    }

    /**
     * Handles the logic when a collision occurs, adding a second paddle if it doesn't already exist.
     *
     * @param thisObj  The GameObject with this collision strategy.
     * @param otherObj The other GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {

        // Check if brick already died or the second paddle already exists
        basicCollisionStrategy.onCollision(thisObj, otherObj);

        if (SecondPaddle.isActive()){
            return;
        }

        // Add a second paddle to the game
        SecondPaddle.activate();
        Paddle secondPaddle = new SecondPaddle(
                new Vector2(windowDimensions.x() / 2 - paddleSize.x() / 2, windowDimensions.y() / 2),
                paddleSize,
                paddleImage,
                inputListener,
                windowDimensions.x(),
                gameObjects);
        gameObjects.addGameObject(secondPaddle);
    }

}
