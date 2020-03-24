package com.launcher;

import com.views.ViewsHandler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.utils.ProblemSingleton;


public class Main extends Application {

	Stage window;
	Scene scene1, scene2;
	
	@Override
    public void start(Stage stage) throws Exception {
		window = stage;
		ProblemSingleton.getInstance().setStage(window);
		FXMLLoader loader = new FXMLLoader(ViewsHandler.class.getResource("main-screen.fxml"));
		Parent root = (Parent)loader.load();
		
        Scene scene = new Scene (root, 600, 400);
        window.setTitle("Optimal Test Suit");
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

    public static void main(String[] args) {
        launch();
    }

}