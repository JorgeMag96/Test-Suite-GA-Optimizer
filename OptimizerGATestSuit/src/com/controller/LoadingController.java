package com.controller;

import java.io.IOException;

import com.utils.AlertHandler;
import com.views.ViewsHandler;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class LoadingController {
	
	@FXML
	public void initialize() {
		
		setRotate(innerCircle, true, 360, 10);
		setRotate(outerCircle, false, 180, 18);
		
		Runnable task = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
					Platform.runLater(() -> transition());
				}
				catch (InterruptedException e) {
					AlertHandler.showAlert(AlertType.ERROR, "Error Dialog",
							e.getMessage(),
							"");
				}
			}
		};
		
		new Thread(task).start();
	}
	
	private void transition() {
		try {
			AnchorPane nextPane = FXMLLoader.load(ViewsHandler.class.getResource("fourth-screen.fxml"));
			rootPane.getChildren().setAll(nextPane);
		} 
		catch (IOException e) {
			AlertHandler.showAlert(AlertType.ERROR, "Error Dialog",
					e.getMessage(),
					"");
		}
	}

	private void setRotate(Circle c, boolean reverse, int angle, int duration) {
		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(duration), c);
		
		rotateTransition.setAutoReverse(reverse);
		
		rotateTransition.setByAngle(angle);
		rotateTransition.setDelay(Duration.seconds(0));
		rotateTransition.setRate(3);
		rotateTransition.setCycleCount(18);
		rotateTransition.play();
	}
	
	@FXML private AnchorPane rootPane;
	@FXML private Circle outerCircle;
	@FXML private Circle innerCircle;
	@FXML private Text loadingText;
}
