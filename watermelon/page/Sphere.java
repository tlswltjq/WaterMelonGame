package watermelon.page;

import processing.core.PApplet;

import java.util.ArrayList;

public class Sphere {
    PApplet pApplet;
    float x, y;  // Position of the sphere
    float diameter;  // Diameter of the sphere
    int step;  // Step in the sequence
    boolean isMerged = false;  // Flag to track merged state

    Sphere(PApplet pApplet, float x, float y, SphereStep step) {
        this.pApplet = pApplet;
        this.x = x;
        this.y = y;
        this.diameter = step.getSize();
        this.step = step.ordinal() + 1;
    }

    // Display the sphere
    void display() {
        // Draw the filled sphere
        pApplet.fill(150);  // Set fill color to gray
        pApplet.ellipse(x, y, diameter, diameter);

        // Draw the border
        pApplet.stroke(0);  // Set stroke color to black
        pApplet.noFill();   // No fill for the border
        pApplet.ellipse(x, y, diameter, diameter);

        // Display the step in the center
        pApplet.fill(0);  // Set fill color to black for text
        pApplet.textAlign(PApplet.CENTER, PApplet.CENTER);
        pApplet.textSize(12);
        pApplet.text(step, x, y);
    }

    // Update the position of the sphere (falling down)
    void update() {
        y += 5;  // Adjust the falling speed as needed

        // Check if the sphere reached the bottom
        if (y > pApplet.height - diameter / 2) {
            y = pApplet.height - diameter / 2;  // Keep the sphere on the ground
        }
    }

    // Check collision with other spheres
    // Check collision with other spheres
    void checkCollision(ArrayList<Sphere> others) {
        for (Sphere other : others) {
            // Avoid self-collision check
            if (other != this) {
                float distance = pApplet.dist(x, y, other.x, other.y);
                float minDist = diameter / 2 + other.diameter / 2;

                // If collision detected, call the function to handle merging
                if (distance < minDist) {
                    mergeIfPossible(other);
                }
            }
        }
    }


    void mergeIfPossible(Sphere other) {
        if (diameter == other.diameter && !other.isMerged && step == other.step) {
            // Merge the spheres
            step = PApplet.max(step, other.step) + 1;  // Update the step after merging
            diameter = SphereStep.values()[step - 1].getSize();  // Update the diameter to the next step

            other.isMerged = true;

            // Print the merged sizes to the console
            System.out.println("Merged Sizes: " + step);
        } else {
            // Adjust positions to avoid overlap (simplest method)
            float angle = pApplet.atan2(y - other.y, x - other.x);
            x = other.x + pApplet.cos(angle) * (diameter / 2 + other.diameter / 2);
            y = other.y + pApplet.sin(angle) * (diameter / 2 + other.diameter / 2);
        }
    }



    // Check collision with boundaries
    void checkBoundary() {
        // Check bottom boundary
        if (y + diameter / 2 > pApplet.height - GamePA.wallThickness) {
            y = pApplet.height - GamePA.wallThickness - diameter / 2;  // Keep the sphere above the bottom wall
        }

        // Check left boundary
        if (x - diameter / 2 < GamePA.wallThickness) {
            x = GamePA.wallThickness + diameter / 2;  // Keep the sphere to the right of the left wall
        }

        // Check right boundary
        if (x + diameter / 2 > pApplet.width - GamePA.wallThickness) {
            x = pApplet.width - GamePA.wallThickness - diameter / 2;  // Keep the sphere to the left of the right wall
        }
    }

    // Find the step in the sequence based on the diameter
    int findStep(float diameter) {
        for (int i = 0; i < SphereStep.values().length; i++) {
            if (diameter == SphereStep.values()[i].getSize()) {
                return i;
            }
        }
        return -1;
    }
}
