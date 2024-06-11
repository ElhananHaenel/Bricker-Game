package bricker.brick_strategies;

import danogl.GameObject;

/**
 * The abstract class representing a collision strategy for game objects.
 * Each subclass should implement the specific behavior to be executed when a collision occurs.
 */
public interface CollisionStrategy {

    /**
     * Handles the logic when a collision occurs between two game objects.
     *
     * @param thisObj  The game object associated with this collision strategy.
     * @param otherObj The other game object involved in the collision.
     */
     void onCollision(GameObject thisObj, GameObject otherObj);
}
