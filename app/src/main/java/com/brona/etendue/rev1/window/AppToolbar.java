package com.brona.etendue.rev1.window;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class AppToolbar extends JPanel {

    protected final List<Component> components;

    public AppToolbar() {
        this.setLayout(new FlowLayout(FlowLayout.LEADING));
        this.components = new LinkedList<>();
    }

    public void addComponent(Component component) {
        components.add(component);
        this.add(component);
    }

}
