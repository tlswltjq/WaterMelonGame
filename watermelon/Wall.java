package watermelon;

import processing.core.PApplet;

public class Wall {
    PApplet pApplet;
    int startX, startY;
    boolean isFirstClick = true;

    public Wall(PApplet pApplet) {
        this.pApplet = pApplet;
    }

    void drawWallBetweenClicks() {
        if (isFirstClick) {
            // 첫 번째 클릭 지점 저장
            startX = pApplet.mouseX;
            startY = pApplet.mouseY;
            isFirstClick = false;
        } else {
            // 두 번째 클릭 시 두께 5, 길이 100인 직선 그리기
            pApplet.strokeWeight(5);
            pApplet.line(startX, startY, pApplet.mouseX, pApplet.mouseY);

            // 첫 번째 클릭 지점 초기화
            isFirstClick = true;
        }
    }
    void mousePressed() {
        this.drawWallBetweenClicks(); // 객체의 메서드 호출
    }
}
