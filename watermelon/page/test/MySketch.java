package watermelon.page.test;

import processing.core.PApplet;
import processing.core.PSurface;

public class MySketch extends PApplet {
    public static void main(String[] args) {
        PApplet.main("MySketch");
    }

    public void settings() {
        size(400, 400);
    }

    public void setup() {
        // 초기화 코드 작성
    }

    public void draw() {
        // 그리기 코드 작성
        background(255);
        ellipse(width / 2, height / 2, 50, 50);
    }
}

