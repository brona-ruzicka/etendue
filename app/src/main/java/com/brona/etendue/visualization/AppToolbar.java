package com.brona.etendue.visualization;

import javax.swing.*;
import java.awt.*;

public class AppToolbar extends JPanel {

    protected final AppButton clear;

    public AppToolbar() {
        this.setLayout(new FlowLayout(FlowLayout.LEADING));

        this.add(this.clear = new AppButton("Clear"));
    }

    public AppButton getClear() {
        return clear;
    }
}
