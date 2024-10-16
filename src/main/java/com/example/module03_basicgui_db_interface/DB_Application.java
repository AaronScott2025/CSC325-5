package com.example.module03_basicgui_db_interface;
import com.example.module03_basicgui_db_interface.db.ConnDbOps;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import java.io.IOException;


public class DB_Application extends Application {

    public static void main(String[] args) {
        launch();
    }


    private Stage primaryStage;
    private static ConnDbOps cdbop;

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(false);
        showScene1();

    }

    private void showScene1() {
        cdbop = new ConnDbOps();
        try {
            cdbop.connectToDatabase();
            Parent root = FXMLLoader.load(getClass().getResource("splash_screen.fxml"));
            Scene scene = new Scene(root, 850, 560);
            scene.getStylesheets().add("style.css");
            primaryStage.setScene(scene);
            primaryStage.show();
            changeScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeScene() {
        try {
            Parent newRoot = FXMLLoader.load(getClass().getResource("db_interface_gui.fxml"));

            Scene currentScene = primaryStage.getScene();
            Parent currentRoot = currentScene.getRoot();
            currentScene.getStylesheets().add("style.css");
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), currentRoot);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(e -> {


                Scene newScene = new Scene(newRoot, 850, 560);
                newScene.setOnKeyPressed(event -> {
                    try {
                        handleKeyPress(event);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                });
                primaryStage.setScene(newScene);

            });

            fadeOut.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case E:
                if(event.isControlDown()) { //Ctrl + E
                    System.exit(0);
                }
                break;
        }
    }
}