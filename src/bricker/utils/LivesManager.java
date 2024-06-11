package bricker.utils;

import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.gameobjects.GraphicLifeCounter;
import bricker.gameobjects.NumericLifeCounter;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The LivesManager class manages the player's lives in the game. It includes functionality
 * to decrement, increment, and restart lives. It also manages both numeric and graphic representations
 * of lives, providing visual feedback to the player.
 */
public class LivesManager {

    private static final int NOT_GREEN_LIMIT = 3;
    // Constants for heart colors based on the number of lives
    private final Color ONE_LIFE_COLOR = Color.RED;
    private final Color TWO_LIVES_COLOR = Color.YELLOW;
    private final Color THREE_OR_MORE_LIVES_COLOR = Color.GREEN;

    // Mapping of lives to corresponding heart colors
    private final Map<Integer, Color> LIVES_TO_COLOR = new HashMap<>() {{
        put(1, ONE_LIFE_COLOR);
        put(2, TWO_LIVES_COLOR);
        put(3, THREE_OR_MORE_LIVES_COLOR);
    }};

    // Constants for heart layout and initial lives
    private static final float HEART_DISTANCE_FROM_WINDOW_LEFT = 10f;
    private static final float HEART_SIZE = 15f;
    private static final float HEART_TO_HEART_DISTANCE = 25f;
    private static final float HEART_DISTANCE_FROM_WINDOW_BOTTOM = 20f;
    private static final int INITIAL_LIVES = 3;
    private static final int MAX_LIVES_AUTHORIZED = 4;

    // Counter for managing lives
    private final Counter livesCounter;

    // Maximum allowed lives
    private final int maxLives;

    // Window dimensions for layout calculations
    private final Vector2 windowDimensions;

    // GameObjectCollection for managing game objects
    private final GameObjectCollection gameObjects;

    // Initial number of lives
    private final int initialLivesNum;

    // Numeric representation of lives
    private NumericLifeCounter numericLifeCounter;

    // Current number of lives
    private int curLives;

    // Renderable for heart image
    private final Renderable heartImage;

    // Array for graphic representation of lives
    private GraphicLifeCounter[] graphicLifeCounters;

    /**
     * Constructs a LivesManager with the specified parameters.
     *
     * @param initialLivesNum   The initial number of lives.
     * @param maxLives          The maximum allowed lives.
     * @param windowDimensions  The dimensions of the game window.
     * @param gameObjects       The GameObjectCollection for managing game objects.
     * @param heartImage        The Renderable representing the heart image.
     */
    public LivesManager(int initialLivesNum,
                        int maxLives,
                        Vector2 windowDimensions,
                        GameObjectCollection gameObjects,
                        Renderable heartImage) {
        // Initialize counters and parameters
        livesCounter = new Counter(initialLivesNum);
        this.initialLivesNum = initialLivesNum;
        this.maxLives = maxLives;
        this.windowDimensions = windowDimensions;
        this.gameObjects = gameObjects;
        curLives = initialLivesNum;
        this.heartImage = heartImage;

        // Create initial representations of lives
        createNumericCounter(INITIAL_LIVES);
        createGraphicCounter();
    }

    /**
     * Checks if the player is losing the game (no remaining lives).
     *
     * @return True if the player is losing, false otherwise.
     */
    public boolean isLosing() {
        return livesCounter.value() <= 0;
    }

    /**
     * Decrements the player's life count.
     */
    public void decrementLife() {
        livesCounter.decrement();
    }

    /**
     * Increments the player's life count if it is below the maximum allowed.
     */
    public void incrementLife() {
        if (livesCounter.value() < maxLives) {
            livesCounter.increment();
        }
    }

    /**
     * Restarts the player's lives to the initial number.
     */
    public void restartLives() {
        livesCounter.reset();
        livesCounter.increaseBy(initialLivesNum);
    }

    /**
     * Manages changes in the player's lives, updating both numeric and graphic representations.
     */
    public void manageLives() {
        if (livesCounter.value() != curLives) {
            boolean addedLives = livesCounter.value() > curLives;
            curLives = livesCounter.value();
            gameObjects.removeGameObject(numericLifeCounter, Layer.BACKGROUND);
            createNumericCounter(livesCounter.value());
            gameObjects.addGameObject(numericLifeCounter, Layer.BACKGROUND);

            if (addedLives) {
                for (int i = 0; i < curLives; i++) {
                    gameObjects.addGameObject(graphicLifeCounters[i], Layer.BACKGROUND);
                }
            } else {
                gameObjects.removeGameObject(graphicLifeCounters[curLives], Layer.BACKGROUND);
            }
        }
    }

    /**
     * Creates the numeric representation of lives.
     *
     * @param lives The current number of lives.
     */
    private void createNumericCounter(int lives) {
        Vector2 location = new Vector2(
                HEART_DISTANCE_FROM_WINDOW_LEFT +
                        MAX_LIVES_AUTHORIZED * (HEART_SIZE + HEART_TO_HEART_DISTANCE) +
                        HEART_TO_HEART_DISTANCE,
                windowDimensions.y() - HEART_DISTANCE_FROM_WINDOW_BOTTOM);

        Vector2 size = new Vector2(HEART_SIZE, HEART_SIZE);
        TextRenderable counterRenderable = new TextRenderable(String.valueOf(lives));
        counterRenderable.setColor(livesCounter.value() <= NOT_GREEN_LIMIT ?
                LIVES_TO_COLOR.get(livesCounter.value()): Color.GREEN);
        numericLifeCounter = new NumericLifeCounter(location, size, counterRenderable);
        gameObjects.addGameObject(numericLifeCounter, Layer.BACKGROUND);
    }

    /**
     * Creates the graphic representation of lives.
     */
    private void createGraphicCounter() {
        graphicLifeCounters = new GraphicLifeCounter[MAX_LIVES_AUTHORIZED];

        for (int i = 0; i < MAX_LIVES_AUTHORIZED; i++) {
            graphicLifeCounters[i] = new GraphicLifeCounter(
                    new Vector2(
                            HEART_DISTANCE_FROM_WINDOW_LEFT + i * (HEART_TO_HEART_DISTANCE + HEART_SIZE),
                            windowDimensions.y() - HEART_DISTANCE_FROM_WINDOW_BOTTOM),
                    new Vector2(HEART_SIZE, HEART_SIZE),
                    heartImage);

            // Add the graphic life counter to the game objects if it's within the initial number of lives
            if (i < INITIAL_LIVES) {
                gameObjects.addGameObject(graphicLifeCounters[i], Layer.BACKGROUND);
            }
        }
    }
}

