package com.models.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.utils.ProblemSingleton;

public class GeneticAlgorithm {

	private List<Individual> population;
	private int iterations;
	private ArrayList<Integer> testCasesArray;
	
	public GeneticAlgorithm(int iterations) {
		ProblemSingleton singleton = ProblemSingleton.getInstance();
		this.testCasesArray = singleton.getTestCasesArray();
		population = initializePopulation(singleton.getTestCases(), 100);
		this.iterations = iterations;
	}
	
	private List<Individual> initializePopulation(int genesSize, int populationSize){
		Random ran = new Random();
		List<Individual> generatedPopulation = new ArrayList<>();
		for(int i = 0; i < populationSize; i++) {
			int[] genes = new int[genesSize];
			for(int j = 0; j < genesSize; j++) {
				genes[j] = ran.nextInt()%2 == 0 ? 1 : 0;
			}
			generatedPopulation.add(new Individual(genes));
		}
		
		return generatedPopulation;
	}
	
	public void startAlgorithm() {
		boolean maxFitFound = false;
		
		System.out.println("Print initial population...");
		for (int i = 0; i < population.size(); i++) {
			System.out.println("Individual "+(i+1)+" "+population.get(i).toString());
		}
		
		System.out.println("Test cases array = "+testCasesArray.toString());
		System.out.println("Iterations = "+iterations);
		System.out.println("Max fit found = "+maxFitFound);
		
		/*
		while(iterations > 0) {
			selection();
			crossOver();
			mutate();
			iterations--;
			if(maxFitFound) break;
		}
		*/
		ProblemSingleton.getInstance().setGAFinished();
	}
	
	private void selection() {
		
	}
	
	private void crossOver() {
		
	}
	
	private void mutate() {
		
	}
	
	public List<Individual> getPopulation(){
		return population;
	}
}
