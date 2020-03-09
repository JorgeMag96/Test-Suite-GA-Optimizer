package com.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.models.Individual;
import com.utils.AlertHandler;
import com.utils.GreedyAlgorithm;
import com.utils.ProblemSingleton;
import com.views.ViewsHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
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
		
		printResults();
	}
	
	private void nextScreenValidation() {
		try {
			AnchorPane nextPane = FXMLLoader.load(ViewsHandler.class.getResource("main-screen.fxml"));
			rootPane.getChildren().setAll(nextPane);
		} catch (IOException e) {
			AlertHandler.showAlert(AlertType.ERROR, "Error Dialog",
					e.getMessage(),
					"");
		}
	}
	
	private void printResults() {
		GreedyAlgorithm greedyAlgo = new GreedyAlgorithm();
		Individual lastResult = greedyAlgo.getGreedyIndividual();
		int testCases = 0;
		
		resultsTxtArea.appendText(lastResult.toString()+"\n");
		resultsTxtArea.appendText("---------------------------\n");
		for(int i = 0; i < lastResult.getGenes().length; i++) {
			if(lastResult.getGenes()[i] == 1) {
				resultsTxtArea.appendText("Test case "+(i+1)+" = "+ProblemSingleton.getInstance().getTestCasesArray().get(i)+" statements covered.\n");
				testCases++;
			}
		}
		resultsTxtArea.appendText("---------------------------\n");
		resultsTxtArea.appendText("Total test cases = "+testCases+"\n");
		resultsTxtArea.appendText("Total statements covered = "+lastResult.getFitness()+"\n");
		resultsTxtArea.appendText("Greedy optimization % of coverage = "+(((float)lastResult.getFitness()/ProblemSingleton.getInstance().getCodeStatements())*100)+"%");
		resultsTxtArea.setEditable(false);
		
		try {
			FileWriter myWriter = new FileWriter("results//GreedyResults.txt");
			myWriter.write(resultsTxtArea.getText());
			myWriter.flush();
			myWriter.close();
		}
		catch (IOException e) {
			AlertHandler.showAlert(Alert.AlertType.ERROR, "Error while writting to file", e.getCause().toString(), e.getMessage());
		}
	}
	
	@FXML private AnchorPane rootPane;
	@FXML private ImageView imageView;
	@FXML private TextArea resultsTxtArea;
	@FXML private Button exitBtn;
}
