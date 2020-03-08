package com.models;

public class TestCase {
	private String testCase;
	private Integer statementsCovered;
	 
	public TestCase(String testCase, Integer statementsCovered) {
		this.testCase = testCase;
		this.statementsCovered = statementsCovered;
	}

	public String getTestCase() {
		return testCase;
	}

	public void setTestCase(String testCase) {
		this.testCase = testCase;
	}

	public Integer getStatementsCovered() {
		return statementsCovered;
	}

	public void setStatementsCovered(Integer statementsCovered) {
		this.statementsCovered = statementsCovered;
	}
	
	
}
