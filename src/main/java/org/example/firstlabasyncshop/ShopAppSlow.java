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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class ShopAppSlow extends Application {
    private double fishY = 600;
    private double fishXOne = 260;
    private double fishXTwo = 660;
    private double fishXThree = 1060;
    @Override
    public void start(Stage stage) {
        Ship ship = new Ship(230, 150, 600, 2);
        Image image = new Image(getClass().getResourceAsStream("/ryba.png"));

        ImageView fishOne = createFish(fishXOne, image);
        ImageView fishTwo = createFish(fishXTwo, image);
        ImageView fishThree = createFish(fishXThree, image);

        List<Ship> ships = new ArrayList<>();
        ships.add(ship);

        List<ImageView> fishes = new ArrayList<>();
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

        int TimeForFish =6;
        for(ImageView fish : fishes) {
            Timeline timelineFish = new Timeline(new KeyFrame(Duration.seconds(TimeForFish), event -> fishEats(ships, fish)));
            timelineFish.setCycleCount(Timeline.INDEFINITE);
            timelineFish.play();
            TimeForFish -=2;
        }

        stage.setTitle("Shipy-ship");
        stage.show();
    }

    private ImageView createFish(double x, Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(100);
        imageView.setX(x);
        imageView.setY(fishY);
        return imageView;
    }

    private void fishEats(List<Ship> ships, ImageView fish) {
        for (Ship ship : ships) {
            if (ship.getBoxes() > 0 && !ship.isBusy()) {
                ship.setBusy(true);

                double startTranslateX = fish.getTranslateX();
                double startTranslateY = fish.getTranslateY();

                double targetTranslateX = -(fish.getX() - ship.getVBoxX() - 50);
                double targetTranslateY = -(fish.getY() - ship.getVBoxY() - 350);
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
