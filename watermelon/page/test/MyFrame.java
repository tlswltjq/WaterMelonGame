package watermelon.page.test;

import javax.swing.*;

public class MyFrame extends JFrame {
    public MyFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        MySketch mySketch = new MySketch();
        MyPanel myPanel = new MyPanel(mySketch);
        add(myPanel);

        // 스케치 시작되도록 확인
//        mySketch.start();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MyFrame());
    }
}
