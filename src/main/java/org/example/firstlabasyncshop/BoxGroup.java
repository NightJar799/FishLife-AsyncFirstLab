package org.example.firstlabasyncshop;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class BoxGroup {
    private static final int MAXAMMOUNT = 5;
    private static final int MINAMMOUNT = 0;
    private int createTime;
    private int capacity = 0;
    private HBox group;
    private List<Circle> boxes;
    private Node last;
    public BoxGroup (int createTime) {
        this.createTime = createTime;
        group = new HBox();
    }
    public void AddBox(){
        if (capacity < MAXAMMOUNT) {
            Circle circle = new Circle();
            circle.setFill(Color.GREEN);
            circle.setCenterX(50);
            circle.setCenterY((group.getChildren().size() * 3 + 70) * 10);
            circle.setRadius(20);
            last = circle;
            group.getChildren().add(circle);
            capacity++;
        }
    }
    public void delBox(){
        if (capacity != MINAMMOUNT) {
            group.getChildren().remove(last);
            capacity--;
            if (capacity != 0) last = group.getChildren().getLast();
        } else {
            throw new RuntimeException();
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public HBox getGroup() {
        return group;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }
}
