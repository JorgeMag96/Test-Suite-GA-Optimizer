package com.models.test;

import java.util.Arrays;

public class Individual {

	private int[] genes;
	
	public Individual(int[] genes) {
		this.genes = genes;
	}
	
	public int[] getGenes() {
		return genes;
	}

	@Override
	public String toString() {
		return "[genes=" + Arrays.toString(genes) + "]";
	}
	
}
