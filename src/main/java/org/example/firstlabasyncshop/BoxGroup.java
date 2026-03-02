package org.example.firstlabasyncshop;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class BoxGroup {
    private static final int MAXAMMOUNT = 5;
    private static final int MINAMMOUNT = 0;
    private int createTime;
    private int capacity = 0;
    private HBox group;
    private Node last;
    private Image image = new Image(getClass().getResourceAsStream("/sweet.png"));
    public BoxGroup (int createTime) {
        this.createTime = createTime;
        group = new HBox();
    }
    public void AddBox(){
        if (capacity < MAXAMMOUNT) {
            ImageView sweet = new ImageView(image);
            sweet.setFitHeight(50);
            sweet.setFitWidth(50);
            sweet.setX(50);
            sweet.setY((group.getChildren().size() * 3 + 70) * 10);
            last = sweet;
            group.getChildren().add(sweet);
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
