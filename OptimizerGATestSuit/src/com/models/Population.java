package com.models;

import java.util.ArrayList;

import com.models.Individual;

public class Population {

    int popSize = 100;
    Individual[] individuals;
    float fittest = 0;
    int nT = 0;
    int nS = 0;
    ArrayList<Integer> arrList;

    public Population(int nT, int nS, int nPopSize, ArrayList<Integer> arrState) {
        this.nT = nT;
        this.nS = nS;
        this.arrList = arrState;
        this.popSize = nPopSize;
        individuals = new Individual[popSize];
    }
    
    //Initialize population
    public void initializePopulation(int size) {
        for (int i = 0; i < individuals.length; i++) {
            individuals[i] = new Individual(nT, nS, arrList);
        }
    }

    //Get the fittest individual
    public Individual getFittest() {
        float maxFit = Integer.MIN_VALUE;
        int maxFitIndex = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (maxFit <= individuals[i].fitness) {
                maxFit = individuals[i].fitness;
                maxFitIndex = i;
            }
        }
        fittest = individuals[maxFitIndex].fitness;
        try {
            return (Individual) individuals[maxFitIndex].clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Get the second most fittest individual
    public Individual getSecondFittest() {
        int maxFit1 = 0;
        int maxFit2 = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (individuals[i].fitness > individuals[maxFit1].fitness) {
                maxFit2 = maxFit1;
                maxFit1 = i;
            } else if (individuals[i].fitness > individuals[maxFit2].fitness) {
                maxFit2 = i;
            }
        }
        try {
            return (Individual) individuals[maxFit2].clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Get index of least fittest individual
    public int getLeastFittestIndex() {
        float minFitVal = Float.MAX_VALUE;
        int minFitIndex = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (minFitVal >= individuals[i].fitness) {
                minFitVal = individuals[i].fitness;
                minFitIndex = i;
            }
        }
        return minFitIndex;
    }

    //Calculate fitness of each individual
    public void calculateFitness() {

        for (int i = 0; i < individuals.length; i++) {
            individuals[i].calcFitness();
        }
        getFittest();
    }

}
