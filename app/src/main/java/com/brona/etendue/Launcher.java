package com.brona.etendue;

import com.brona.etendue.internal.App;
import com.brona.etendue.visualization.AppWindow;

public class Launcher {

    public static void main(String[] args) {

        App app = new App("Etendue App", 1280, 720);
        app.run();

    }

}
