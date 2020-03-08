package com.controller;

import java.io.File;

import com.models.TestCase;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ThirdWindowController {

	
	@SuppressWarnings("unchecked")
	@FXML
	public void initialize() {
		File file = new File("resources/images/thirdScreenImage.png");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        
        TableColumn testCaseCol = new TableColumn<>("Test Case");
        testCaseCol.setCellValueFactory(new PropertyValueFactory<TestCase, String>("testCase"));
        
        TableColumn statementsCoveredCol = new TableColumn<>("Statements Covered");
        statementsCoveredCol.setCellValueFactory(new PropertyValueFactory<TestCase, Integer>("statementsCovered"));
        statementsCoveredCol.setEditable(true);
        
        table.getColumns().addAll(testCaseCol, statementsCoveredCol);
        table.setItems(FXCollections.observableArrayList(
        	      new TestCase("Total number of statements covered by test case 1", 0),
        	      new TestCase("Total number of statements covered by test case 2", 0)
        	    ));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	public AnchorPane rootPane;
	public Button findMaxBtn;
	public TableView<TestCase> table;
	public ImageView imageView;
}
