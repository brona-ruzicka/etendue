package com.brona.etendue.experiments.event;

import com.brona.etendue.experiments.draw.AppFrame;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Eventor {

    public static void main(String[] args) {

        AppFrame frame = new AppFrame("Event App", 1280, 720);
        Container pane = frame.getContentPane();


        final List<int[]> points = new ArrayList<>();
        final List<int[][]> lines = new ArrayList<>();


        Canvas canvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                if (g instanceof Graphics2D) {
                    ((Graphics2D) g).setRenderingHint(
                            RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON
                    );
                }

                points.forEach(point -> g.fillRect(point[0] - 2, point[1] - 2, 3, 3));
                lines.forEach(line -> g.drawLine(line[0][0], line[0][1], line[1][0], line[1][1]));

                if (g instanceof Graphics2D) {
                    ((Graphics2D) g).setRenderingHint(
                            RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_OFF
                    );
                }
            }
        };

        MouseAdapter adapter = new MouseAdapter() {

            protected boolean initial = true;
            protected int[] lastPoint = null;

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1)
                    return;

                lastPoint = new int[]{ e.getX(), e.getY() };
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (lastPoint == null)
                    return;

                int[] point = new int[]{ e.getX(), e.getY() };
                lines.add(new int[][]{ lastPoint, point });

                canvas.repaint(
                        Math.min(lastPoint[0], point[0]) - 5,
                        Math.min(lastPoint[1], point[1]) - 5,
                        Math.abs(lastPoint[0] - point[0]) + 10,
                        Math.abs(lastPoint[1] - point[1]) + 10
                );

                initial = false;
                lastPoint = point;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1)
                    return;

                if (initial) {
                    points.add(lastPoint);
                    canvas.repaint(
                            lastPoint[0] - 5,
                            lastPoint[1] - 5,
                            10,
                            10
                    );
                } else {
                    mouseDragged(e);
                    initial = true;
                }

                lastPoint = null;
            }

        };

        canvas.addMouseListener(adapter);
        canvas.addMouseMotionListener(adapter);

        pane.add(canvas, BorderLayout.CENTER);


        Button button = new Button("Clear");
        button.setPreferredSize(new Dimension(200, 50));

        button.addActionListener(event -> {
            points.clear();
            lines.clear();
            canvas.repaint();
        });

        pane.add(button, BorderLayout.PAGE_START);


        frame.setVisible(true);

    }

}
