package com.models;

import java.util.ArrayList;
import java.util.Random;

public class Individual implements Cloneable{

    float fitness = 0;
    int nS = 0;
    int nT = 0;
    ArrayList<Integer> arrList;
    int[] genes;
    int geneLength = 0;

    public Individual(int nT, int nS, ArrayList<Integer> arrList) {
        this.nT = nT;
        this.nS = nS;
        this.arrList = arrList;
        geneLength = arrList.size();
        genes = new int[geneLength];
        
        Random rn = new Random();

        //Set genes randomly for each individual
        for (int i = 0; i < genes.length; i++) {
            genes[i] = Math.abs(rn.nextInt() % 2);
        }

        fitness = 0;
    }

    //Calculate fitness
    public void calcFitness() {

        fitness = 0;
        for (int j = 0; j < nT; j++) {
            float sum = 0;
            for (int i = 0; i < nS; i++) {
                if (genes[nT * j + i] == 1) {
                    ++sum;
                }
            }
            fitness += (sum / arrList.get(j));
        }
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        Individual individual = (Individual)super.clone();
        individual.genes = new int[geneLength];
        for(int i = 0; i < individual.genes.length; i++){
            individual.genes[i] = this.genes[i];
        }
        return individual;
    }
}