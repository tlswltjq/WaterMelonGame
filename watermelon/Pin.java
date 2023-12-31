package watermelon;

import processing.core.PApplet;

class Pin {
    PApplet pApplet;
    float x, y;
    int color;
    int diameter;

    public Pin(PApplet pApplet, float x, float y) {
        this.pApplet = pApplet;
        this.x = x;
        this.y = y;
        this.color = pApplet.color(255, 255, 0);
        this.diameter =20;
    }

    public void display() {
        pApplet.fill(color);
        pApplet.noStroke();
        pApplet.ellipse(x, y, diameter, diameter);
    }
}