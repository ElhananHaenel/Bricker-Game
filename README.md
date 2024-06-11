# Bricker Game

## Overview
Bricker is a classic brick breaker game implemented in Java. Players control a paddle to bounce a ball and break bricks. Features include multiple levels, extra lives through falling hearts, and special power-ups.

## Installation
To set up the Bricker game, follow these steps:

1. **Clone the Repository:**
    ```sh
    git clone https://github.com/yourusername/bricker.git
    ```

2. **Navigate to the Project Directory:**
    ```sh
    cd bricker
    ```

3. **Install DanoGameLab:**
    ```sh
    git clone https://github.com/DanoGameLab/DanoGameLab.git
    ```

4. **Compile the Java Files:**
    ```sh
    javac -cp DanoGameLab/src:src *.java
    ```

5. **Run the Game:**
    ```sh
    java -cp DanoGameLab/src:src BrickerGameManager
    ```

## Usage
After starting the game, use the arrow keys to control the paddle. Bounce the ball to break all the bricks. Avoid letting the ball fall off the screen, or you will lose a life.

## Command Line Arguments
You can customize the number of bricks and rows by providing optional command line arguments:

- **First Argument:** Number of bricks per row.
- **Second Argument:** Number of rows of bricks.

Example:
```sh
java -cp DanoGameLab/src:src BrickerGameManager 8 7
```

## Dependencies
Bricker relies on the following Java libraries:

- **DanoGameLab:** Manages game objects, collisions, and graphics. [DanoGameLab GitHub](https://github.com/DanoGameLab/DanoGameLab)
