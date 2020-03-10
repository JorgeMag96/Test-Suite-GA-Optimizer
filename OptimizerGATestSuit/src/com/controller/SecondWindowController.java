package com.controller;

import java.io.IOException;
import java.util.function.UnaryOperator;

import com.utils.AlertHandler;
import com.utils.ProblemSingleton;
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
			try {
				ProblemSingleton singleton = ProblemSingleton.getInstance();
				singleton.setCodeStatements(Integer.parseInt(codeStatementsTxt.getText().toString()));
				singleton.setTestCases(Integer.parseInt(testCasesTxt.getText().toString()));
				
				AnchorPane nextPane = FXMLLoader.load(ViewsHandler.class.getResource("third-screen.fxml"));
				rootPane.getChildren().setAll(nextPane);
			} catch (IOException e) {
				AlertHandler.showAlert(AlertType.ERROR, "Error Dialog",
						e.getMessage(),
						"");
			}
		}
	}
	
	
	
	@FXML private TextField testCasesTxt;
	@FXML private TextField codeStatementsTxt;
	@FXML private Button nextBtn;
	@FXML private AnchorPane rootPane;
}
