package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.utils.CameraManager;
import bricker.utils.LivesManager;

import java.util.Random;

/**
 * The BrickStrategyFactory class is responsible for creating instances of collision strategies
 * for bricks in the Brick Breaker game. It randomly selects a strategy from a predefined set of
 * strategies, including adding life, adding a paddle, turning on the camera, adding more balls,
 * removing a brick, and applying double behavior.
 */
public class BrickStrategyFactory {

    private static final int MAX_STRATEGY = 3;
    private static final int TWO_STRATEGIES = 2;
    private boolean canAddDoubleStrategy = false;
    private static final int RANDOM_OPTION_LENGTH=10;
    private static final int SPECIAL_STRATEGY_LENGTH=5;
    private static final int EXCLUDE_DOUBLE_BEHAVIOR=1;
    private static final int GET_THE_ODD_STRATEGY=4;
    private static final int GET_THE_NORMAL_STRATEGY=4;
    private int counter=0;
    private static boolean flagDoubleStrategy=false;

    /**
     * Enumerates the possible collision strategies for bricks.
     */
    private enum Strategies {

        REMOVE_BRICK,  // Pay attention! RemoveBrick has to be the last one
        ADD_LIFE,
        ADD_PADDLE,
        CAMERA_ON,
        MORE_BALLS,
        DOUBLE_BEHAVIOR; // Pay attention! DOUBLE_BEHAVIOR has to be the last one

        public static final Strategies[] strategies = values();

        private static final Random random = new Random();

        /**
         * Gets a random strategy from the available strategies.
         *
         * @param includeDouble Indicates whether to include the DOUBLE_BEHAVIOR strategy
         *                      as a possible return value.
         * @param excludeBasicStrategy Indicates whether you include Basic behavior strategy
         *                             as a possible return value.
         * @return A randomly selected strategy.
         */
        public static Strategies getRandomStrategy(boolean includeDouble,boolean
                excludeBasicStrategy) {
            int option = RANDOM_OPTION_LENGTH;
            if (excludeBasicStrategy){
                option = SPECIAL_STRATEGY_LENGTH;
            }
            if (!includeDouble) {
                option -= EXCLUDE_DOUBLE_BEHAVIOR;
            }
            int randResult=random.nextInt(option);
            if  (excludeBasicStrategy){
                return strategies[1+randResult];
            }
            if (randResult<=GET_THE_NORMAL_STRATEGY) {
                return strategies[0];
            }

            return strategies[randResult-GET_THE_ODD_STRATEGY];
        }
    }

    private final GameObjectCollection objectCollection;
    private final Counter bricksCounter;
    private final ImageReader imageReader;
    private final Sound onCollisionSound;
    private final WindowController windowController;
    private final UserInputListener inputListener;
    private final CameraManager cameraManager;
    private final LivesManager livesManager;
    private final Vector2 paddleSize;
    private final Renderable secondPaddleImg;
    private final Renderable heartImg;
    private final Vector2 ballSize;
    private String tagBall;
    private final String mainPaddleTag;
//    private boolean isInner = false;

    /**
     * Constructs a new BrickStrategyFactory instance.
     *
     * @param objectCollection  Collection of GameObjects in the game.
     * @param bricksCounter     Counter for tracking the number of bricks.
     * @param imageReader       ImageReader for reading images.
     * @param onCollisionSound  Sound for ball collisions.
     * @param windowController  WindowController for managing the game window.
     * @param inputListener     UserInputListener for handling user input.
     * @param cameraManager     CameraManager for managing the camera.
     * @param livesManager      LivesManager for managing player lives.
     * @param tagBall           The tag of objects type ball
     * @param mainPaddleTag      Tag of mainPaddle object type.
     */
    public BrickStrategyFactory(GameObjectCollection objectCollection,
                                Counter bricksCounter,
                                ImageReader imageReader,
                                Sound onCollisionSound,
                                WindowController windowController,
                                UserInputListener inputListener,
                                CameraManager cameraManager,
                                LivesManager livesManager,
                                Vector2 paddleSize,
                                Renderable secondPaddleImg,
                                Renderable heartImg,Vector2 ballSize, String tagBall,
                                String mainPaddleTag) {
        this.objectCollection = objectCollection;
        this.bricksCounter = bricksCounter;
        this.imageReader = imageReader;
        this.onCollisionSound = onCollisionSound;
        this.windowController = windowController;
        this.inputListener = inputListener;
        this.cameraManager = cameraManager;
        this.livesManager = livesManager;
        this.paddleSize = paddleSize;
        this.secondPaddleImg = secondPaddleImg;
        this.heartImg = heartImg;
        this.ballSize = ballSize;
        this.tagBall=tagBall;
        this.mainPaddleTag = mainPaddleTag;
    }

    /**
     * Gets a randomly selected collision strategy for a brick.
     *
     * @return A CollisionStrategy instance representing the selected strategy.
     */
    public CollisionStrategy getStrategy() {
        Strategies strategyEnum = Strategies.getRandomStrategy(true,
                false);
        CollisionStrategy help=getStrategyByEnum(strategyEnum);
        counter=0;
        flagDoubleStrategy=false;
        return help;
    }

    private CollisionStrategy getStrategyByEnum(Strategies strategyEnum) {
        counter+=1;
        switch (strategyEnum) {
            case ADD_LIFE:
                return new AddLifeStrategy(
                        objectCollection,
                        bricksCounter,
                        heartImg,
                        BrickerGameManager.HEART_SIZE,
                        windowController.getWindowDimensions(),
                        livesManager,
                        mainPaddleTag
                );
            case ADD_PADDLE:
                return new AddPaddleStrategy(
                        objectCollection,
                        bricksCounter,
                        windowController.getWindowDimensions(),
                        secondPaddleImg,
                        inputListener,
                        paddleSize
                );
            case CAMERA_ON:
                return new CameraOnBallStrategy(objectCollection, bricksCounter, cameraManager
                        ,tagBall);
            case MORE_BALLS:
                return new MoreBallsStrategy(
                        objectCollection,
                        bricksCounter,
                        imageReader,
                        onCollisionSound,
                        windowController.getWindowDimensions(), ballSize
                );
            case REMOVE_BRICK:
                return new BasicCollisionStrategy(objectCollection, bricksCounter);
            case DOUBLE_BEHAVIOR:
                counter -= 1;
                return getDoubleStrategyFirstTime();
            default:
                counter -= 1;
                return null;
        }
    }

    private DoubleBehaviorStrategy getDoubleStrategyFirstTime() {
        flagDoubleStrategy=true;
        canAddDoubleStrategy=counter+TWO_STRATEGIES <MAX_STRATEGY;
        Strategies firstStrategyEnum = Strategies.getRandomStrategy(canAddDoubleStrategy,
                true);

        counter += 1;
        CollisionStrategy firstStrategy = getStrategyByEnum(firstStrategyEnum);
        counter -= 1;
        canAddDoubleStrategy=counter+TWO_STRATEGIES <=MAX_STRATEGY;
        Strategies secondStrategyEnum = Strategies.getRandomStrategy(canAddDoubleStrategy
                ,true);

        CollisionStrategy secondStrategy = getStrategyByEnum(secondStrategyEnum);
        canAddDoubleStrategy = true;
        return new DoubleBehaviorStrategy(
                firstStrategy,
                secondStrategy,
                bricksCounter,
                objectCollection
        );
    }
}


