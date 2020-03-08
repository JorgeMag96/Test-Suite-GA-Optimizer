package com.utils;

public class ProblemSingleton {

	private ProblemSingleton() {};
	
	public static ProblemSingleton getInstance() {
		return SingletonHelper.uniqueInstance;
	}
	
	private static class SingletonHelper{
		
		private static final ProblemSingleton uniqueInstance = new ProblemSingleton();
	}
	
	public int getTestCases() {
		return testCases;
	}

	public void setTestCases(int testCases) {
		this.testCases = testCases;
	}

	public int getCodeStatements() {
		return codeStatements;
	}

	public void setCodeStatements(int codeStatements) {
		this.codeStatements = codeStatements;
	}
	
	public int[] getTestCasesArray() {
		return testCasesArray;
	}

	public void setTestCasesArray(int[] testCasesArray) {
		this.testCasesArray = testCasesArray;
	}

	private int testCases;
	private int codeStatements;
	private int[] testCasesArray;
}
