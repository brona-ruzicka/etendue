package com.brona.etendue.experiments.draw;

import javax.swing.*;

public class AppFrame extends JFrame {

    public AppFrame(String name, int width, int height) {
        super(name);
        this.setSize(width,height);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
