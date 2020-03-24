package com.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.UnaryOperator;

import com.controller.SecondWindowController.InputData;
import com.utils.AlertHandler;
import com.utils.ProblemSingleton;
import com.views.ViewsHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class SecondWindowController {

	@FXML
    public void initialize() {
		UnaryOperator<Change> number = change -> (change.getControlNewText().matches("([1-9][0-9]*)?")) ? change : null;
		testCasesTxt.setTextFormatter(new TextFormatter<>(number));
		codeStatementsTxt.setTextFormatter(new TextFormatter<>(number));

		nextBtn.setOnAction(e -> nextScreenValidation());
		selectFileBtn.setOnAction(e -> selectInstance());
		
		initChoiceBox();
		
	}
	
	private void nextScreenValidation() {		

		switch(choicebox.getValue()){
			case MANUAL:				
				if(!getManualData()) {
					AlertHandler.showAlert(AlertType.ERROR, "Error Dialog",
							"Please input number of test cases and statements covered.",
							"");
					return;
				}
				break;
				
			case FILE:
				if(!getFileData()) {
					AlertHandler.showAlert(AlertType.ERROR, "Error Dialog",
							"Unnable to parse file data.",
							"");
					return;
				}
				break;
		}
		
		transitionToNextScreen();
			
	}
	
	public void selectInstance() {
		
		selectInstanceImp();
				
	}
	
	private void selectInstanceImp() {

		FileChooser instanceFileChooser = new FileChooser();
		instanceFileChooser.getExtensionFilters().addAll(
			     new FileChooser.ExtensionFilter("Text Files", "*.txt")			    
			);
		instanceFileChooser.setInitialDirectory(new File(System.getProperty("user.dir")+"\\resources\\files"));
		
		File selectedInstanceFile = instanceFileChooser.showOpenDialog(ProblemSingleton.getInstance().getStage());		
		
		filepath.setText((selectedInstanceFile != null)? selectedInstanceFile.toString():"");

	}
	
	private boolean getFileData() {
			
		codeStatementsList = new ArrayList<>();	
		File data = new File(filepath.getText());
		if(!data.isFile()) {
			AlertHandler.showAlert(AlertType.ERROR, "Error Dialog",
					"Invalid File",
					"");
			return false;
		}
		else {
			try {
				Scanner s = new Scanner(data);
				String line;
				while(s.hasNext()) {
					line = s.nextLine();
					Integer statementsCovered = 0;
					try {
						statementsCovered = Integer.parseInt(line.trim());
					} catch(NumberFormatException ex) {
						AlertHandler.showAlert(AlertType.ERROR, "Number Format Exception",
								ex.getMessage(),
								"");
						return false;
					}
					codeStatementsList.add(statementsCovered);
				}
				testCases = codeStatementsList.size();
				this.codeStatements = (int) codeStatementsList.stream().mapToDouble(a -> a).max().getAsDouble();
				s.close();
			} 
			catch (FileNotFoundException e) {			
				AlertHandler.showAlert(AlertType.ERROR, "File Not Found",
						e.getMessage(),
						"");
				return false;
			}
						
		}
		ProblemSingleton.getInstance().setFromDataFile(true);
		ProblemSingleton.getInstance().setCodeStatementsList(codeStatementsList);
		return true;
	}
	
	private boolean getManualData() {
		if(testCasesTxt.getText().isEmpty() || codeStatementsTxt.getText().isEmpty()) {
			return false;
		}
		else {
			codeStatements 	= Integer.parseInt(codeStatementsTxt.getText());
			testCases		= Integer.parseInt(testCasesTxt.getText());
			return true;
		}
	}
	
	private void initChoiceBox() {
		choicebox.getItems().addAll(InputData.values());
		choicebox.setValue(InputData.MANUAL);
		doInputDataChange();
		choicebox.setOnAction(e -> doInputDataChange());
	}
	
	public void doInputDataChange() {
		switch(choicebox.getValue()) {
		
		case MANUAL:
			testCasesTxt.setDisable(false);
			codeStatementsTxt.setDisable(false);
			filepath.setDisable(true);
			filepath.clear();
			selectFileBtn.setDisable(true);
			break;
			
		case FILE:
			testCasesTxt.setDisable(true);
			codeStatementsTxt.setDisable(true);
			filepath.setDisable(false);
			selectFileBtn.setDisable(false);
			break;
			
		default:
			break;
		}
		
	}
	
	private void transitionToNextScreen() {
		try {
			ProblemSingleton singleton = ProblemSingleton.getInstance();
			singleton.setCodeStatements(codeStatements);
			singleton.setTestCases(testCases);
			
			AnchorPane nextPane = FXMLLoader.load(ViewsHandler.class.getResource("third-screen.fxml"));
			rootPane.getChildren().setAll(nextPane);
		} catch (IOException e) {
			AlertHandler.showAlert(AlertType.ERROR, "Error Dialog",
					e.getMessage(),
					"");
		}
	}
	
	enum InputData{
		MANUAL,FILE;
	}
	
	@FXML private AnchorPane rootPane;
	@FXML private ChoiceBox<InputData> choicebox;
	
	@FXML private TextField testCasesTxt;
	@FXML private TextField codeStatementsTxt;
	
	@FXML private Button selectFileBtn;
	@FXML private TextField filepath;
	
	@FXML private Button nextBtn;
	
	List<Integer> codeStatementsList;
	private int codeStatements = 0;
	private int testCases = 0;
}
