package com.brona.etendue.internal;

import com.brona.etendue.simulation.Intersections;
import com.brona.etendue.simulation.Vectors;
import com.brona.etendue.visualization.AppWindow;
import com.brona.etendue.visualization.PainterPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

public class App extends MouseAdapter implements Runnable {

    protected final AppWindow window;
    protected final PainterPanel canvas;

    protected boolean[] mouseButtons;
    protected boolean shouldStartNew;

    protected final CoordinateConverter coordinateConverter;

    protected final ArrayList<ArrayList<double[]>> lines;
    protected final double[][] ray;


    public App(String name, int width, int height) {

        window = new AppWindow(name, width, height);
        canvas = window.getCanvas();

        mouseButtons = new boolean[]{ false, false };
        shouldStartNew = true;

        coordinateConverter = CoordinateConverter.INVERTED_Y;

        lines = new ArrayList<>();
        ray = new double[][]{ null, null };

        window.getCanvas().addPainter(this::paint);
        window.getCanvas().addMouseListener(this);
        window.getCanvas().addMouseMotionListener(this);
        window.getToolbar().getClearButton().addActionListener(this::clear);

    }

    public void paint(Graphics2D g) {

        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

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

            int[] intersectionCoord = coordinateConverter.toScreenCoords(computeIntersection());
            if (intersectionCoord == null)
                intersectionCoord = Util.getOffscreenPoints(new int[][]{coordA, coordB})[1];

            g.drawLine(coordA[0], coordA[1], intersectionCoord[0], intersectionCoord[1]);

            g.fillOval(coordA[0] - 1, coordA[1] - 1, 3, 3);
            g.fillOval(coordB[0] - 1, coordB[1] - 1, 3, 3);

        }

    }


    public void clear(ActionEvent e) {

        lines.clear();
        shouldStartNew = true;

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

        double[] point = coordinateConverter.toSimulationCoords( new int[]{ e.getX(), e.getY() } );

        if (ray[0] == null) {
            ray[0] = point;
            canvas.repaint();
        } else if (ray[1] == null) {
            ray[1] = point;
            canvas.repaint();
        } else if (shouldStartNew || e.getButton() == MouseEvent.BUTTON3) {
            ArrayList<double[]> line = new ArrayList<>();
            line.add(point);
            lines.add(line);
            shouldStartNew = false;
        } else {
            ArrayList<double[]> lastLine = lines.get(lines.size() - 1);
            lastLine.add(point);

            canvas.repaint();
        }

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

        double[] point = coordinateConverter.toSimulationCoords( new int[]{ e.getX(), e.getY() } );

        if (ray[1] == null && (ray[0][0] != point[0] || ray[0][1] != point[1])) {
            ray[1] = point;
            canvas.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if (ray[1] == null || (!mouseButtons[0] && !mouseButtons[1]))
            return;

        shouldStartNew = true;

        double[] point = coordinateConverter.toSimulationCoords( new int[]{ e.getX(), e.getY() } );
        ArrayList<double[]> lastLine = lines.get(lines.size() - 1);
        lastLine.add(point);

        canvas.repaint();

    }


    protected double[] computeIntersection() {
        return lines.stream()
                .flatMap(list -> {
                    Stream.Builder<double[][]> sections = Stream.builder();

                    double[] last = list.get(0);
                    for (int i = 1; i < list.size(); i++) {
                        double[] current = list.get(i);
                        sections.add(new double[][]{ last, current });
                        last = current;
                    }

                    return sections.build();
                })
                .map(section -> Intersections.intersectLineLike(
                        section[0], section[1], Intersections.LineLike.SECTION,
                        ray[0], ray[1], Intersections.LineLike.RAY
                ))
                .filter(Objects::nonNull)
                .min(Comparator.comparingDouble(
                        intersection -> Vectors.squaredLength(Vectors.minus(intersection, ray[0]))
                ))
                .orElse(null);
    }


    @Override
    public void run() {
        window.open();
    }

}

