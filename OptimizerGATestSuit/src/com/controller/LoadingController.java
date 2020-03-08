package com.controller;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class LoadingController {
	@FXML
	public void initialize() {
		setRotate(innerCircle, true, 360, 10);
		setRotate(outerCircle, false, 180, 18);
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
	
	public AnchorPane rootPane;
	public Circle outerCircle;
	public Circle innerCircle;
	public Text loadingText;
}
