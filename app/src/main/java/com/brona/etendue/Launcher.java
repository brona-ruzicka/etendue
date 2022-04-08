package com.brona.etendue;

import com.brona.etendue.visualization.AppWindow;

public class Launcher {

    public static void main(String[] args) {

        AppWindow frame = new AppWindow("Etendue App", 1280, 720);

        frame.getCanvas().addPainter(g -> {
            g.drawPolyline(
                    new int[]{ 100, 200, 600, 300 },
                    new int[]{ 100, 400, 500, 200 },
                    4
            );
        });

        frame.setVisible(true);

    }

}
