package com.brona.etendue.experiments.draw;

public class DrawingApp {

    public static void main(String[] args) {
        new DrawingApp().show();
    }


    protected final AppFrame frame;
    protected final PainterCanvas canvas;


    public DrawingApp() {
        frame = new AppFrame("Drawing App", 1280, 720);

        canvas = new PainterCanvas();
        frame.add(canvas);

        paint();
    }

    protected void paint() {
        canvas.painters.add(graphics -> {
            graphics.drawPolyline(
                    new int[]{ 100, 200, 600, 300 },
                    new int[]{ 100, 400, 500, 200 },
                    4
            );


        });
    }


    public void show() {
        frame.setVisible(true);
    }

    public void hide() {
        frame.setVisible(false);
    }

}
