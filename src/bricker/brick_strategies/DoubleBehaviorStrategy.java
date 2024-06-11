package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * The DoubleBehaviorStrategy class represents a collision strategy that combines two strategies
 * and includes a basic collision strategy, it implements CollisionStrategy.
 * It applies both strategies and the basic strategy when a collision occurs.
 */
class DoubleBehaviorStrategy implements CollisionStrategy {

    private final CollisionStrategy strategy1;                // First collision strategy
    private final CollisionStrategy strategy2;                // Second collision strategy
    private final BasicCollisionStrategy basicCollisionStrategy;  // Basic collision strategy

    /**
     * Constructs a new DoubleBehaviorStrategy instance.
     *
     * @param strategy1        The first collision strategy to be combined.
     * @param strategy2        The second collision strategy to be combined.
     * @param bricksCounter    Counter for tracking the number of bricks.
     * @param gameObjects      Collection of GameObjects in the game.
     */
    protected DoubleBehaviorStrategy(
            CollisionStrategy strategy1,
            CollisionStrategy strategy2,
            Counter bricksCounter,
            GameObjectCollection gameObjects
    ) {
        basicCollisionStrategy = new BasicCollisionStrategy(gameObjects, bricksCounter);
        this.strategy1 = strategy1;
        this.strategy2 = strategy2;
    }

    /**
     * Handles the logic when a collision occurs between two game objects.
     * Applies both strategies and the basic strategy.
     *
     * @param thisObj  The game object associated with this collision strategy.
     * @param otherObj The other game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        basicCollisionStrategy.onCollision(thisObj, otherObj);
        strategy1.onCollision(thisObj, otherObj);
        strategy2.onCollision(thisObj, otherObj);
    }
}
