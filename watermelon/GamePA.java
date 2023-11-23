package watermelon;

import processing.core.PApplet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GamePA extends PApplet {
    ArrayList<Sphere> spheres;
    Queue<SphereStep> nextSizes;
    Sphere followingSphere;
    static int wallThickness = 20;
    boolean gameStarted = false;
    int currentPlayer = 1; // Player 1 starts
    int player1Score = 0;
    int player2Score = 0;
    boolean canClick = true;

    public void settings() {
        pixelDensity(1);
        size(600, 750);
    }

    public void setup() {
        initializeGame();
    }

    public void draw() {
        background(255);

        if (!gameStarted) {
            fill(0);
            textAlign(CENTER, CENTER);
            textSize(32);
            text("Click to start the game!", width / 2, height / 2);
        } else {
            updateFollowingSphere();
            updateAndDisplaySpheres();
            drawBoundaries();
            displayFollowingSphere();

            // Display the turn timer at the top-right corner
            fill(0);
            textSize(20);
            textAlign(RIGHT, TOP);
            text("Current Player: " + currentPlayer, width - 20, 20);
        }
    }

    public void mousePressed() {
        if (!gameStarted) {
            startGame();
        } else {
            handlePlayerTurn();
        }
    }

    private void startGame() {
        gameStarted = true;
        currentPlayer = 1; // Reset to player 1
        initializeGame();
    }

    private void handlePlayerTurn() {
        SphereStep nextSize = getNextSize();
        updateFollowingSphereSize(nextSize);

        // Determine ownership based on the current player
        int outlineColor = (currentPlayer == 1) ? color(0, 0, 255) : color(0, 255, 0);

        createNewSphere(nextSize, outlineColor);

        // Switch currentPlayer
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    private void initializeGame() {
        spheres = new ArrayList<>();
        nextSizes = new LinkedList<>();

        for (int i = 0; i < 10; i++) {
            nextSizes.add(SphereStep.values()[(int) (Math.random() * 3)]);
        }

        followingSphere = new Sphere(this, this, width / 2f, wallThickness, SphereStep.STEP_1, color(0, 0, 255), 2, currentPlayer);
    }

    private void updateFollowingSphere() {
        followingSphere.x = mouseX;
        followingSphere.y = wallThickness;

        if (!nextSizes.isEmpty()) {
            SphereStep firstSize = nextSizes.peek();
            followingSphere.diameter = firstSize.getSize();
            followingSphere.step = firstSize.getStep();
        }
    }

    private void updateAndDisplaySpheres() {
        for (int i = spheres.size() - 1; i >= 0; i--) {
            Sphere s = spheres.get(i);
            s.display();
            s.update();
            s.checkCollision(spheres);
            s.checkBoundary();
            if (s.isMerged) {
                spheres.remove(i);
            }
        }
    }

    private void drawBoundaries() {
        fill(150);
        stroke(100);
        strokeWeight(1);

        rect(0, height - wallThickness, width, wallThickness);
        rect(0, 0, wallThickness, height);
        rect(width - wallThickness, 0, wallThickness, height);
    }

    private void displayFollowingSphere() {
        followingSphere.display();
    }

    private SphereStep getNextSize() {
        return nextSizes.isEmpty() ? SphereStep.STEP_1 : nextSizes.poll();
    }

    private void updateFollowingSphereSize(SphereStep nextSize) {
        followingSphere.diameter = nextSize.getSize();
        followingSphere.step = nextSize.getStep();
    }

    private void createNewSphere(SphereStep nextSize, int outlineColor) {
        Sphere newSphere = new Sphere(this, this, mouseX, wallThickness, nextSize, outlineColor, 2, currentPlayer);
        newSphere.checkCollision(spheres);
        spheres.add(newSphere);
    }

    private void setClickPermission(boolean canClick) {
        this.canClick = canClick;
    }
}
