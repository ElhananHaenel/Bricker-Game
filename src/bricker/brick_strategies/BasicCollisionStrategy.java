package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * The BasicCollisionStrategy class implements the CollisionStrategy interface
 * and defines the strategy for handling collisions that remove a brick
 * from the game in the Brick Breaker game.
 */
class BasicCollisionStrategy implements CollisionStrategy {

    private final GameObjectCollection gameObjects;  // Collection of GameObjects in the game.
    private final Counter bricksCounter;             // Counter for tracking the number of bricks.

    /**
     * Constructs a new BasicCollisionStrategy instance.
     *
     * @param gameObjects   Collection of GameObjects in the game.
     * @param bricksCounter Counter for tracking the number of bricks.
     */
    BasicCollisionStrategy(GameObjectCollection gameObjects, Counter bricksCounter) {
        super();
        this.gameObjects = gameObjects;
        this.bricksCounter = bricksCounter;
    }

    /**
     * Handles the logic and GUI when a collision occurs, removing a brick from the game.
     *
     * @param thisObj  The GameObject with this collision strategy.
     * @param otherObj The other GameObject involved in the collision.
     */
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (thisObj.getTag().equals(Brick.ACTIVE_TAG)) {
            removeBrick(thisObj);
        }
    }

    /**
     * Removes the brick from the GameObject collection and decrements the brick counter.
     *
     * @param thisObj The GameObject representing the brick to be removed.
     */
    private void removeBrick(GameObject thisObj) {
        gameObjects.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        bricksCounter.decrement();
        thisObj.setTag(Brick.INACTIVE_TAG);
    }
}
