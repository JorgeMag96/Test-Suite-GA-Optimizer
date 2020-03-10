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
		try {
			FileInputStream fis = new FileInputStream("results//ExcelResults.xlsx");
			Workbook wb = WorkbookFactory.create(fis);
			
			if(wb.getSheet("GA Results") == null) {
				wb.createSheet("GA Results");
			}
			else {
				wb.removeSheetAt(wb.getSheetIndex(wb.getSheet("GA Results")));
				wb.createSheet("GA Results");
			}
			
			Sheet sheet = wb.getSheet("GA Results");
			Row row = sheet.createRow(1);
			Cell cell = row.createCell(0);
			
			resultsTxtArea.appendText(fittest.toString()+"\n");
			cell.setCellValue(fittest.toString());
			resultsTxtArea.appendText("---------------------------\n");
			
			row = sheet.createRow(3);
			cell = row.createCell(0);
			cell.setCellValue("Test Case");
			cell = row.createCell(1);
			cell.setCellValue("Statements Covered");
			
			int testCases = 0;
			for(int i = 0; i < fittest.getGenes().length; i++) {
				if(fittest.getGenes()[i] == 1) {
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
			resultsTxtArea.appendText("Total test cases = "+testCases+"\n");
			row = sheet.createRow(testCases+5);
			cell = row.createCell(0);
			cell.setCellValue("Total test cases = "+testCases);
			
			resultsTxtArea.appendText("Statements covered = "+fittest.getFitness()+"\n");
			cell = row.createCell(1);
			cell.setCellValue("Statements covered = "+fittest.getFitness());
			
			resultsTxtArea.appendText("GA % of coverage = "+(((float)fittest.getFitness()/ProblemSingleton.getInstance().getCodeStatements())*100)+"%");
			row = sheet.createRow(testCases+7);
			cell = row.createCell(0);
			cell.setCellValue("GA % of coverage = "+(((float)fittest.getFitness()/ProblemSingleton.getInstance().getCodeStatements())*100)+"%");
			
			ProblemSingleton.getInstance().setGeneticAlgorithmFitness(fittest.getFitness());
			resultsTxtArea.setEditable(false);
			
			FileWriter myWriter;
		
			myWriter = new FileWriter("results//GeneticAlgorithmResults.txt");
			myWriter.write(resultsTxtArea.getText());
			myWriter.flush();
			myWriter.close();
			FileOutputStream fos = new FileOutputStream("results//ExcelResults.xlsx");
			wb.write(fos);
			fos.flush();
			fos.close();
			fis.close();
		} catch (IOException e) {
			AlertHandler.showAlert(Alert.AlertType.ERROR, "Error while writting to file", e.getCause().toString(), e.getMessage());
		}
		
	}
	
	@FXML private AnchorPane rootPane;
	@FXML private TextArea resultsTxtArea;
	@FXML private Button proceedBtn;
}
