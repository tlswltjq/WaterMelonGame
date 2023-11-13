package watermelon.page;

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

        // Draw title
        fill(0);
        textSize(24);
        text("Temp", width / 2, height / 6);

        // Draw rounded rectangles as buttons
        drawRoundedRectangle(width / 2 - 150, height / 3 * 2, "Game Start");
        drawRoundedRectangle(width / 2 + 150, height / 3 * 2, "Exit");
    }

    void drawRoundedRectangle(float x, float y, String label) {
        // Check if the mouse is over the button
        boolean overButton = overRect(x - buttonWidth / 2, y - buttonHeight / 2, buttonWidth, buttonHeight);

        // Determine the button color based on mouse over state
        if (overButton) {
            fill(200);
        } else {
            fill(150);
        }

        // Draw the rounded rectangle
        rect(x, y, buttonWidth, buttonHeight, cornerRadius);

        // Draw the label on the button
        fill(0);
        textSize(16);
        text(label, x, y);
    }

    boolean overRect(float x, float y, float width, float height) {
        // Check if the mouse is over the rectangle
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public void mousePressed() {
        // Check if the mouse is pressed over the buttons
        if (overRect(width / 2 - 150, height / 3 * 2, buttonWidth, buttonHeight)) {
            openNewWindow();
            this.noLoop();  // draw() 함수를 멈추어 화면을 감춤
            this.surface.setVisible(false);  // 창을 화면에서 숨김
        } else if (overRect(width / 2 + 150, height / 3 * 2, buttonWidth, buttonHeight)) {
            exit();
            // Add actions to be performed when "Exit" button is clicked
        }
    }
    private void openNewWindow() {
        PApplet.runSketch(new String[]{""}, new GamePA());
    }
}
