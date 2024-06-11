package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.gameobjects.Heart;
import bricker.utils.LivesManager;

/**
 * The AddLifeStrategy class implements the CollisionStrategy interface
 * and defines the strategy for handling collisions that drop a heart,
 * which can add one life when collected by the player's paddle in the Brick Breaker game.
 */
public class AddLifeStrategy implements CollisionStrategy {

    private final Vector2 HEART_VELOCITY = new Vector2(0, 100);  // Velocity of the dropped heart.
    private final GameObjectCollection gameObjects;
    private final Renderable heartImage;             // Renderable representing the heart image.
    private final float heartSize;                   // Size of the dropped heart.
    private final Vector2 windowDimensions;          // Dimensions of the game window.
    private final LivesManager livesManager;         // Manages the player's lives.
    private final BasicCollisionStrategy basicCollisionStrategy;
    private final String mainPaddleTag;

    /**
     * Constructs a new AddLifeStrategy instance.
     *
     * @param gameObjects       Collection of GameObjects in the game.
     * @param bricksCounter     Counter for tracking the number of bricks.
     * @param heartImage        Renderable representing the heart image.
     * @param heartSize         Size of the dropped heart.
     * @param windowDimensions  Dimensions of the game window.
     * @param livesManager      Manages the player's lives.
     * @param mainPaddleTag      Tag of mainPaddle object type.
     */
    AddLifeStrategy(
            GameObjectCollection gameObjects,
            Counter bricksCounter,
            Renderable heartImage,
            float heartSize,
            Vector2 windowDimensions,
            LivesManager livesManager, String mainPaddleTag) {
        this.mainPaddleTag = mainPaddleTag;
        basicCollisionStrategy = new BasicCollisionStrategy(gameObjects, bricksCounter);
        this.gameObjects = gameObjects;
        this.heartImage = heartImage;
        this.heartSize = heartSize;
        this.windowDimensions = windowDimensions;
        this.livesManager = livesManager;
    }

    /**
     * Handles the logic when a collision occurs, dropping a heart that can be collected by the
     * player's paddle.
     *
     * @param thisObj  The GameObject with this collision strategy.
     * @param otherObj The other GameObject involved in the collision.
     */
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        basicCollisionStrategy.onCollision(thisObj, otherObj);

        // Drop a heart GameObject at the center of the destroyed brick
        Heart heart = new Heart(
                new Vector2(thisObj.getCenter().x() - heartSize / 2,
                        thisObj.getCenter().y() - heartSize / 2),
                new Vector2(heartSize, heartSize),
                heartImage,
                gameObjects,
                windowDimensions,
                livesManager,mainPaddleTag);
        heart.setVelocity(HEART_VELOCITY);
        gameObjects.addGameObject(heart);
    }
}
