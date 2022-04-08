package com.brona.etendue.visualization;

import javax.swing.*;
import java.awt.*;

public class AppWindow extends JFrame {

    protected final AppToolbar toolbar;
    protected final PainterCanvas canvas;

    public AppWindow(String name, int width, int height) {
        super(name);
        this.setSize(width,height);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = this.getContentPane();

        this.toolbar = new AppToolbar();
        pane.add(toolbar, BorderLayout.PAGE_START);

        this.canvas = new PainterCanvas();
        pane.add(canvas, BorderLayout.CENTER);
    }


    public PainterCanvas getCanvas() {
        return canvas;
    }

    public AppToolbar getToolbar() {
        return toolbar;
    }


    public void open() {
        this.setVisible(true);
    }

    public void close() {
        this.setVisible(false);
    }

}
