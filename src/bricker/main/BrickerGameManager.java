package bricker.main;

import bricker.brick_strategies.BrickStrategyFactory;
import bricker.gameobjects.Brick;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Paddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.utils.CameraManager;
import bricker.utils.LivesManager;

import java.awt.event.KeyEvent;





/**
 * The main game manager class for the "Bricker" game.
 */
public class BrickerGameManager extends GameManager {

    private static final String HEADLINE = "Bricker";
    private static final String TAG_BALL="Ball";
    private static final String TAG_MAIN_PADDLE="MainPaddle";
    private static final float WALLS_THICK = 5f;
    private static final int NUMBER_OF_WALLS_SIDES = 2;
    private static final int ONE_LESS = 1;
    private static final float BRICK_HIGHT = 15f;
    private static final String LOSE_MSG = "You Lose! ";
    private static final String PLAY_AGAIN_MSG = "Play again?";
    private static final String WIN_MSG = "You Win! ";
    private static final int MAX_LIVES_AUTHORIZED = 4;
    private static final Vector2 PADDLE_SIZE = new Vector2(100, 15);
    private static final float HALF = 0.5f;
    private static final int HEIGHT_OF_PADDLE=30;
    private static final Vector2 BALL_SIZE = new Vector2(20, 20);
    private static final Vector2 GAME_DIMENSIONS = new Vector2(700, 500);
    private static final float BRICK_DISTANCE_FROM_WALLS = 20f;
    private static final float BRICK_TO_BRICK_DISTANCE = 5f;
    private Counter bricksCounter;
    private static final int INITIAL_LIVES = 3;
    private static final String HEART_IMG_PATH = "assets/heart.png";
    private static final String BRICK_IMAGE_PATH = "assets/brick.png";
    private static final String BACKGROUND_IMAGE_PATH = "assets/DARK_BG2_small.jpeg";
    private static final String BALL_IMAGE_PATH = "assets/ball.png";
    private static final String BALL_COLLISION_SOUND_PATH = "assets/blop_cut_silenced.wav";
    private static final String PADDLE_IMAGE_PATH = "assets/paddle.png";
    private  static final String SECOND_PADDLE_IMAGE_PATH = "assets/paddle.png";
    private static int bricksRowsNum = 7;
    private static int bricksColsNum = 8;
    private Vector2 windowDimentions;
    private WindowController windowController;
    private Ball ball;
    private UserInputListener inputListener;
    private Sound collisionSound;
    private CameraManager cameraManager;
    private LivesManager livesManager;
    /**
     * the size of a heart object in the game.
     */
    public static final float HEART_SIZE = 15f;

    /**
     * Constructs the BrickerGameManager.
     *
     * @param windowTitle      The title of the game window.
     * @param windowDimensions The dimensions of the game window.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    /**
     * Initializes the game, setting up necessary components.
     *
     * @param imageReader      The image reader for loading game images.
     * @param soundReader      The sound reader for loading game sounds.
     * @param inputListener    The input listener for handling user input.
     * @param windowController The window controller for managing the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        this.inputListener = inputListener;

        // Creating the Ball
        createBall(imageReader, windowController, soundReader);

        // Creating the camera
        cameraManager = new CameraManager(this, ball, windowDimentions);

        // Creating the paddle
        createPaddle(imageReader, inputListener);

        // Creating the walls
        createWalls();

        // Setting the background
        setBackground(imageReader);

        // Creating the lives manager
        livesManager = new LivesManager(
                INITIAL_LIVES,
                MAX_LIVES_AUTHORIZED,
                windowController.getWindowDimensions(),
                gameObjects(),
                imageReader.readImage(HEART_IMG_PATH, true));

        // Creating the bricks
        createBricks(imageReader);
    }

    /**
     * The main method to start the Bricker game.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        if (args.length==2) {
            bricksRowsNum = Integer.parseInt(args[0]);
            bricksColsNum = Integer.parseInt(args[1]);
        }

        new BrickerGameManager(HEADLINE, GAME_DIMENSIONS).run();
    }

    /**
     * Creates the walls around the game area.
     */
    private void createWalls() {
        GameObject upperWall =
                new GameObject(Vector2.ZERO, new Vector2(windowDimentions.x(), WALLS_THICK), null);
        GameObject leftWall =
                new GameObject(Vector2.ZERO, new Vector2(WALLS_THICK, windowDimentions.y()), null);
        GameObject rightWall = new GameObject(new Vector2(windowDimentions.x() - WALLS_THICK, 0),
                new Vector2(WALLS_THICK, windowDimentions.y()),
                null);
        this.gameObjects().addGameObject(upperWall);
        this.gameObjects().addGameObject(leftWall);
        this.gameObjects().addGameObject(rightWall);
    }

