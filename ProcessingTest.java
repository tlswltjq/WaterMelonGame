import processing.core.PApplet;

import java.util.ArrayList;

public class ProcessingTest extends PApplet {

    ArrayList<Sphere> spheres;
    @Override
    public void settings() {
        size(600, 400);
    }

    @Override
    public void setup() {
        spheres = new ArrayList<>();
    }

    @Override
    public void draw() {
        background(255);

        for (Sphere sphere : spheres) {
            sphere.display();
            sphere.update();
        }
    }

    @Override
    public void mousePressed() {
        Sphere newSphere = new Sphere(mouseX, mouseY, random(10, 50));
        spheres.add(newSphere);
    }

    class Sphere {
        float x, y;
        float diameter;
        float speed;

        Sphere(float x, float y, float diameter) {
            this.x = x;
            this.y = y;
            this.diameter = diameter;
            this.speed = random(1, 5);
        }

        void display() {
            fill(200, 0, 0);
            ellipse(x, y, diameter, diameter);
        }

        void update() {
            y += speed;

            // Check if the sphere reaches the bottom
            if (y > height - diameter / 2) {
                y = height - diameter / 2; // Keep the sphere on the ground
            }
        }
    }
}
