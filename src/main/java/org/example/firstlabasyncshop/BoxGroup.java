package org.example.firstlabasyncshop;

import javafx.scene.Node;
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
    private Node last;
    private Image image = new Image(getClass().getResourceAsStream("/sweet.png"));
    public BoxGroup () {
        groupFirst = new HBox();
        groupSecond = new HBox();
        groupThird = new HBox();
    }
    public void AddBox(){
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
    public void delBox(){
        if (capacity != MINAMMOUNT) {
            if (capacity < 5) {
                groupFirst.getChildren().remove(capacity);
            } else if (capacity < 10) {
                groupSecond.getChildren().remove(capacity - 5);
            } else {
                groupSecond.getChildren().remove(capacity - 10);
            }
            capacity--;
        } else {
            throw new RuntimeException();
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
