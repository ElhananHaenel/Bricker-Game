package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;
import bricker.utils.CameraManager;

/**
 * The CameraOnBallStrategy class implements the CollisionStrategy interface
 * and defines the strategy for handling collisions that turn on the camera
 * when the ball collides with a specific object in the Brick Breaker game.
 */
class CameraOnBallStrategy implements CollisionStrategy {

    private final CameraManager cameraManager;       // Manages the camera state.
    private final BasicCollisionStrategy basicCollisionStrategy;
    private final String tagBall;

    /**
     * Constructs a new CameraOnBallStrategy instance.
     *
     * @param gameObjects    Collection of GameObjects in the game.
     * @param bricksCounter  Counter for tracking the number of bricks.
     * @param cameraManager  Manages the camera state.
     * @param tagBall           The tag of objects type ball
     */
    protected CameraOnBallStrategy(
            GameObjectCollection gameObjects,
            Counter bricksCounter,
            CameraManager cameraManager,String tagBall) {
        this.tagBall = tagBall;
        this.basicCollisionStrategy = new BasicCollisionStrategy(gameObjects, bricksCounter);
        this.cameraManager = cameraManager;
    }

    /**
     * Handles the logic when a collision occurs, activating the camera if it is not already on.
     *
     * @param thisObj  The GameObject with this collision strategy.
     * @param otherObj The other GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {

        basicCollisionStrategy.onCollision(thisObj, otherObj);

        // Activate the camera if it is not already on
        if (otherObj.getTag().equals(tagBall)&& !cameraManager.isCameraOn()) {
            cameraManager.turnCameraOn();
        }
    }
}
