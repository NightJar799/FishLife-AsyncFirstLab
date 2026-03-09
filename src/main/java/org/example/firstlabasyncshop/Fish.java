package org.example.firstlabasyncshop;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Fish {
    private Path fish;

    public Fish(Color colorIn, Color colorOut, double x, double y) {
        this.fish = new Path();

        MoveTo start = new MoveTo(50, 100);
        CubicCurveTo topBody = new CubicCurveTo(150, 20, 250, 20, 300, 100);
        LineTo tailTop = new LineTo(350, 70);
        QuadCurveTo tailCurve = new QuadCurveTo(330, 100, 350, 130);
        LineTo tailBottom = new LineTo(300, 100);
        CubicCurveTo bottomBody = new CubicCurveTo(250, 180, 150, 180, 50, 100);

        this.fish.getElements().addAll(start, topBody, tailTop, tailCurve, tailBottom, bottomBody);
        this.fish.setLayoutX(x);
        this.fish.setLayoutY(y);

        fish.setFill(colorIn);
        fish.setStroke(colorOut);
        fish.setStrokeWidth(3);
    }

    public Path getFish() {
        return fish;
    }
}
