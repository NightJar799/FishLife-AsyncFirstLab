package org.example.firstlabasyncshop;

import javafx.geometry.Orientation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private VBox vBox;
    private Image image;
    private ImageView imageView;
    private BoxGroup boxGroup;
    private double sec;
    private boolean busy = false;

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public Ship(double XImg, double YImg, double XBox, int sec){
        this.sec = sec;
        image = new Image(getClass().getResourceAsStream("/img.png"));
        imageView = new ImageView(image);
        imageView.setFitHeight(YImg);
        imageView.setFitWidth(XImg);

        boxGroup = new BoxGroup(5);
        vBox = new VBox();
        vBox.getChildren().add(imageView);
        vBox.getChildren().add(boxGroup.getGroup());
        vBox.setSpacing(30);
        vBox.setLayoutX(XBox);
        vBox.setLayoutY(50);
    }

    public VBox getTilePane() {
        return vBox;
    }

    public void addCircle() {
        boxGroup.AddBox();
    }

    public void deleteCircle() {
        boxGroup.delBox();
    }

    public int getBoxes() {
        return boxGroup.getCapacity();
    }

    public double getVBoxX() {
        return vBox.getLayoutX();
    }

    public double getVBoxY() {
        return vBox.getLayoutY();
    }

    public double getSec() {
        return sec;
    }

    public void setSec(double sec) {
        this.sec = sec;
    }
}
