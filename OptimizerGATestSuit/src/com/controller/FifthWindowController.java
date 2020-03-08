package com.controller;

import java.io.File;
import java.io.IOException;

import com.models.TestCase;
import com.utils.AlertHandler;
import com.views.ViewsHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class FifthWindowController {
	@FXML
	public void initialize() {
		File file = new File("resources/images/finalImage.png");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        
		exitBtn.setOnAction(e ->{
			nextScreenValidation();
		});
	}
	
	private void nextScreenValidation() {
		System.out.println("Switching to main window, and passing the information...");
		try {
			AnchorPane nextPane = FXMLLoader.load(ViewsHandler.class.getResource("main-screen.fxml"));
			rootPane.getChildren().setAll(nextPane);
		} catch (IOException e) {
			AlertHandler.showAlert(AlertType.ERROR, "Error Dialog",
					e.getMessage(),
					"");
		}
}
	
	@FXML private AnchorPane rootPane;
	@FXML private ImageView imageView;
	@FXML private ListView<TestCase> listView;
	@FXML private Button exitBtn;
}
