package com.brona.etendue.window;

import com.brona.etendue.math.tuple.Point2;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

@ToString
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public class WindowListener extends MouseAdapter {

    @NotNull
    Consumer<Point2> listener;

    @Override
    public void mousePressed(MouseEvent e) {
        listener.accept(Point2.create(e.getX(), e.getY()));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        listener.accept(Point2.create(e.getX(), e.getY()));
    }


}
