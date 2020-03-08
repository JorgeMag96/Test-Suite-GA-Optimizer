module OptimizerGATestSuit {
	exports com.launcher;
	exports com.controller;
	exports com.views;
	exports com.models;
	exports com.utils;
	
	opens com.controller to javafx.fxml;
	
	requires javafx.base;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires transitive javafx.controls;
}