# Flappy Bird (Java / Swing)

A classic Flappy Bird game implemented in Java using javax.swing for rendering and input handling.

Overview

The bird flies forward at a constant horizontal speed, falls under gravity, and a left mouse click makes it flap upward. The goal is to fly between the pipes without hitting them and rack up points.

Tech Stack
Java (Swing / AWT)
No external dependencies — standard library only
Project Structure
FlappyBird/
├── src/
│   ├── App.java            # entry point, creates the JFrame and starts the game
│   ├── FlappyBird.java     # core game logic (panel, rendering, physics, collisions)
│   ├── flappybird.png      # bird sprite
│   ├── flappybirdbg.png    # background
│   ├── toppipe.png         # top pipe
│   └── bottompipe.png      # bottom pipe
├── out/                    # compiled .class files
└── FlappyBird.iml          # IntelliJ IDEA project file
How to Run
Via IntelliJ IDEA
Open the FlappyBird folder as a project.
Run the App.java class.
Via terminal

From inside the src folder:

bash
javac App.java FlappyBird.java
java App

The .png images must sit next to the compiled .class files, since they're loaded via getClass().getResource(...).

Controls
Action	Input
Flap (fly upward)	Left mouse button
Start the game	Click on the window
Restart after game over	Right mouse button
Game Mechanics
Gravity: constantly pulls the bird down (gravity = 0.4); each click sets velocity_y = -6.
Pipes: spawn every 1.5 seconds (place_pipes_timer) with a randomized vertical gap.
Game loop: updates 60 times per second (game_loop, 1000/60 ms).
Score: +0.5 for each pipe passed (top and bottom pipes are counted separately, so +1 per pair).
Game over: triggered by colliding with a pipe or falling past the bottom of the screen.
