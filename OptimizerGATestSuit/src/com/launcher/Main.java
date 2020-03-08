package com.launcher;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {

	Stage window;
	Scene scene1, scene2;
	
	@Override
    public void start(Stage stage) {
		window = stage;
        String version = System.getProperty("java.version");
        Label l = new Label ("Hello, JavaFX 11, running on "+version);
        Scene scene = new Scene (new StackPane(l), 300, 200);
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch();
    }

}