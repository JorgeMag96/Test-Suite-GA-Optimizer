package com.controller;

import java.io.File;
import java.io.IOException;

import com.views.ViewsHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MainController {
	
	@FXML
    public void initialize() {
		File file = new File("resources/images/logo.png");
        Image image = new Image(file.toURI().toString());
        logoView.setImage(image);
        
        beginBtn.setOnAction(e ->{
        	try {
        		System.out.println("Switching to second window, and passing the information...");
				AnchorPane pane = FXMLLoader.load(ViewsHandler.class.getResource("second-screen.fxml"));
				rootPane.getChildren().setAll(pane);
			} catch (IOException e1) {
				System.err.print(e1.getMessage());
			}
        });

	}
	
	
	@FXML private ImageView logoView;
	@FXML private Button beginBtn;
	@FXML private AnchorPane rootPane;
}
