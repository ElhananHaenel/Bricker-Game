package bricker.utils;

import danogl.GameManager;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import bricker.gameobjects.Ball;

/**
 * The `CameraManager` class manages the camera behavior,
 * centering the view on the main mainBall during gameplay.
 *
 * Features:
 * - Activates the camera to focus on the mainBall when triggered.
 * - Automatically turns off the camera after a specified number of mainBall hits.
 *
 * Usage:
 * - Create an instance by providing the `GameManager`, the `MainBall` object, and window dimensions.
 * - Use `turnCameraOn` to activate the camera, and it will follow the mainBall until the hit
 * limit is reached.
 * - Call `manageCamera` regularly to update the camera state and turn it off when needed.
 *
 * Example:
 * ```java
 * CameraManager cameraManager = new CameraManager(gameManager, mainBall, windowDimensions);
 * cameraManager.turnCameraOn();
 * // ...
 * cameraManager.manageCamera();
 * ```
 */
public class CameraManager {
    private static final int HITS_UNTIL_STOP_CAMERA = 4;
    private static final float CAMERA_DIM_FACTOR = 1.2f;
    private boolean isCameraOn;
    private final GameManager gameManager;
    private final Ball mainBall;
    private final Vector2 windowDimension;
    private int hitsToReach;

    /**
     * Constructs a `CameraManager` instance.
     *
     * @param gameManager      The game manager.
     * @param mainBall             The main mainBall to be tracked by the camera.
     * @param windowDimension  The dimensions of the game window.
     */
    public CameraManager(GameManager gameManager, Ball mainBall, Vector2 windowDimension){
        this.gameManager = gameManager;
        this.mainBall = mainBall;
        this.windowDimension = windowDimension;
    }

    /**
     * Activates the camera, focusing on the mainBall.
     */
    public void turnCameraOn(){
        isCameraOn = true;
        hitsToReach = mainBall.getCollisionCounter() + HITS_UNTIL_STOP_CAMERA;
        gameManager.setCamera(
                new Camera(
                        mainBall,
                        Vector2.ZERO,
                        windowDimension.mult(CAMERA_DIM_FACTOR),
                        windowDimension
                )
        );
    }

    /**
     * Manages the camera state, turning it off when the hit limit is reached.
     */
    public void manageCamera(){
        if (isCameraOn && mainBall.getCollisionCounter() > hitsToReach){
            isCameraOn = false;
            gameManager.setCamera(null);
        }
    }

    /**
     * Checks if the camera is currently active.
     *
     * @return True if the camera is active, false otherwise.
     */
    public boolean isCameraOn() {
        return isCameraOn;
    }
}
