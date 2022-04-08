package com.brona.etendue.internal;

import com.brona.etendue.visualization.AppWindow;
import com.brona.etendue.visualization.PainterCanvas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class App extends MouseAdapter implements Runnable {

    protected final AppWindow window;
    protected final PainterCanvas canvas;

    protected final int[] mousePosition;
    protected boolean[] mouseButtons;

    protected final CoordinateConverter coordinateConverter;

    protected final ArrayList<ArrayList<double[]>> lines;
    protected final double[][] ray;


    public App(String name, int width, int height) {

        window = new AppWindow(name, width, height);
        canvas = window.getCanvas();

        mousePosition = new int[]{ -1, -1 };
        mouseButtons = new boolean[]{ false, false };

        coordinateConverter = CoordinateConverter.INVERSED_Y;

        lines = new ArrayList<>();
        ray = new double[][]{ null, null };


        window.getCanvas().addPainter(this::paint);
        window.getCanvas().addMouseListener(this);
        window.getCanvas().addMouseMotionListener(this);
        window.getToolbar().getClear().addActionListener(this::clear);

    }


    public void paint(Graphics2D g) {

        if (lines.size() > 0) {
            g.setPaint(Color.black);
            lines.forEach(line -> {
                int length = line.size();
                if (length < 2) return;

                int[] xCoords = new int[length];
                int[] yCoords = new int[length];

                for (int i = 0; i < length; i++) {
                    int[] coords = coordinateConverter.toScreenCoords(line.get(i));
                    xCoords[i] = coords[0];
                    yCoords[i] = coords[1];
                }

                g.drawPolyline(xCoords, yCoords, length);
            });
        }

        if (ray[1] != null) {
            g.setPaint(Color.red);

            int[] coordA = coordinateConverter.toScreenCoords(ray[0]);
            int[] coordB = coordinateConverter.toScreenCoords(ray[1]);

            g.drawLine(coordA[0], coordA[1], coordB[0], coordB[1]);
        }

        if ((ray[0] != null && ray[1] == null) || lines.size() > 0) {
            g.setPaint(Color.gray);

            int[] origin;
            if (ray[1] == null) {
                origin = coordinateConverter.toScreenCoords(ray[0]);
            } else {
                ArrayList<double[]> lastLine = lines.get(lines.size() - 1);
                origin = coordinateConverter.toScreenCoords(lastLine.get(lastLine.size() - 1));
            }

            g.drawLine(origin[0], origin[1], mousePosition[0], mousePosition[1]);
        }



    }


    public void clear(ActionEvent e) {

        lines.clear();

        ray[0] = null;
        ray[1] = null;

        window.getCanvas().repaint();

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (mouseButtons[0] || mouseButtons[1])
            return;

        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                mouseButtons[0] = true;
                break;

            case MouseEvent.BUTTON3:
                mouseButtons[1] = true;
                break;

            default:
                return;
        }


        repaintPreview();

        double[] point = coordinateConverter.toSimulationCoords( new int[]{ e.getX(), e.getY() } );

        if (ray[0] == null) {
            ray[0] = point;
        } else if (ray[1] == null) {
            ray[1] = point;

            repaintLine(ray[0], ray[1]);

        } else if (lines.size() == 0 || e.getButton() == MouseEvent.BUTTON3) {
            ArrayList<double[]> line = new ArrayList<>();
            line.add(point);
            lines.add(line);
        } else {
            ArrayList<double[]> lastLine = lines.get(lines.size() - 1);
            double[] origin = lastLine.get(lastLine.size() - 1 );
            lastLine.add(point);

            repaintLine(origin, point);
        }

        updatePreview(e);
        repaintPreview();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                if (!mouseButtons[0]) return;
                mouseButtons[0] = false;
                break;

            case MouseEvent.BUTTON3:
                if (!mouseButtons[1]) return;
                mouseButtons[1] = false;
                break;

            default:
                return;
        }


        repaintPreview();

        double[] point = coordinateConverter.toSimulationCoords( new int[]{ e.getX(), e.getY() } );


        if (ray[1] == null && (ray[0][0] != point[0] || ray[0][1] != point[1])) {
            ray[1] = point;
            repaintLine(ray[0], ray[1]);
        }

        updatePreview(e);
        repaintPreview();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if (ray[1] == null || (!mouseButtons[0] && !mouseButtons[1]))
            return;


        repaintPreview();

        double[] point = coordinateConverter.toSimulationCoords( new int[]{ e.getX(), e.getY() } );
        ArrayList<double[]> lastLine = lines.get(lines.size() - 1);
        double[] origin = lastLine.get(lastLine.size() - 1 );
        lastLine.add(point);

        repaintLine(origin, point);

        updatePreview(e);
        repaintPreview();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        repaintPreview();
        updatePreview(e);
        repaintPreview();
    }

    protected void repaintPreview() {
        if (!( (ray[0] != null && ray[1] == null) || lines.size() > 0 ))
            return;

        int[] origin;
        if (ray[1] == null) {
            origin = coordinateConverter.toScreenCoords(ray[0]);
        } else {
            ArrayList<double[]> lastLine = lines.get(lines.size() - 1);
            origin = coordinateConverter.toScreenCoords(lastLine.get(lastLine.size() - 1));
        }

        canvas.repaint(
                Math.min(origin[0], mousePosition[0]) - 5,
                Math.min(origin[1], mousePosition[1]) - 5,
                Math.abs(origin[0] - mousePosition[0]) + 10,
                Math.abs(origin[1] - mousePosition[1]) + 10
        );
    }

    protected void repaintLine(double[] pointA, double[] pointB) {
        int[] coordA = coordinateConverter.toScreenCoords(pointA);
        int[] coordB = coordinateConverter.toScreenCoords(pointB);

        canvas.repaint(
                Math.min(coordA[0], coordB[0]) - 5,
                Math.min(coordA[1], coordB[1]) - 5,
                Math.abs(coordA[0] - coordB[0]) + 10,
                Math.abs(coordA[1] - coordB[1]) + 10
        );
    }

    protected void updatePreview(MouseEvent e) {
        mousePosition[0] = e.getX();
        mousePosition[1] = e.getY();
    }


    @Override
    public void run() {
        window.open();
    }

}

