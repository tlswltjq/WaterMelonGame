package watermelon;
public enum SphereStep {
    STEP_1(30, 1),
    STEP_2(41, 2),
    STEP_3(56, 3),
    STEP_4(76, 4),
    STEP_5(104, 5),
    STEP_6(141, 6),
    STEP_7(192, 7),
    STEP_8(261, 8),
    STEP_9(355, 9),
    STEP_10(483, 10),
    STEP_11(656, 11);

    private final int size;
    private final int step;

    SphereStep(int size, int step) {
        this.size = size;
        this.step = step;
    }

    public int getSize() {
        return size;
    }

    public int getStep() {
        return step;
    }
}
