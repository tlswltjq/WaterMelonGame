package watermelon.test;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    private PApplet pApplet;

    public MyPanel(PApplet pApplet) {
        this.pApplet = pApplet;

        // Ensure that the surface is created before attempting to get its native component
        if (pApplet.getSurface() != null && pApplet.getSurface() instanceof PSurfaceAWT) {
            PSurfaceAWT surf = (PSurfaceAWT) pApplet.getSurface();
            Component surfaceComponent = (Component) surf.getNative();

            setLayout(new BorderLayout());
            add(surfaceComponent, BorderLayout.CENTER);
        } else {
            System.err.println("Error: PSurfaceAWT is not available or not properly initialized.");
        }
    }
}
