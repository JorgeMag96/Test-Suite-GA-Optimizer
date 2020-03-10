package com.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.models.TestCase;
import com.utils.AlertHandler;
import com.utils.GeneticAlgorithm;
import com.utils.ProblemSingleton;
import com.views.ViewsHandler;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.NumberStringConverter;

public class ThirdWindowController {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	public void initialize() {
		File file = new File("resources/images/thirdScreenImage.png");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        
        table.setEditable(true);
        TableColumn testCaseCol = new TableColumn<>("Test Case");
        testCaseCol.setCellValueFactory(new PropertyValueFactory<TestCase, String>("testCase"));
        
        TableColumn statementsCoveredCol = new TableColumn<>("Statements Covered");
        statementsCoveredCol.setCellValueFactory(new PropertyValueFactory<TestCase, Integer>("statementsCovered"));
        
        statementsCoveredCol.setCellFactory(TextFieldTableCell.<TestCase, Number>forTableColumn(new NumberStringConverter()));
        statementsCoveredCol.setOnEditCommit(e -> editStatementsCovered((CellEditEvent)e));
        
        ArrayList<TestCase> testCases = new ArrayList<>();
        ProblemSingleton singleton = ProblemSingleton.getInstance();
        for(int i = 0; i < singleton.getTestCases(); i++) {
        	testCases.add(new TestCase("Total number of statements covered by test case "+(i+1), 0));
        }
        
        table.getColumns().addAll(testCaseCol, statementsCoveredCol);
        table.setItems(FXCollections.observableArrayList(testCases));
        
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        
        findMaxBtn.setOnAction(e ->{
        	nextScreenValidation();
        });
	}

	private void nextScreenValidation() {
			try {
				
				ArrayList<Integer> columnData = new ArrayList<>();
				for (TestCase item : table.getItems()) {
				    columnData.add(item.getStatementsCovered());
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
	
	@SuppressWarnings("rawtypes")
	public void editStatementsCovered(CellEditEvent edittedCell) {
		TestCase testCaseSelected = table.getSelectionModel().getSelectedItem();
		testCaseSelected.setStatementsCovered(Integer.parseInt(edittedCell.getNewValue().toString()));
	}
	
	@FXML private AnchorPane rootPane;
	@FXML private Button findMaxBtn;
	@FXML private TableView<TestCase> table;
	@FXML private ImageView imageView;
}
