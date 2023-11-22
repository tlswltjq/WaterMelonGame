package watermelon;

import processing.core.PApplet;

import java.util.ArrayList;

public class Sphere {
    PApplet pApplet;
    float x, y;
    float diameter;
    int step;
    boolean isMerged = false;
    int outlineColor;  // Outline color
    int outlineThickness;  // Outline thickness

    Sphere(PApplet pApplet, float x, float y, SphereStep step, int outlineColor, int outlineThickness) {
        this.pApplet = pApplet;
        this.x = x;
        this.y = y;
        this.diameter = step.getSize();
        this.step = step.ordinal() + 1;
        this.outlineColor = outlineColor;
        this.outlineThickness = outlineThickness;
    }

    void display() {
        // Fill the interior
        pApplet.fill(150);
        pApplet.ellipse(x, y, diameter, diameter);

        // Draw the outline
        pApplet.stroke(outlineColor);
        pApplet.strokeWeight(outlineThickness);
        pApplet.noFill();
        pApplet.ellipse(x, y, diameter, diameter);

        // Draw the text
        pApplet.fill(0);
        pApplet.textAlign(PApplet.CENTER, PApplet.CENTER);
        pApplet.textSize(12);
        pApplet.text(step, x, y);
    }

    void update() {
        y += 5;

        if (y > pApplet.height - diameter / 2) {
            y = pApplet.height - diameter / 2;
        }
    }

    void checkCollision(ArrayList<Sphere> others) {
        for (Sphere other : others) {
            if (other != this) {
                float distance = pApplet.dist(x, y, other.x, other.y);
                float minDist = diameter / 2 + other.diameter / 2;

                if (distance < minDist) {
                    mergeIfPossible(other);
                }
            }
        }
    }

    void mergeIfPossible(Sphere other) {
        if (diameter == other.diameter && !other.isMerged && step == other.step) {
            step = PApplet.max(step, other.step) + 1;
            diameter = SphereStep.values()[step - 1].getSize();

            other.isMerged = true;

            System.out.println("Merged Sizes: " + step);
        } else {
            float angle = pApplet.atan2(y - other.y, x - other.x);
            x = other.x + pApplet.cos(angle) * (diameter / 2 + other.diameter / 2);
            y = other.y + pApplet.sin(angle) * (diameter / 2 + other.diameter / 2);
        }
    }

    void checkBoundary() {
        if (y + diameter / 2 > pApplet.height - GamePA.wallThickness) {
            y = pApplet.height - GamePA.wallThickness - diameter / 2;
        }

        if (x - diameter / 2 < GamePA.wallThickness) {
            x = GamePA.wallThickness + diameter / 2;
        }

        if (x + diameter / 2 > pApplet.width - GamePA.wallThickness) {
            x = pApplet.width - GamePA.wallThickness - diameter / 2;
        }
    }

    int findStep(float diameter) {
        for (int i = 0; i < SphereStep.values().length; i++) {
            if (diameter == SphereStep.values()[i].getSize()) {
                return i;
            }
        }
        return -1;
    }
}
