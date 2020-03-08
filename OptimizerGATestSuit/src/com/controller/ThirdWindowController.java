package com.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.models.TestCase;
import com.models.test.GeneticAlgorithm;
import com.utils.AlertHandler;
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

	@SuppressWarnings("unchecked")
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
			System.out.println("Switching to fourth window, and passing the information...");
			try {
				//TODO: Retrieve table information to pass test cases array to singleton..
				ArrayList<Integer> columnData = new ArrayList<>();
				for (TestCase item : table.getItems()) {
				    columnData.add(item.getStatementsCovered());
				}
				
				ProblemSingleton.getInstance().setTestCasesArray(columnData);
				GeneticAlgorithm ga = new GeneticAlgorithm(100);
				ga.startAlgorithm();
				//TODO: Run genetic algorithm while loading screen shows up.
				AnchorPane nextPane = FXMLLoader.load(ViewsHandler.class.getResource("loading-screen.fxml"));
				rootPane.getChildren().setAll(nextPane);
			} catch (IOException e) {
				AlertHandler.showAlert(AlertType.ERROR, "Error Dialog",
						e.getMessage(),
						"");
			}
	}
	
	public void editStatementsCovered(CellEditEvent edittedCell) {
		TestCase testCaseSelected = table.getSelectionModel().getSelectedItem();
		testCaseSelected.setStatementsCovered(Integer.parseInt(edittedCell.getNewValue().toString()));
	}
	
	@FXML private AnchorPane rootPane;
	@FXML private Button findMaxBtn;
	@FXML private TableView<TestCase> table;
	@FXML private ImageView imageView;
}
