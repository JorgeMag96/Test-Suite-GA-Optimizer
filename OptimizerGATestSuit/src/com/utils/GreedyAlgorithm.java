package com.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.models.Individual;

import javafx.scene.control.Alert;

public class GreedyAlgorithm {
	
	public Individual getGreedyIndividual() {
		
		int[] selectedGenes = ProblemSingleton.getInstance().getfittest().getGenes();
		
		List<Integer> testCaseArray = ProblemSingleton.getInstance().getTestCasesArray();
		
		List<Pair> pairs = new ArrayList<>();
		for(int i = 0; i < selectedGenes.length; i++) {
			pairs.add(new Pair(i, selectedGenes[i] == 1 , testCaseArray.get(i)));
		}
		
		Collections.sort(pairs);
		
		int optimal = ProblemSingleton.getInstance().getCodeStatements();
		Individual greedyChoice = new Individual(new int[testCaseArray.size()]);
		
		for(Pair p : pairs) {
			if(greedyChoice.getFitness() < optimal) {
				if(p.hasGene) {
					greedyChoice.getGenes()[p.getIndex()] = 1;
				}
			}
		}
		
		try {
			FileWriter myWriter = new FileWriter("results//GreedyResults.txt");
			myWriter.write("Test suite with maximum statements covered and minimum test cases = "+greedyChoice.toString()+"\n");
			myWriter.write("Statements covered = "+greedyChoice.getFitness()+"\n");
			myWriter.flush();
			myWriter.close();
		}
		catch (IOException e) {
			AlertHandler.showAlert(Alert.AlertType.ERROR, "Error while writting to file", e.getCause().toString(), e.getMessage());
		}
		
		return greedyChoice;
	}
	
	class Pair implements Comparable<Pair>{
		private int index;
		private int value;
		private boolean hasGene;
		
		public Pair(int index, boolean hasGene, int value) {
			this.index = index;
			this.hasGene = hasGene;
			this.value = value;
		}
		
		public int getIndex() {
			return index;
		}

		public int getValue() {
			return value;
		}
		
		public boolean hasGene() {
			return hasGene;
		}

		@Override
		public int compareTo(Pair i) {
			
			if(getValue() < i.getValue()) {
				return 1;
			}
			else if(getValue() > i.getValue()) {
				return -1;
			}
			
			return 0;
		}
	}
}
