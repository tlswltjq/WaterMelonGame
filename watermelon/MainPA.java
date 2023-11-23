package watermelon;

import processing.core.PApplet;

public class MainPA extends PApplet {
    int buttonWidth = 100;
    int buttonHeight = 40;
    int cornerRadius = 10;

    public void settings() {
        size(600, 400, P2D);
    }

    public void setup() {
        textAlign(CENTER, CENTER);
        rectMode(CENTER);
    }

    public void draw() {
        background(255);

        fill(0);
        textSize(24);
        text("WATERMELON", width / 2, height / 6);

        drawRoundedRectangle(width / 2 - 150, height / 3 * 2, "Game Start");
        drawRoundedRectangle(width / 2 + 150, height / 3 * 2, "Exit");
    }

    void drawRoundedRectangle(float x, float y, String label) {
        boolean overButton = overRect(x - buttonWidth / 2, y - buttonHeight / 2, buttonWidth, buttonHeight);

        if (overButton) {
            fill(200);
        } else {
            fill(150);
        }

        rect(x, y, buttonWidth, buttonHeight, cornerRadius);

        fill(0);
        textSize(16);
        text(label, x, y);
    }

    boolean overRect(float x, float y, float width, float height) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public void mousePressed() {
        if (overRect(width / 2 - 150, height / 3 * 2, buttonWidth, buttonHeight)) {
            openNewWindow();
            this.noLoop();
            this.surface.setVisible(false);
        } else if (overRect(width / 2 + 150, height / 3 * 2, buttonWidth, buttonHeight)) {
            exit();
        }
    }
    private void openNewWindow() {
        PApplet.runSketch(new String[]{""}, new GamePA());
    }
}
