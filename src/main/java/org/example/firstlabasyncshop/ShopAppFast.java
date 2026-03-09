package org.example.firstlabasyncshop;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class ShopAppFast extends Application {
    private double fishY = 600;
    private double fishXOne = 100;
    private double fishXTwo = 500;
    private double fishXThree = 900;
    @Override
    public void start(Stage stage) {
        Ship ship = new Ship(230, 150, 600, 2);

        Path fishOne = createFish(fishXOne, Color.RED, Color.DARKRED);
        Path fishTwo = createFish(fishXTwo, Color.GREEN, Color.DARKGREEN);
        Path fishThree = createFish(fishXThree, Color.BLUE, Color.DARKBLUE);

        List<Ship> ships = new ArrayList<>();
        ships.add(ship);

        List<Path> fishes = new ArrayList<>();
        fishes.add(fishOne);
        fishes.add(fishTwo);
        fishes.add(fishThree);

        Pane pane = new Pane(ship.getTilePane(), fishOne, fishTwo, fishThree);
        pane.setStyle("-fx-background-color: lightblue;");

        Scene scene = new Scene(pane, 1500, 1000, Color.WHITESMOKE);
        stage.setScene(scene);

        double seconds = ship.getSec();
        Timeline timelineADD1 = new Timeline(new KeyFrame(Duration.seconds(seconds), event -> ship.addCircle()));
        timelineADD1.setCycleCount(Timeline.INDEFINITE);
        timelineADD1.play();
        Timeline timelineADD2 = new Timeline(new KeyFrame(Duration.seconds(seconds), event -> ship.addCircle()));
        timelineADD2.setCycleCount(Timeline.INDEFINITE);
        timelineADD2.play();
        Timeline timelineADD3 = new Timeline(new KeyFrame(Duration.seconds(seconds), event -> ship.addCircle()));
        timelineADD3.setCycleCount(Timeline.INDEFINITE);
        timelineADD3.play();

        int TimeForFish =6;
        for(Path fish : fishes) {
            Timeline timelineFish = new Timeline(new KeyFrame(Duration.seconds(TimeForFish), event -> fishEats(ships, fish)));
            timelineFish.setCycleCount(Timeline.INDEFINITE);
            timelineFish.play();
            TimeForFish -=2;
        }

        stage.setTitle("Shipy-ship");
        stage.show();
    }

    private Path createFish(double x, Color in, Color out) {
        Fish fish = new Fish(in, out, x, fishY);
        return fish.getFish();
    }

    private void fishEats(List<Ship> ships, Path fish) {
        for (Ship ship : ships) {
            if (ship.getBoxes() > 0 && !ship.isBusy()) {
                ship.setBusy(true);

                double startTranslateX = fish.getTranslateX();
                double startTranslateY = fish.getTranslateY();

                double targetTranslateX = -(fish.getLayoutX() - ship.getVBoxX() - 50);
                double targetTranslateY = -(fish.getLayoutY() - ship.getVBoxY() - 350);
                System.out.println(startTranslateX + " " +startTranslateY);
                System.out.println(targetTranslateX + " " + targetTranslateY);

                TranslateTransition moveToFood = new TranslateTransition(Duration.seconds(0.1), fish);
                moveToFood.setToX(targetTranslateX);
                moveToFood.setToY(targetTranslateY);

                moveToFood.setOnFinished(event -> {
                    try {
                        ship.deleteCircle();
                    } catch (Exception e) {
                        System.out.println("Ошибка при удалении коробки");
                    }
                });

                TranslateTransition moveBack = new TranslateTransition(Duration.seconds(0.1), fish);
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
