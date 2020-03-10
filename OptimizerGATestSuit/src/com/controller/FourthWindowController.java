package com.controller;

import java.io.FileWriter;
import java.io.IOException;

import com.models.Individual;
import com.utils.AlertHandler;
import com.utils.ProblemSingleton;
import com.views.ViewsHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class FourthWindowController {

	@FXML
	public void initialize() {
		
		proceedBtn.setOnAction(e -> {
			nextScreenValidation();
		});
		
		printResults();
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
	
	private void printResults() {
		Individual fittest = ProblemSingleton.getInstance().getfittest();
		
		resultsTxtArea.appendText(fittest.toString()+"\n");
		resultsTxtArea.appendText("---------------------------\n");
		int testCases = 0;
		for(int i = 0; i < fittest.getGenes().length; i++) {
			if(fittest.getGenes()[i] == 1) {
				resultsTxtArea.appendText("Test case "+(i+1)+" = "+ProblemSingleton.getInstance().getTestCasesArray().get(i)+" statements covered.\n");
				testCases++;
			}
		}
		resultsTxtArea.appendText("---------------------------\n");
		resultsTxtArea.appendText("Test cases = "+testCases+"\n");
		resultsTxtArea.appendText("Statements covered = "+fittest.getFitness()+"\n");
		resultsTxtArea.appendText("GA % of coverage = "+(((float)fittest.getFitness()/ProblemSingleton.getInstance().getCodeStatements())*100)+"%");
		ProblemSingleton.getInstance().setGeneticAlgorithmFitness(fittest.getFitness());
		resultsTxtArea.setEditable(false);
		
		FileWriter myWriter;
		try {
			myWriter = new FileWriter("results//GeneticAlgorithmResults.txt");
			myWriter.write(resultsTxtArea.getText());
			myWriter.flush();
			myWriter.close();
		} catch (IOException e) {
			AlertHandler.showAlert(Alert.AlertType.ERROR, "Error while writting to file", e.getCause().toString(), e.getMessage());
		}
		
	}
	
	@FXML private AnchorPane rootPane;
	@FXML private TextArea resultsTxtArea;
	@FXML private Button proceedBtn;
}
