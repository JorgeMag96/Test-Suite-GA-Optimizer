package com.models;

import java.util.ArrayList;
import java.util.Arrays;

import com.utils.ProblemSingleton;

public class Individual implements Comparable<Individual>{

	private int[] genes;
	
	public Individual(int[] genes) {
		this.genes = genes;
	}
	
	public int[] getGenes() {
		return genes;
	}
	
	public int getFitness() {
		ArrayList<Integer> testCasesArray = ProblemSingleton.getInstance().getTestCasesArray();
		int fitness = 0;
		for(int i = 0; i < genes.length; i++) {
			fitness += genes[i] * testCasesArray.get(i);
		}
		return fitness;
	}
	
	@Override
	public String toString() {
		return " genes=" + Arrays.toString(genes);
	}

	@Override
	public int compareTo(Individual i) {
		
		if(getFitness() < i.getFitness()) {
			return 1;
		}
		else if(getFitness() > i.getFitness()) {
			return -1;
		}
		
		return 0;
	}
	
}
