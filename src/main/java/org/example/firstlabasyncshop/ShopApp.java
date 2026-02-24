package org.example.firstlabasyncshop;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class ShopApp extends Application {
    private double fishY = 500;
    private double fishXOne = 300;
    private double fishXTwo = 700;
    private double fishXThree = 1100;
    @Override
    public void start(Stage stage) {
        Ship shipOne = new Ship(230, 150, 200, 3);
        Ship shipTwo = new Ship(230, 150, 600, 10);
        Ship shipThree = new Ship(230, 150, 1000, 1);

        Circle fishOne = createFish(fishXOne);
        Circle fishTwo = createFish(fishXTwo);
        Circle fishThree = createFish(fishXThree);

        List<Ship> ships = new ArrayList<>();
        ships.add(shipOne);
        ships.add(shipTwo);
        ships.add(shipThree);

        List<Circle> fishes = new ArrayList<>();
        fishes.add(fishOne);
        fishes.add(fishTwo);
        fishes.add(fishThree);

        Pane pane = new Pane(shipOne.getTilePane(), shipTwo.getTilePane(), shipThree.getTilePane(),
                fishOne, fishTwo, fishThree);
        pane.setStyle("-fx-background-color: lightblue;");

        Scene scene = new Scene(pane, 1500, 1000, Color.WHITESMOKE);
        stage.setScene(scene);
        for (Ship ship : ships) {
            double seconds = ship.getSec();
            Timeline timelineADD = new Timeline(new KeyFrame(Duration.seconds(seconds), event -> ship.addCircle()));
            timelineADD.setCycleCount(Timeline.INDEFINITE);
            timelineADD.play();
        }

        for(Circle fish : fishes) {
            Timeline timelineFish = new Timeline(new KeyFrame(Duration.seconds(5), event -> fishEats(ships, fish)));
            timelineFish.setCycleCount(Timeline.INDEFINITE);
            timelineFish.play();
        }

        stage.setTitle("Shipy-ship");
        stage.show();
    }

    private Circle createFish(double x) {
        Circle circle = new Circle();
        circle.setFill(Color.BLUE);
        circle.setRadius(25);
        circle.setCenterY(fishY);
        circle.setCenterX(x);
        return circle;
    }

    private void fishEats(List<Ship> ships, Circle fish) {
        for (Ship ship : ships) {
            if (ship.getBoxes() > 0 && !ship.isBusy()) {
                ship.setBusy(true);

                double startTranslateX = fish.getTranslateX();
                double startTranslateY = fish.getTranslateY();

                double targetTranslateX = ship.getVBoxX() - fish.getCenterX() + 150;
                double targetTranslateY = ship.getVBoxY() - fish.getCenterY() + 250;

                TranslateTransition moveToFood = new TranslateTransition(Duration.seconds(2), fish);
                moveToFood.setToX(targetTranslateX);
                moveToFood.setToY(targetTranslateY);

                moveToFood.setOnFinished(event -> {
                    try {
                        ship.deleteCircle();
                    } catch (Exception e) {
                        System.out.println("Ошибка при удалении коробки");
                    }
                });

                TranslateTransition moveBack = new TranslateTransition(Duration.seconds(1), fish);
                moveBack.setToX(startTranslateX);
                moveBack.setToY(startTranslateY);

                SequentialTransition sequentialTransition = new SequentialTransition(moveToFood, moveBack);
                sequentialTransition.play();
                sequentialTransition.setOnFinished(event -> {
                    ship.setBusy(false);
                });
                break;
            }
        }
    }
}
