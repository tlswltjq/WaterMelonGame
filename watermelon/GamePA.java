package watermelon;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GamePA extends PApplet {
    ArrayList<Sphere> spheres;
    ArrayList<Pin> pins;
    Queue<SphereStep> nextSizes;
    Sphere followingSphere;
    static int wallThickness = 20;
    boolean gameStarted = false;
    int currentPlayer = 1;
    int player1Score = 0;
    int player2Score = 0;
    boolean canClick = true;
    int timer = 0;
    float deadline = 160;
    boolean itemUse = false;
    int callCount1 = 0;
    int callCount2 = 0;
    int pinCostPlayer1 = 1;
    int pinCostPlayer2 = 1;

    public void settings() {
        pixelDensity(1);
        size(600, 750);
    }

    public void setup() {
        initializeGame();
    }

    public void draw() {
        background(255);

        if (!gameStarted) {
            fill(0);
            textAlign(CENTER, CENTER);
            textSize(32);
            text("Click to start the game!", width / 2, height / 2);
        } else {
            showQueueContents();
            updateFollowingSphere();
            updateAndDisplaySpheres();
            drawBoundaries();
            displayFollowingSphere();

            stroke(0, 0, 0, 100);
            strokeWeight(2);
            line(0, deadline, width, deadline);

            fill(0);
            textSize(20);
            textAlign(LEFT, TOP);
            text("Player 1 Score: " + player1Score, 20, 20);

            textAlign(RIGHT, TOP);
            text("Player 2 Score: " + player2Score, width - 20, 20);

            fill(0, 0, 0, 178);
            textSize(40);
            textAlign(CENTER, BOTTOM);
            text("Player: " + currentPlayer, width / 2, 100);

            fill(0);
            textSize(20);
            textAlign(CENTER, TOP);
            text("Timer: " + timer, width / 2, 140);

            if (timer > 0) {
                timer--;
                if (timer == 0) {
                    canClick = true;
                    switchTurn();
                }
            }
        }
    }

    public void keyPressed() {
        if (timer == 0 && key == 'q') {
            itemUse = true;
            if (currentPlayer == 1 && Math.pow(2, callCount1) <= player1Score) {
                createNewPin(mouseX, mouseY);
                player1Score -= Math.pow(2, callCount1);
                callCount1++;
                pinCostPlayer1 *= 2;
            } else if (currentPlayer == 2 && Math.pow(2, callCount2) <= player2Score) {
                createNewPin(mouseX, mouseY);
                player2Score -= Math.pow(2, callCount2);
                callCount2++;
                pinCostPlayer2 *= 2;
            }
            itemUse = false;
        }
    }

    public void mousePressed() {
        if (!gameStarted) {
            startGame();
        } else if (canClick && timer == 0) {
            if (hasHitDeadline()) {

                int[] sc = sumScoresByOwner();
                this.noLoop();
                this.surface.setVisible(false);

                PApplet.runSketch(new String[]{""}, new ResultPA(sc[0], sc[1]));
            } else {
                handlePlayerTurn();
            }
        }
    }

    private void startGame() {
        gameStarted = true;
        currentPlayer = 1;
        initializeGame();
    }

    private void handlePlayerTurn() {
        SphereStep nextSize = getNextSize();
        updateFollowingSphereSize(nextSize);

        int outlineColor = (currentPlayer == 1) ? color(0, 0, 255) : color(0, 255, 0);

        if (!itemUse) {
            createNewSphere(nextSize, outlineColor);
        }

        canClick = false;
        timer = 180;
    }

    private void initializeGame() {
        spheres = new ArrayList<>();
        pins = new ArrayList<>();
        nextSizes = new LinkedList<>();

        for (int i = 0; i < 10; i++) {
            nextSizes.add(SphereStep.values()[(int) (Math.random() * 3)]);
        }

        followingSphere = new Sphere(this, this, width / 2f, wallThickness, SphereStep.STEP_1, color(0, 0, 255), 2, currentPlayer);

    }

    private void updateFollowingSphere() {
        followingSphere.x = mouseX;
        followingSphere.y = wallThickness;
        if (!nextSizes.isEmpty()) {
            SphereStep firstSize = nextSizes.peek();
            followingSphere.diameter = firstSize.getSize();
            followingSphere.step = firstSize.getStep();
            followingSphere.sphereImage = this.loadImage("watermelon/src/mark.png");
        }
    }

    private void updateAndDisplaySpheres() {
        for (int i = spheres.size() - 1; i >= 0; i--) {
            Sphere s = spheres.get(i);
            s.display();
            s.update();
            s.checkCollision(spheres, pins);
            s.checkBoundary();
            if (s.isMerged) {
                if (currentPlayer == 1) {
                    player1Score += s.step * 2;
                } else {
                    player2Score += s.step * 2;
                }
                spheres.remove(i);
            }
        }

        for (Pin pin : pins) {
            pin.display();
        }
    }

    private void drawBoundaries() {
        fill(150);
        stroke(100);
        strokeWeight(1);

        rect(0, height - wallThickness, width, wallThickness);
        rect(0, 0, wallThickness, height);
        rect(width - wallThickness, 0, wallThickness, height);
    }

    private void displayFollowingSphere() {
        followingSphere.display();
    }

    private SphereStep getNextSize() {
        return nextSizes.isEmpty() ? SphereStep.STEP_1 : nextSizes.poll();
    }

    private void updateFollowingSphereSize(SphereStep nextSize) {
        followingSphere.diameter = nextSize.getSize();
        followingSphere.step = nextSize.getStep();
    }

    private void createNewSphere(SphereStep nextSize, int outlineColor) {
        Sphere newSphere = new Sphere(this, this, mouseX, wallThickness, nextSize, outlineColor, 2, currentPlayer);
        newSphere.checkCollision(spheres, pins);
        spheres.add(newSphere);
    }

    private void createNewPin(float x, float y) {
        Pin newPin = new Pin(this, x, y);
        pins.add(newPin);
    }

    private void switchTurn() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    private boolean hasHitDeadline() {
        boolean hit = false;
        for (Sphere s : spheres) {
            if (s.y - s.diameter / 2 < deadline) {
                hit = true;
                if (currentPlayer ==1){
                    player2Score-=20;
                }else{
                    player1Score-=20;
                }
                break;
            }
        }
        return hit;
    }
    private int[] sumScoresByOwner() {
        int player1TotalScore = 0;
        int player2TotalScore = 0;

        for (Sphere s : spheres) {
            if (s.owner == 1) {
                player1TotalScore += s.step * 2;
            } else if (s.owner == 2) {
                player2TotalScore += s.step * 2;
            }
        }


        // 총 점수를 배열에 담아 반환
        int[] totalScores = {player1TotalScore, player2TotalScore};
        return totalScores;
    }
    private void showQueueContents(){
        float imageX = width / 2f - 50; // 이미지를 화면 중앙 상단에 그릴 위치
        float imageY = 20;

        // 큐가 비어있지 않을 때만 이미지 그리기
        if (!nextSizes.isEmpty()) {
            SphereStep firstSize = nextSizes.peek();
            image(getSphereStepImage(firstSize), imageX, imageY, 50, 50);
        }

        if (nextSizes.size() > 1) {
            SphereStep secondSize = new ArrayList<>(nextSizes).get(1);
            image(getSphereStepImage(secondSize), imageX + 60, imageY, 50, 50);
        }
    }
    private PImage getSphereStepImage(SphereStep step) {
        String imagePath = "watermelon/src/" + step.name() + ".png";
        return loadImage(imagePath);
    }
}
