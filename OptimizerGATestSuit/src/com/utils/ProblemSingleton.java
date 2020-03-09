package com.utils;

import java.util.ArrayList;

import com.models.Individual;

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
	
	public ArrayList<Integer> getTestCasesArray() {
		return testCasesArray;
	}

	public void setTestCasesArray(ArrayList<Integer> testCasesArray) {
		this.testCasesArray = testCasesArray;
	}
	
	public void setGAFinished() {
		gAFinished = true;
	}
	
	public boolean isGAFinished() {
		return gAFinished;
	}
	
	public void setFittest(Individual fittest) {
		this.fittest = fittest;
	}
	
	public Individual getfittest() {
		return fittest;
	}

	private int testCases;
	private int codeStatements;
	private ArrayList<Integer> testCasesArray;
	private boolean gAFinished = false;
	private Individual fittest;
}
