package com.controller;

import java.io.IOException;

import com.models.Individual;
import com.models.TestCase;
import com.utils.AlertHandler;
import com.utils.ProblemSingleton;
import com.views.ViewsHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class FourthWindowController {

	@FXML
	public void initialize() {
		
		proceedBtn.setOnAction(e -> {
			nextScreenValidation();
		});
		Individual fittest = ProblemSingleton.getInstance().getfittest();
		int testCases = 0;
		for(int i = 0; i < fittest.getGenes().length; i++) {
			if(fittest.getGenes()[i] == 1) testCases++;
		}
		resultsTxtArea.appendText(fittest.toString()+"\n");
		resultsTxtArea.appendText("Test cases = "+testCases+"\n");
		resultsTxtArea.appendText("Statements covered = "+fittest.getFitness());
		resultsTxtArea.setEditable(false);
	}
	
	private void nextScreenValidation() {
		try {
			AnchorPane nextPane = FXMLLoader.load(ViewsHandler.class.getResource("fifth-screen.fxml"));
			rootPane.getChildren().setAll(nextPane);
		} catch (IOException e) {
			AlertHandler.showAlert(AlertType.ERROR, "Error Dialog",
					e.getMessage(),
					"");
		}
	}
	
	@FXML private AnchorPane rootPane;
	@FXML private TextArea resultsTxtArea;
	@FXML private Button proceedBtn;
}
