package com.controller;

import java.io.IOException;
import java.util.function.UnaryOperator;

import com.utils.AlertHandler;
import com.views.ViewsHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.AnchorPane;

public class SecondWindowController {

	@FXML
    public void initialize() {
		UnaryOperator<Change> number = change -> (change.getControlNewText().matches("([1-9][0-9]*)?")) ? change : null;
		testCasesTxt.setTextFormatter(new TextFormatter<>(number));
		codeStatementsTxt.setTextFormatter(new TextFormatter<>(number));
		
		nextBtn.setOnAction(e -> nextScreenValidation());
	}
	
	private void nextScreenValidation() {
		if(testCasesTxt.getText().isEmpty() || codeStatementsTxt.getText().isEmpty()) {
			AlertHandler.showAlert(AlertType.ERROR, "Error Dialog",
					"Please input number of test cases and statements covered.",
					"");
		}
		else {
			System.out.println("Switching to third window, and passing the information...");
			try {
				AnchorPane nextPane = FXMLLoader.load(ViewsHandler.class.getResource("loading-screen.fxml"));
				rootPane.getChildren().setAll(nextPane);
			} catch (IOException e) {
				AlertHandler.showAlert(AlertType.ERROR, "Error Dialog",
						e.getMessage(),
						"");
			}
		}
	}
	
	
	
	public TextField testCasesTxt;
	public TextField codeStatementsTxt;
	public Button nextBtn;
	public AnchorPane rootPane;
}
