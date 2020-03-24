package com.utils;

import java.util.ArrayList;
import java.util.List;

import com.models.Individual;

import javafx.stage.Stage;

public class ProblemSingleton {

	private ProblemSingleton() {};
	
	public static ProblemSingleton getInstance() {
		return SingletonHelper.uniqueInstance;
	}
	
	private static class SingletonHelper{
		
		private static final ProblemSingleton uniqueInstance = new ProblemSingleton();
	}
	
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
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
	
	public int getGeneticAlgorithmFitness() {
		return geneticAlgorithmFitness;
	}

	public void setGeneticAlgorithmFitness(int geneticAlgorithmFitness) {
		this.geneticAlgorithmFitness = geneticAlgorithmFitness;
	}	

	public boolean isFromDataFile() {
		return isFromDataFile;
	}

	public void setFromDataFile(boolean isFromDataFile) {
		this.isFromDataFile = isFromDataFile;
	}	
	
	public List<Integer> getCodeStatementsList() {
		return codeStatementsList;
	}

	public void setCodeStatementsList(List<Integer> codeStatementsList) {
		this.codeStatementsList = codeStatementsList;
	}

	private List<Integer> codeStatementsList = new ArrayList<>();
	private boolean isFromDataFile = false;
	private Stage stage;
	private int testCases;
	private int codeStatements;
	private ArrayList<Integer> testCasesArray;
	private boolean gAFinished = false;
	private Individual fittest;
	private int geneticAlgorithmFitness;
}
