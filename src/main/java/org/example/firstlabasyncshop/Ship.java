package org.example.firstlabasyncshop;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class Ship {
    private VBox vBox;
    private Image image;
    private BoxGroup boxGroup = new BoxGroup();
    private double sec;
    private boolean busy = false;

    public Ship(double XImg, double YImg, double XBox, double sec){
        this.sec = sec;
        image = new Image(getClass().getResourceAsStream("/ship.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(YImg);
        imageView.setFitWidth(XImg);

        List<HBox> food = boxGroup.getGroup();
        vBox = new VBox();
        vBox.getChildren().add(imageView);
        vBox.getChildren().add(food.get(0));
        vBox.getChildren().add(food.get(1));
        vBox.getChildren().add(food.get(2));
        vBox.setSpacing(30);
        vBox.setLayoutX(XBox);
        vBox.setLayoutY(50);
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public VBox getTilePane() {
        return vBox;
    }

    public synchronized void addCircle() {
        boxGroup.AddBox();
    }

    public synchronized void deleteCircle() {
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
}