    /**
     * Sets the background image for the game.
     *
     * @param imageReader The image reader for loading images.
     */
    private void setBackground(ImageReader imageReader) {
        GameObject background = new GameObject(
                Vector2.ZERO,
                windowDimentions,
                imageReader.readImage(BrickerGameManager.BACKGROUND_IMAGE_PATH, false));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * Creates the paddle for the game.
     *
     * @param imageReader    The image reader for loading images.
     * @param inputListener  The input listener for handling user input.
     */
    private void createPaddle(ImageReader imageReader, UserInputListener inputListener) {
        Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE_PATH, true);
        GameObject paddle =
                new Paddle(Vector2.ZERO, PADDLE_SIZE, paddleImage, inputListener, windowDimentions.x());
        paddle.setCenter(new Vector2(windowDimentions.x() / 2, windowDimentions.y()-HEIGHT_OF_PADDLE));
        this.gameObjects().addGameObject(paddle);
        paddle.setTag(TAG_MAIN_PADDLE);
    }

    /**
     * Creates the main Ball for the game.
     *
     * @param imageReader    The image reader for loading images.
     * @param windowController The window controller for managing the game window.
     * @param soundReader    The sound reader for loading sounds.
     */
    private void createBall(ImageReader imageReader,
                            WindowController windowController,
                            SoundReader soundReader) {
        Renderable ballImage =
                imageReader.readImage(BALL_IMAGE_PATH, true);

        windowDimentions = windowController.getWindowDimensions();
        collisionSound = soundReader.readSound(BALL_COLLISION_SOUND_PATH);
        ball = new Ball(windowDimentions.mult(HALF),
                BALL_SIZE,
                ballImage,
                collisionSound,
                windowDimentions,
                gameObjects());
        ball.setRandomVelocity();
        this.gameObjects().addGameObject(ball);
        ball.setTag(TAG_BALL);
    }

    /**
     * Creates the bricks for the game.
     *
     * @param imageReader The image reader for loading images.
     */
    private void createBricks(ImageReader imageReader) {
        bricksCounter = new Counter(bricksColsNum * bricksRowsNum);
        BrickStrategyFactory brickStrategyFactory = new BrickStrategyFactory(gameObjects(),
                bricksCounter,
                imageReader,
                collisionSound,
                windowController,
                inputListener,
                cameraManager,
                livesManager,
                PADDLE_SIZE,
                imageReader.readImage(SECOND_PADDLE_IMAGE_PATH, true),
                imageReader.readImage(HEART_IMG_PATH, true),
                BALL_SIZE,
                TAG_BALL
                ,TAG_MAIN_PADDLE
        );
        Renderable brickImage = imageReader.readImage(BRICK_IMAGE_PATH, false);
        float brickWidth = (windowDimentions.x() -
                (((WALLS_THICK + BRICK_DISTANCE_FROM_WALLS) * NUMBER_OF_WALLS_SIDES) +
                        (BRICK_TO_BRICK_DISTANCE * (bricksColsNum - ONE_LESS)))) / bricksColsNum;
        for (int i = 0; i < bricksRowsNum; i++) {
            for (int j = 0; j < bricksColsNum; j++) {
                Brick brick = new Brick(
                        new Vector2(WALLS_THICK + BRICK_DISTANCE_FROM_WALLS +
                                j * (brickWidth + BRICK_TO_BRICK_DISTANCE),
                                WALLS_THICK + BRICK_DISTANCE_FROM_WALLS +
                                        i * (BRICK_HIGHT + BRICK_TO_BRICK_DISTANCE)),
                        new Vector2(brickWidth, BRICK_HIGHT),
                        brickImage,
                        brickStrategyFactory.getStrategy());
                this.gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }
    }

    /**
     * Updates the game state.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        livesManager.manageLives();
        cameraManager.manageCamera();
        float ballHeight = ball.getCenter().y();
        String prompt = "";
        if (ballHeight > windowDimentions.y()) {
            livesManager.decrementLife();
            if (!livesManager.isLosing()) {
                gameObjects().addGameObject(ball);
                ball.setCenter(windowDimentions.mult(HALF));
                ball.setRandomVelocity();
                return;
            }
            prompt = LOSE_MSG;
        }
        if (bricksCounter.value() <= 0 || inputListener.isKeyPressed(KeyEvent.VK_W)) {
            prompt = WIN_MSG;
        }
        if (!prompt.isEmpty()) {
            prompt += PLAY_AGAIN_MSG;
            if (windowController.openYesNoDialog(prompt)) {
                livesManager.restartLives();
                windowController.resetGame();
            } else {
                windowController.closeWindow();
            }
        }
    }
}
