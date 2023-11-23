package watermelon;

import processing.core.PApplet;

public class Wall {
    PApplet pApplet;
    float x1, y1; // 첫 번째 클릭 지점
    float x2, y2; // 두 번째 클릭 지점
    float thickness = 5;
    float length = 100;
    int collisions = 0;
    int maxCollisions = 2;
    int color;

    public Wall(PApplet pApplet, float x1, float y1, float x2, float y2, int color) {
        this.pApplet = pApplet;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
    }

    public void display() {
        pApplet.stroke(color);
        pApplet.strokeWeight(thickness);

        // Draw the line
        pApplet.line(x1, y1, x2, y2);

        // Draw the end points
        pApplet.fill(color);
        pApplet.ellipse(x1, y1, thickness, thickness);
        pApplet.ellipse(x2, y2, thickness, thickness);
    }

    public void checkCollision(Sphere sphere) {
        float d = pApplet.dist(sphere.x, sphere.y, x1, y1);

        // Check collision with the first end point
        if (d < sphere.diameter / 2) {
            handleCollision(sphere);
        }

        d = pApplet.dist(sphere.x, sphere.y, x2, y2);

        // Check collision with the second end point
        if (d < sphere.diameter / 2) {
            handleCollision(sphere);
        }

        // Check collision with the line segment
        float lineDist = pApplet.dist(x1, y1, x2, y2);
        float u = ((sphere.x - x1) * (x2 - x1) + (sphere.y - y1) * (y2 - y1)) / (lineDist * lineDist);

        float closestX, closestY;

        if (u >= 0 && u <= 1) {
            closestX = x1 + u * (x2 - x1);
            closestY = y1 + u * (y2 - y1);
        } else {
            if (u < 0) {
                closestX = x1;
                closestY = y1;
            } else {
                closestX = x2;
                closestY = y2;
            }
        }

        d = pApplet.dist(sphere.x, sphere.y, closestX, closestY);

        // Check collision with the line segment
        if (d < sphere.diameter / 2) {
            handleCollision(sphere);
        }
    }

    private void handleCollision(Sphere sphere) {
        if (collisions < maxCollisions) {
            collisions++;
            // Decrease transparency on collision
            color = pApplet.color(pApplet.red(color), pApplet.green(color), pApplet.blue(color), 128);
        } else {
            // Remove the wall after two collisions
            color = pApplet.color(pApplet.red(color), pApplet.green(color), pApplet.blue(color), 0);
        }
    }
}
