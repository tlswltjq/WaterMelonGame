package watermelon;

import processing.core.PApplet;

public class Item {
    PApplet pApplet;

    Item(PApplet pApplet) {
        this.pApplet = pApplet;
    }

    public void createWall() {
        // 시작점을 마우스 클릭 위치로 설정
        float startX = pApplet.mouseX;
        float startY = pApplet.mouseY;

        // 종단 방향을 두 번째 마우스 클릭 위치로 설정
        pApplet.text("Click the endpoint for the wall", pApplet.width / 2, pApplet.height - 50);
        float endX = pApplet.mouseX;
        float endY = pApplet.mouseY;

        // 벽의 길이 초기화
        float wallLength = 30;

        // 벽 그리기
        pApplet.stroke(0, 0, 255, 255);  // 파란색 벽, 초기 투명도
        pApplet.strokeWeight(5);
        pApplet.line(startX, startY, endX, endY);

        // 벽에 부딛힌 구체 확인 및 투명도 감소
        for (Sphere sphere : GamePA.spheres) {
            if (hasHitWall(sphere, startX, startY, endX, endY, wallLength)) {
                sphere.reduceTransparency();  // 구체에 부딛혔을 때 투명도 감소
                if (sphere.getTransparency() <= 0) {
                    GamePA.spheres.remove(sphere);  // 투명도가 0 이하이면 구체 삭제
                }
            }
        }
    }

    private boolean hasHitWall(Sphere sphere, float startX, float startY, float endX, float endY, float wallLength) {
        float distance = pApplet.dist(sphere.x, sphere.y, startX, startY);

        if (distance < sphere.diameter / 2) {
            // 시작점과의 거리가 구체 반지름보다 작으면 부딛힌 것으로 간주
            return true;
        } else {
            // 시작점과의 거리가 구체 반지름보다 크면 종단 방향 확인
            float angle = PApplet.atan2(endY - startY, endX - startX);
            float wallDirectionX = PApplet.cos(angle);
            float wallDirectionY = PApplet.sin(angle);

            // 벽의 방향과 구체의 방향 벡터 내적
            float dotProduct = (sphere.x - startX) * wallDirectionX + (sphere.y - startY) * wallDirectionY;

            // 내적이 0보다 크고 벽의 길이보다 작으면 벽에 부딛힌 것으로 간주
            return dotProduct > 0 && dotProduct < wallLength;
        }
    }
}
