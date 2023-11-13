package watermelon.page;

public enum SphereStep {
    STEP_1(30),
    STEP_2(41),
    STEP_3(56),
    STEP_4(76),
    STEP_5(104),
    STEP_6(141),
    STEP_7(192),
    STEP_8(261),
    STEP_9(355),
    STEP_10(483),
    STEP_11(656);

    private final int size;

    SphereStep(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
