package com.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.models.Individual;

import javafx.scene.control.Alert;

public class GeneticAlgorithm {

	private List<Individual> population;
	private int iterations;
	private ArrayList<Integer> testCasesArray;
	private int optimalFitness;
	
	public GeneticAlgorithm(int iterations) {
		ProblemSingleton singleton = ProblemSingleton.getInstance();
		this.testCasesArray = singleton.getTestCasesArray();
		population = initializePopulation(singleton.getTestCases(), 100);
		this.iterations = iterations;
		this.optimalFitness = singleton.getCodeStatements();
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
		
		while(iterations > 0) {
			
			//if(optimalAchieved()) break;
			Collections.sort(population);
			
			Individual i1 = new Individual(population.get(0).getGenes());
			Individual i2 = new Individual(population.get(1).getGenes());
			
			crossOver(i1, i2);
			
			if (new Random().nextInt(100) < 10) {
				mutate(i1, i2);
	        }
			
			population.remove(population.size()-1);
			population.add(population.size()-1, newFitter(i1,i2));
			
			iterations--;
		}
		
		try {
			FileWriter myWriter = new FileWriter("results//GeneticAlgorithmPopulation.txt");
			myWriter.write("Test cases array = "+testCasesArray.toString()+"\n\n");
			population.forEach((e)->{
				try {
					myWriter.write(e.toString()+"\t Fitness = "+e.getFitness()+"\n");
				} 
				catch (IOException e1) {
					AlertHandler.showAlert(Alert.AlertType.ERROR, "Error while writting to file", e1.getCause().toString(), e1.getMessage());
				}
			});
			myWriter.flush();
			myWriter.close();
		} 
		catch (IOException e1) {
			AlertHandler.showAlert(Alert.AlertType.ERROR, "Error while writting to file", e1.getCause().toString(), e1.getMessage());
		}
		
		
		ProblemSingleton.getInstance().setFittest(population.get(0));
		ProblemSingleton.getInstance().setGAFinished();
	}
	
	private void crossOver(Individual i1, Individual i2) {
		
		Random rn = new Random();
        //Select a random crossover point
        int crossOverPoint = rn.nextInt(testCasesArray.size());
        
		for(int i = 0; i < crossOverPoint; i++) {
			int temp = i1.getGenes()[i];
			i1.getGenes()[i] = i2.getGenes()[i];
			i2.getGenes()[i] = temp;
		}
	}
	
	private void mutate(Individual i1, Individual i2) {
		Random rn = new Random();

        //Select a random mutation point
        int mutationPoint = rn.nextInt(testCasesArray.size());

        //Flip values at the mutation point
        if (i1.getGenes()[mutationPoint] == 0) {
        	i1.getGenes()[mutationPoint] = 1;
        } else {
        	i1.getGenes()[mutationPoint] = 0;
        }
        
        mutationPoint = rn.nextInt(testCasesArray.size());

        if (i2.getGenes()[mutationPoint] == 0) {
        	i2.getGenes()[mutationPoint] = 1;
        } else {
        	i2.getGenes()[mutationPoint] = 0;
        }
       
	}
	
	private Individual newFitter(Individual i1, Individual i2) {
		
		if(i1.getFitness() > i2.getFitness()) {
			return i1;
		}
		else {
			return i2;
		}
	}
	
	private boolean optimalAchieved() {
		
		if(population.get(1).getFitness() > optimalFitness) {
			return true;
		}
		
		return false;
		
	}
	
	public List<Individual> getPopulation(){
		return population;
	}
}
