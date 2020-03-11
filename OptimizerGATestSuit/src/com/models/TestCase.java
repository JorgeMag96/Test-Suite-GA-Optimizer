package com.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TestCase {
    
    private final StringProperty testCase = new SimpleStringProperty();;
    private final StringProperty statementsCovered = new SimpleStringProperty();;
	
	public TestCase(String testCase, String statementsCovered) {
		setTestCase(testCase);
		setStatementsCovered(statementsCovered);
	}

	public String getTestCase() {
		return this.testCaseProperty().get();
	}

	 public final StringProperty testCaseProperty() {
         return this.testCase;
     }
	 
	public final void setTestCase(final java.lang.String testCase) {
        this.testCaseProperty().set(testCase);
    }

	public String getStatementsCovered() {
		return this.statementsCoveredProperty().get();
	}

	 public final StringProperty statementsCoveredProperty() {
         return this.statementsCovered;
     }
	 
	public final void setStatementsCovered(final java.lang.String statementsCovered) {
        this.statementsCoveredProperty().set(statementsCovered);
    }

}
