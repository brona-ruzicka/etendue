package com.brona.etendue.experiments.graphincs;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class AppCanvas extends Canvas {

    protected final List<BufferedImage> layers =  new ArrayList<BufferedImage>();

    public AppCanvas() {

        createBufferStrategy(3);



    }





}
