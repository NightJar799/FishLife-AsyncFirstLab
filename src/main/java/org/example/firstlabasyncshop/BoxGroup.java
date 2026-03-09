package org.example.firstlabasyncshop;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class BoxGroup {
    private static final int MAXAMMOUNT = 15;
    private static final int MINAMMOUNT = 0;
    private int capacity = 0;
    private HBox groupFirst;
    private HBox groupSecond;
    private HBox groupThird;
    private Image image = new Image(getClass().getResourceAsStream("/sweet.png"));

    public BoxGroup() {
        groupFirst = new HBox();
        groupSecond = new HBox();
        groupThird = new HBox();
    }

    public synchronized void AddBox() {
        if (capacity < MAXAMMOUNT) {
            ImageView sweet = new ImageView(image);
            sweet.setFitHeight(50);
            sweet.setFitWidth(50);
            sweet.setX(50);

            if (capacity < 5) {
                sweet.setY((groupFirst.getChildren().size() * 3 + 70) * 10);
                groupFirst.getChildren().add(sweet);
            } else if (capacity < 10) {
                sweet.setY((groupSecond.getChildren().size() * 3 + 70) * 10);
                groupSecond.getChildren().add(sweet);
            } else {
                sweet.setY((groupThird.getChildren().size() * 3 + 70) * 10);
                groupThird.getChildren().add(sweet);
            }
            capacity++;
        }
    }

    public synchronized void delBox() {
        if (capacity > MINAMMOUNT) {
            if (capacity <= 5) {
                if (!groupFirst.getChildren().isEmpty()) {
                    groupFirst.getChildren().remove(groupFirst.getChildren().size() - 1);
                }
            } else if (capacity <= 10) {
                if (!groupSecond.getChildren().isEmpty()) {
                    groupSecond.getChildren().remove(groupSecond.getChildren().size() - 1);
                }
            } else {
                if (!groupThird.getChildren().isEmpty()) {
                    groupThird.getChildren().remove(groupThird.getChildren().size() - 1);
                }
            }
            capacity--;
        } else {
            throw new RuntimeException("No boxes to delete");
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public List<HBox> getGroup() {
        ArrayList<HBox> list = new ArrayList<>();
        list.add(groupFirst);
        list.add(groupSecond);
        list.add(groupThird);
        return list;
    }
}