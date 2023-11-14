package watermelon.page;

import processing.core.PApplet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GamePA extends PApplet {

    ArrayList<Sphere> spheres;
    static int wallThickness = 20;
    Queue<SphereStep> nextSizes;
    Sphere followingSphere;
    boolean gameStarted = false;

    public void settings() {
        pixelDensity(1);
        size(600, 750);
    }

    public void setup() {
        spheres = new ArrayList<>();
        nextSizes = new LinkedList<>();

        for (int i = 0; i < 10; i++) {
            nextSizes.add(SphereStep.values()[(int) (Math.random() * 3)]);
        }

        followingSphere = new Sphere(this, width / 2f, wallThickness, SphereStep.STEP_1);
    }

    public void draw() {
        background(255);

        if (!gameStarted) {
            fill(0);
            textSize(32);
            textAlign(CENTER, CENTER);
            text("Click to start", width / 2, height / 2);
        } else {
            followingSphere.x = mouseX;
            followingSphere.y = wallThickness;

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

            fill(200);
            rect(0, height - wallThickness, width, wallThickness);
            rect(0, 0, wallThickness, height);
            rect(width - wallThickness, 0, wallThickness, height);

            followingSphere.display();

            fill(0);
            textSize(16);
            textAlign(RIGHT, TOP);
            int index = 0;
            for (SphereStep step : nextSizes) {
                text("Next Size " + (index + 1) + ": " + step, width - 10, index * 20 + 10);
                index++;
            }
        }
    }

    public void mousePressed() {
        if (!gameStarted) {
            gameStarted = true;
        } else {
            SphereStep nextSize;
            if (!nextSizes.isEmpty()) {
                nextSize = nextSizes.poll();
            } else {
                nextSize = SphereStep.STEP_1;
            }

            followingSphere.diameter = nextSize.getSize();

            Sphere newSphere = new Sphere(this, mouseX, wallThickness, nextSize);
            newSphere.checkCollision(spheres);
            spheres.add(newSphere);

            nextSizes.add(SphereStep.values()[(int) (Math.random() * 3)]);
            printQueueContents();
        }
    }

    private void printQueueContents() {
        System.out.print("Queue Contents: ");
        for (SphereStep step : nextSizes) {
            System.out.print(step + " ");
        }
        System.out.println();
    }
}
