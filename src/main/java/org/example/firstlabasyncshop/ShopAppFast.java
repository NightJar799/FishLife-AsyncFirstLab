package org.example.firstlabasyncshop;

import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
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
import java.util.concurrent.atomic.AtomicBoolean;

public class ShopAppFast extends Application {
    private double fishY = 600;
    private double fishXOne = 100;
    private double fishXTwo = 500;
    private double fishXThree = 900;
    private volatile boolean addingFlag = true;

    private AtomicBoolean fishOneBusy = new AtomicBoolean(false);
    private AtomicBoolean fishTwoBusy = new AtomicBoolean(false);
    private AtomicBoolean fishThreeBusy = new AtomicBoolean(false);

    @Override
    public void start(Stage stage) {
        Ship ship = new Ship(230, 150, 600, 0.5);

        Path fishOne = createFish(fishXOne, Color.RED, Color.DARKRED);
        Path fishTwo = createFish(fishXTwo, Color.GREEN, Color.DARKGREEN);
        Path fishThree = createFish(fishXThree, Color.BLUE, Color.DARKBLUE);

        fishOne.setRotate(-90);
        fishTwo.setRotate(-90);
        fishThree.setRotate(-90);

        List<Ship> ships = new ArrayList<>();
        ships.add(ship);

        Pane root = new Pane(ship.getTilePane(), fishOne, fishTwo, fishThree);
        root.setStyle("-fx-background-color: lightblue;");

        Scene scene = new Scene(root, 1500, 1000, Color.WHITESMOKE);
        stage.setScene(scene);

        startNewFoodCreation(ships);
        startNewFoodCreation(ships);
        startNewFoodCreation(ships);

        startFishEatsThread(fishOne, ships, fishOneBusy, 2);
        startFishEatsThread(fishTwo, ships, fishTwoBusy, 3);
        startFishEatsThread(fishThree, ships, fishThreeBusy, 4);

        stage.setTitle("Shipy-ship");
        stage.show();
    }

    public void startNewFoodCreation(List<Ship> ships) {
        addingFlag = true;

        Thread workerThread = new Thread(() -> {
            while (addingFlag) {
                try {
                    Platform.runLater(() -> {
                        Ship ship = ships.getFirst();
                        if (ship.getBoxes() < 15) {
                            try {
                                ship.addFood();
                            } catch (Exception e) {
                                System.out.println("Error adding box: " + e.getMessage());
                            }
                        }
                    });
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Food creator thread interrupted");
                    break;
                }
            }
            System.out.println("Food creator thread finished");
        });
        workerThread.setDaemon(true);
        workerThread.start();
    }

    public void startFishEatsThread(Path fish, List<Ship> ships, AtomicBoolean fishBusyFlag, int sec) {
        Thread workerThread = new Thread(() -> {
            while (addingFlag) {
                try {
                    Thread.sleep(1000 * sec);

                    if (!fishBusyFlag.get()) {
                        Platform.runLater(() -> {
                            Ship ship = ships.getFirst();
                            if (ship.getBoxes() > 0 && ship.reserveBox()) {
                                fishEats(ships, fish, fishBusyFlag);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    System.out.println("Fish thread interrupted");
                    break;
                }
            }
            System.out.println("Fish thread finished");
        });
        workerThread.setDaemon(true);
        workerThread.start();
    }

    private Path createFish(double x, Color in, Color out) {
        Fish fish = new Fish(in, out, x, fishY);
        return fish.getFish();
    }

    private void fishEats(List<Ship> ships, Path fish, AtomicBoolean fishBusyFlag) {
        Ship ship = ships.getFirst();

        fishBusyFlag.set(true);

        try {
            double startTranslateX = fish.getTranslateX();
            double startTranslateY = fish.getTranslateY();
            double startRotate = fish.getRotate();

            double targetTranslateX = -(fish.getLayoutX() - ship.getVBoxX() - 50);
            double targetTranslateY = -(fish.getLayoutY() - ship.getVBoxY() - 350);

            RotateTransition rotateToNormal = new RotateTransition(Duration.seconds(0.3), fish);
            rotateToNormal.setToAngle(0);

            TranslateTransition moveToFood = new TranslateTransition(Duration.seconds(0.5), fish);
            moveToFood.setToX(targetTranslateX);
            moveToFood.setToY(targetTranslateY);

            TranslateTransition moveBack = new TranslateTransition(Duration.seconds(0.5), fish);
            moveBack.setToX(startTranslateX);
            moveBack.setToY(startTranslateY);

            RotateTransition rotateBack = new RotateTransition(Duration.seconds(0.3), fish);
            rotateBack.setToAngle(-90);

            SequentialTransition sequentialTransition = new SequentialTransition(
                    rotateToNormal,
                    moveToFood,
                    moveBack,
                    rotateBack
            );

            sequentialTransition.setOnFinished(event -> {
                ship.confirmDeleteReservedBox();
                fishBusyFlag.set(false);
            });

            sequentialTransition.play();

        } catch (Exception e) {
            System.out.println("Error in fishEats: " + e.getMessage());
            fish.setRotate(-90);
            ship.cancelReservation();
            fishBusyFlag.set(false);
        }
    }

    @Override
    public void stop() {
        addingFlag = false;
    }
}