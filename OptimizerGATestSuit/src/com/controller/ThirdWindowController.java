package com.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Function;

import com.models.TestCase;
import com.utils.AlertHandler;
import com.utils.EditCell;
import com.utils.GeneticAlgorithm;
import com.utils.ProblemSingleton;
import com.views.ViewsHandler;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ThirdWindowController {

	@SuppressWarnings({ "unchecked" })
	@FXML
	public void initialize() {
		File file = new File("resources/images/thirdScreenImage.png");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        
        table.getSelectionModel().setCellSelectionEnabled(true);
        table.setEditable(true);
        table.getColumns().add(createColumn("Test Case", TestCase::testCaseProperty));
        table.getColumns().add(createColumn("Statements Covered", TestCase::statementsCoveredProperty));
                
        singleton = ProblemSingleton.getInstance();
        if(singleton.isFromDataFile()) {
        	loadDataFromFile();
        }
        else {
        	ArrayList<TestCase> testCases = new ArrayList<>();
        	for(int i = 0; i < singleton.getTestCases(); i++) {
            	testCases.add(new TestCase("Total number of statements covered by test case "+(i+1), "0"));
            }
        	table.getItems().addAll(testCases);
        }        
        
        
        
        table.setOnKeyPressed(event -> {
            TablePosition<TestCase, ?> pos = table.getFocusModel().getFocusedCell() ;
            if (pos != null && event.getCode().isLetterKey()) {
                table.edit(pos.getRow(), pos.getTableColumn());
            }
        });
        
        findMaxBtn.setOnAction(e ->{
        	nextScreenValidation();
        });
        
        
	}

	private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col ;
    }
	
	private void nextScreenValidation() {
			try {
				
				ArrayList<Integer> columnData = new ArrayList<>();
				for (TestCase item : table.getItems()) {
				    columnData.add(Integer.parseInt(item.getStatementsCovered()));
				}
				
				ProblemSingleton.getInstance().setTestCasesArray(columnData);
				GeneticAlgorithm ga = new GeneticAlgorithm(100);
				ga.startAlgorithm();
				AnchorPane nextPane = FXMLLoader.load(ViewsHandler.class.getResource("loading-screen.fxml"));
				rootPane.getChildren().setAll(nextPane);
			} 
			catch (IOException e) {
				AlertHandler.showAlert(AlertType.ERROR, "Error Dialog",
						e.getMessage(),
						"");
			}
	}
	
	private void loadDataFromFile() {
		ArrayList<Integer> codeStatementsList = (ArrayList)singleton.getCodeStatementsList();
		System.out.println("load data from file to table");
		System.out.println(codeStatementsList.size());
		
		ArrayList<TestCase> testCases = new ArrayList<>();
    	for(int i = 0; i < singleton.getTestCases(); i++) {
        	testCases.add(new TestCase("Total number of statements covered by test case "+(i+1), codeStatementsList.get(i).toString()));
        }
    	table.getItems().addAll(testCases);
	}
	
	@FXML private AnchorPane rootPane;
	@FXML private Button findMaxBtn;
	@FXML private TableView<TestCase> table;
	@FXML private ImageView imageView;
	private ProblemSingleton singleton;
}
