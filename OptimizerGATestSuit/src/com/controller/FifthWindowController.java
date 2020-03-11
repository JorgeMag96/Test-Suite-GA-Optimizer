package com.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

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
		try {
			FileInputStream fis = new FileInputStream("results//ExcelResults.xlsx");
			Workbook wb = WorkbookFactory.create(fis);
			
			if(wb.getSheet("Greedy Results") == null) {
				wb.createSheet("Greedy Results");
			}
			else {
				wb.removeSheetAt(wb.getSheetIndex(wb.getSheet("Greedy Results")));
				wb.createSheet("Greedy Results");
			}
			
			Sheet sheet = wb.getSheet("Greedy Results");
			Row row = sheet.createRow(1);
			Cell cell = row.createCell(0);
			
			
			resultsTxtArea.appendText(lastResult.toString()+"\n");
			cell.setCellValue(lastResult.toString());
			resultsTxtArea.appendText("---------------------------\n");
			
			row = sheet.createRow(3);
			cell = row.createCell(0);
			cell.setCellValue("Test Case");
			cell = row.createCell(1);
			cell.setCellValue("Statements Covered");
			
			int testCases = 0;
			for(int i = 0; i < lastResult.getGenes().length; i++) {
				if(lastResult.getGenes()[i] == 1) {
					resultsTxtArea.appendText("Test case "+(i+1)+" = "+ProblemSingleton.getInstance().getTestCasesArray().get(i)+" statements covered.\n");
					Row outputRow = sheet.createRow(testCases+4);
					Cell outputCell = outputRow.createCell(0);
					outputCell.setCellValue(testCases+1);
					outputCell = outputRow.createCell(1);
					outputCell.setCellValue(ProblemSingleton.getInstance().getTestCasesArray().get(i));
					testCases++;
				}
			}
		
			resultsTxtArea.appendText("---------------------------\n");
			resultsTxtArea.appendText("Total TC = "+testCases+"\n");
			row = sheet.createRow(testCases+5);
			cell = row.createCell(0);
			cell.setCellValue("Total TC = "+testCases);
			
			resultsTxtArea.appendText("Total SC = "+lastResult.getFitness()+"\n");
			cell = row.createCell(1);
			cell.setCellValue("Total SC = "+lastResult.getFitness());
			
			resultsTxtArea.appendText("Greedy % of coverage = "+(((float)lastResult.getFitness()/ProblemSingleton.getInstance().getCodeStatements())*100)+"%\n");
			row = sheet.createRow(testCases+7);
			cell = row.createCell(0);
			cell.setCellValue("Greedy % of coverage = "+(((float)lastResult.getFitness()/ProblemSingleton.getInstance().getCodeStatements())*100));
			
			resultsTxtArea.appendText("Greedy minimization % = "+(((float)(ProblemSingleton.getInstance().getGeneticAlgorithmFitness()-lastResult.getFitness())/ProblemSingleton.getInstance().getGeneticAlgorithmFitness())*100)+"%");
			row = sheet.createRow(testCases+8);
			cell = row.createCell(0);
			cell.setCellValue("Greedy minimization % = "+(((float)(ProblemSingleton.getInstance().getGeneticAlgorithmFitness()-lastResult.getFitness())/ProblemSingleton.getInstance().getGeneticAlgorithmFitness())*100));
			resultsTxtArea.setEditable(false);
		
			FileWriter myWriter = new FileWriter("results//GreedyResults.txt");
			myWriter.write(resultsTxtArea.getText());
			myWriter.flush();
			myWriter.close();
			FileOutputStream fos = new FileOutputStream("results//ExcelResults.xlsx");
			wb.write(fos);
			fos.flush();
			fos.close();
			fis.close();
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
