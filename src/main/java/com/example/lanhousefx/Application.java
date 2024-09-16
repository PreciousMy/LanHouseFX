package com.example.lanhousefx;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Application extends javafx.application.Application {
    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("principal.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Vulkani");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(e-> Platform.exit());
    }

    public static Stage newStage(String url) throws IOException {
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(url));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        return stage;
    }

    public static Scene getScene(){
        return scene;
    }

    public static void atualizaCena(String url) throws IOException {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource(url));
        scene.setRoot(loader.load());

        Stage stage = (Stage) scene.getWindow();
        stage.sizeToScene();
    }

    public static void main(String[] args) {
        launch();
    }
}