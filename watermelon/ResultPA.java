package watermelon;

import processing.core.PApplet;

public class ResultPA extends PApplet {
    int scorePlayer1;
    int scorePlayer2;
    int display1 = 0;
    int display2 = 0;
    int updateTime; // 마지막으로 업데이트한 시간

    public ResultPA(int p1Score, int p2Score) {
        this.scorePlayer1 = p1Score;
        this.scorePlayer2 = p2Score;
        this.updateTime = millis(); // 초기화
    }

    public void settings() {
        size(600, 750);
    }

    public void setup() {

    }

    public void draw() {
        int elapsedTime = millis() - updateTime;

        // 천천히 각 플레이어의 점수를 증가시키는 코드
        if (display1 < scorePlayer1) {
            display1++;
            updateTime = millis(); // 현재 시간으로 업데이트
        }
        if (display2 < scorePlayer2) {
            display2++;
            updateTime = millis(); // 현재 시간으로 업데이트
        }

        // 화면 업데이트
        updateScreen();
    }

    // 화면 업데이트를 처리하는 메서드
    private void updateScreen() {
        background(255);  // 흰 배경으로 설정
        addButton("Restart", width / 2 - 100, height - 60, 80, 40, this::restartGame);
        addButton("To Menu", width / 2 + 20, height - 60, 80, 40, this::toMainMenu);
        // 글자 간격을 맞추기 위해 textAlign() 사용
        textAlign(CENTER, CENTER);

        // 글자를 두껍게 만들기 위해 textFont()와 textSize() 사용
        textFont(createFont("Arial", 24, true));

        fill(0);
        text("Player 1 Score: " + display1, width / 2, height / 3);
        text("Player 2 Score: " + display2, width / 2, 2 * height / 3);

        // 추가적인 그리기 코드가 필요하다면 여기에 추가
    }

    // 버튼을 그리고 클릭 시 실행할 메서드를 등록
    private void addButton(String label, float x, float y, float w, float h, Runnable onClick) {
        fill(200);
        rect(x, y, w, h, 10);

        // 텍스트 색상 및 배경색 조정
        fill(0);
        textSize(16);

        textAlign(CENTER, CENTER);
        text(label, x + w / 2, y + h / 2);

        if (mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h && mousePressed) {
            onClick.run();
        }
    }

    // 게임 재시작
    private void restartGame() {
        this.noLoop();
        PApplet.runSketch(new String[]{""}, new GamePA());
        this.surface.setVisible(false);
    }

    private void toMainMenu() {
        this.noLoop();
        PApplet.runSketch(new String[]{""}, new MainPA());
        this.surface.setVisible(false);
    }
}
