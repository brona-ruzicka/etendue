package com.brona.etendue.rev1.window;

import javax.swing.*;
import java.awt.*;

// TODO rework
public class AppWindow extends JFrame {

    protected AppToolbar toolbar = null;
    protected final AppCanvas canvas;

    public AppWindow(String name, int width, int height) {
        super(name);
        this.setSize(width,height);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.canvas = new AppCanvas();
        this.getContentPane().add(canvas, BorderLayout.CENTER);
    }


    public AppCanvas getCanvas() {
        return canvas;
    }


    public AppToolbar setToolbar(AppToolbar toolbar) {
        AppToolbar oldToolbar = this.toolbar;
        if (this.toolbar != null)
            this.getContentPane().remove(this.toolbar);

        this.toolbar = toolbar;
        if (this.toolbar != null)
            this.getContentPane().add(toolbar, BorderLayout.PAGE_START);

        return oldToolbar;
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
