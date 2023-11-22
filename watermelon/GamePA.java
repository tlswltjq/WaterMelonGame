package watermelon;

import processing.core.PApplet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class GamePA extends PApplet {
    ArrayList<Sphere> spheres;
    Queue<SphereStep> nextSizes;
    Sphere followingSphere;
    static int wallThickness = 20;
    boolean gameStarted = false;

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
            displayStartMessage();
        } else {
            updateFollowingSphere();
            updateAndDisplaySpheres();
            drawBoundaries();
            displayFollowingSphere();
        }
    }

    public void mousePressed() {
        if (!gameStarted) {
            startGame();
        } else {
            handleSphereClick();
        }
    }

    private void initializeGame() {
        spheres = new ArrayList<>();
        nextSizes = new LinkedList<>();

        for (int i = 0; i < 10; i++) {
            nextSizes.add(SphereStep.values()[(int) (Math.random() * 3)]);
        }

        followingSphere = new Sphere(this, width / 2f, wallThickness, SphereStep.STEP_1, color(0, 0, 255), 2);
    }

    private void displayStartMessage() {
        fill(0);
        textSize(32);
        textAlign(CENTER, CENTER);
        text("Click to start", width / 2, height / 2);
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

    private void startGame() {
        gameStarted = true;
    }

    private void handleSphereClick() {
        SphereStep nextSize = getNextSize();
        updateFollowingSphereSize(nextSize);
        createNewSphere(nextSize);
        addNewSizeToQueue();
        printQueueContents();
    }

    private SphereStep getNextSize() {
        return nextSizes.isEmpty() ? SphereStep.STEP_1 : nextSizes.poll();
    }

    private void updateFollowingSphereSize(SphereStep nextSize) {
        followingSphere.diameter = nextSize.getSize();
        followingSphere.step = nextSize.getStep();
    }

    private void createNewSphere(SphereStep nextSize) {
        Sphere newSphere = new Sphere(this, mouseX, wallThickness, nextSize, color(0, 255, 0), 2);
        newSphere.checkCollision(spheres);
        spheres.add(newSphere);
    }

    private void addNewSizeToQueue() {
        nextSizes.add(SphereStep.values()[(int) (Math.random() * 3)]);
    }

    private void printQueueContents() {
        System.out.print("Selected Queue Contents: ");
        int index = 0;
        for (Iterator<SphereStep> iterator = nextSizes.iterator(); iterator.hasNext(); ) {
            SphereStep step = iterator.next();
            if (index >= 1 && index <= 3) {
                System.out.print(step + " ");
            }
            index++;
        }
        System.out.println();
    }
}
