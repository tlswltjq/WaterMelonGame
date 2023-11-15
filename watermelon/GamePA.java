package watermelon;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class GamePA extends PApplet {
    ArrayList<Sphere> spheres;  // ArrayList to store spheres
    Queue<SphereStep> nextSizes;  // Queue to store next sizes
    Sphere followingSphere;  // Sphere that follows the mouse pointer
    static int wallThickness = 20;  // Thickness of the walls
    boolean gameStarted = false;  // Flag to check if the game has started

    public void settings() {
        pixelDensity(1); // DPI 값을 조절하여 화면이 더 크게 표시되도록 함
        size(600, 750);
    }

    public void setup() {
        spheres = new ArrayList<>();
        nextSizes = new LinkedList<>();

        // Initialize the queue with the next 10 sizes
        for (int i = 0; i < 10; i++) {
            nextSizes.add(SphereStep.values()[(int) (Math.random() * 3)]);
        }

        // Create a sphere to follow the mouse pointer
        followingSphere = new Sphere(this, width / 2f, wallThickness, SphereStep.STEP_1);
    }

    public void draw() {
        background(255);

        if (!gameStarted) {
            // Display "Click to start" message box
            fill(0);
            textSize(32);
            textAlign(CENTER, CENTER);
            text("Click to start", width / 2, height / 2);
        } else {
            // Update the position of the sphere that follows the mouse pointer
            followingSphere.x = mouseX;
            followingSphere.y = wallThickness;

            // Update the size and step of the followingSphere based on the first size in the queue
            if (!nextSizes.isEmpty()) {
                SphereStep firstSize = nextSizes.peek();
                followingSphere.diameter = firstSize.getSize();
                followingSphere.step = firstSize.getStep();
            }

            // Display and update all spheres
            for (int i = spheres.size() - 1; i >= 0; i--) {
                Sphere s = spheres.get(i);
                s.display();
                s.update();
                s.checkCollision(spheres);  // Check collision with other spheres
                s.checkBoundary();  // Check collision with boundaries
                if (s.isMerged) {
                    spheres.remove(i);  // Remove merged spheres
                }
            }

            // Draw the boundaries
            fill(200);  // Set fill color to gray for the walls
            rect(0, height - wallThickness, width, wallThickness);  // Bottom wall
            rect(0, 0, wallThickness, height);  // Left wall
            rect(width - wallThickness, 0, wallThickness, height);  // Right wall


            // Display and update the sphere that follows the mouse pointer
            followingSphere.display();
        }
    }

    public void mousePressed() {
        if (!gameStarted) {
            // Start the game on the first click
            gameStarted = true;
        } else {
            // Get the next size from the queue
            SphereStep nextSize;
            if (!nextSizes.isEmpty()) {
                nextSize = nextSizes.poll();
            } else {
                // Use a default size if the queue is empty
                nextSize = SphereStep.STEP_1;
            }

            // Update the size of the sphere that follows the mouse pointer
            followingSphere.diameter = nextSize.getSize();
            followingSphere.step = nextSize.getStep();

            // Create a new sphere at the top of the screen with the selected size
            Sphere newSphere = new Sphere(this, mouseX, wallThickness, nextSize);
            newSphere.checkCollision(spheres);  // Check collision with existing spheres
            spheres.add(newSphere);

            // Add a new size to the queue
            nextSizes.add(SphereStep.values()[(int) (Math.random() * 3)]);
            printQueueContents();
        }
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
