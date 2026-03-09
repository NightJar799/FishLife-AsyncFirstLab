package org.example.firstlabasyncshop;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
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
    private volatile boolean runningFirstFish = true;
    private volatile boolean addingFlag = true;
    @Override
    public void start(Stage stage) {
        Ship ship = new Ship(230, 150, 600, 0.5);

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

        startNewFoodCreation(ships);
        startFishEatsThread(fishOne, ships);

        stage.setTitle("Shipy-ship");
        stage.show();
    }

    public void startNewFoodCreation(List<Ship> ships) {
        addingFlag = true;
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    Platform.runLater(() -> {
                        //Создание коробки анимация
                    });
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Бесконечное повторение

        Thread workerThread = new Thread(() -> {
            while (addingFlag) {
                try {
                    Platform.runLater(() -> {
                        Ship ship = ships.getFirst();
                        if (ship.getBoxes() < 15) {  // Добавить проверку
                            try {
                                ship.addCircle();
                            } catch (Exception e) {
                                System.out.println("Error: " + e.getMessage());
                            }
                        }
                    });

                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted");
                    break;
                }
            }
            System.out.println("Worker creater thread finished");
        });

        workerThread.setDaemon(true);

        timeline.play();
        workerThread.start();
    }

    public void startFishEatsThread(Path fish, List<Ship> ships) {
        runningFirstFish = true;

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2), event -> {
                    Platform.runLater(() -> {
                        fishEats(ships, fish);
                    });
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Бесконечное повторение

        Thread workerThread = new Thread(() -> {
            while (runningFirstFish) {
                try {

                    Platform.runLater(() -> {
                        Ship ship = ships.getFirst();
                        if (ship.getBoxes() > 0) {  // Добавить проверку
                            try {
                                ship.deleteCircle();
                            } catch (Exception e) {
                                System.out.println("Error: " + e.getMessage());
                            }
                        }
                    });

                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted");
                    break;
                }
            }
            System.out.println("Worker thread finished");
        });

        workerThread.setDaemon(true);

        timeline.play();
        workerThread.start();
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

                TranslateTransition moveToFood = new TranslateTransition(Duration.seconds(0.5), fish);
                moveToFood.setToX(targetTranslateX);
                moveToFood.setToY(targetTranslateY);

                TranslateTransition moveBack = new TranslateTransition(Duration.seconds(0.5), fish);
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
