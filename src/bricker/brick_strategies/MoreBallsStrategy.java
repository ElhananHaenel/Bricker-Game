package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.gameobjects.BasicBall;

/**
 * The MoreBallsStrategy class implements the CollisionStrategy interface
 * and defines the strategy for handling collisions that add BALLS_TO_ADD_NUM balls
 * to the game when a specific object is removed in the Brick Breaker game.
 */
class MoreBallsStrategy implements CollisionStrategy {

    private static final float PACK_BALL_RATIO = 0.75f;
    private static final int BALLS_TO_ADD_NUM = 2;           // Number of balls to add.
    private final GameObjectCollection gameObjects;  // Collection of GameObjects in the game.
    private final ImageReader imageReader;           // ImageReader for reading images.
    private final Sound onCollisionSound;            // Sound for ball collisions.
    private final Vector2 windowDimensions;          // Dimensions of the game window.
    private final Vector2 ballSize;
    private final BasicCollisionStrategy basicCollisionStrategy;

    /**
     * Constructs a new MoreBallsStrategy instance.
     *
     * @param gameObjects       Collection of GameObjects in the game.
     * @param bricksCounter     Counter for tracking the number of bricks.
     * @param imageReader       ImageReader for reading images.
     * @param onCollisionSound  Sound for ball collisions.
     * @param windowDimensions  Dimensions of the game window.
     */
    protected MoreBallsStrategy(GameObjectCollection gameObjects,
                                Counter bricksCounter,
                                ImageReader imageReader,
                                Sound onCollisionSound,
                                Vector2 windowDimensions,
                                Vector2 ballSize) {
        basicCollisionStrategy = new BasicCollisionStrategy(gameObjects, bricksCounter);
        this.gameObjects = gameObjects;
        this.imageReader = imageReader;
        this.onCollisionSound = onCollisionSound;
        this.windowDimensions = windowDimensions;
        this.ballSize = ballSize;
    }

    /**
     * Handles the logic when a collision occurs, adding three balls to the game.
     *
     * @param thisObj  The GameObject with this collision strategy.
     * @param otherObj The other GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {

        basicCollisionStrategy.onCollision(thisObj, otherObj);

        // Add three balls to the game
        for (int i = 0; i < BALLS_TO_ADD_NUM; i++) {
            BasicBall ball = new BasicBall(
                    thisObj.getCenter(),
                    new Vector2(ballSize.x()* PACK_BALL_RATIO,ballSize.y()* PACK_BALL_RATIO),
                    imageReader.readImage("assets/mockBall.png", true),
                    windowDimensions,
                    onCollisionSound,
                    gameObjects);

            // Randomize the initial velocity of the added balls
            ball.setRandomVelocity();
            gameObjects.addGameObject(ball);
        }
    }
}
