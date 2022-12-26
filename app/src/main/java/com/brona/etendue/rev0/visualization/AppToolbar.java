package com.brona.etendue.rev0.visualization;

import javax.swing.*;
import java.awt.*;

public class AppToolbar extends JPanel {

    protected final AppButton clearButton;

    public AppToolbar() {
        this.setLayout(new FlowLayout(FlowLayout.LEADING));

        this.add(this.clearButton = new AppButton("Clear"));
    }

    public AppButton getClearButton() {
        return clearButton;
    }
}
