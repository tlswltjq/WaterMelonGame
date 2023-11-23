package watermelon;

import processing.core.PApplet;

class Pin {
    PApplet pApplet;
    float x, y;
    int color;

    public Pin(PApplet pApplet, float x, float y) {
        this.pApplet = pApplet;
        this.x = x;
        this.y = y;
        this.color = pApplet.color(255, 255, 0);
    }

    public void display() {
        pApplet.fill(color);
        pApplet.noStroke();
        pApplet.ellipse(x, y, 10, 10);
    }

    public boolean checkCollision(Sphere sphere) {
        float distance = pApplet.dist(x, y, sphere.x, sphere.y);
        float minDist = 5 + sphere.diameter / 2;

        return distance < minDist;
    }
}