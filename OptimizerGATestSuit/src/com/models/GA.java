package com.models;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.models.Individual;
import com.models.Population;

public class GA {

    Individual fittest;
    Individual secondFittest;
    int generationCount = 0;
    int nT = 0;
    int nS = 0;
    int nPopSize = 100;
    ArrayList<Integer> arrState;
    Population population;

    public GA(int nTest, int nState, int nPopSize, ArrayList<Integer> arrList) {
        this.nT = nTest;
        this.nS = nState;
        this.nPopSize = nPopSize;
        arrState = arrList;
        population = new Population(nT, nS, nPopSize, arrState);
    }
    
    public ArrayList<Integer> findSolution() throws IOException {

        Random rn = new Random();

        //Initialize population
        population.initializePopulation(100);

        //Calculate fitness of each individual
        population.calculateFitness();
        
        //============================================///
         
        FileWriter myWriter = new FileWriter("Resualt.txt");
        
        //----------------------------------------------//
        
      
      myWriter.write("\nGeneration: " + generationCount + " Fittest: " + population.fittest);
      //System.out.println("Generation1: " + generationCount + " Fittest: " + population.fittest);
      
        

        //While population gets an individual with maximum fitness
        //while (population.fittest < 5) {
        while (generationCount < 250) {
            ++generationCount;

            //Do selection
            selection();

            //Do crossover
            crossover();

            //Do mutation under a random probability
            if (rn.nextInt()%7 < 5) {
                mutation();
            }

            //Add fittest offspring to population
            addFittestOffspring();

            //Calculate new fitness value
            population.calculateFitness();
            
          myWriter.write("\nGeneration: " + generationCount + " Fittest: " + population.fittest);
         // System.out.println("Generation2: " + generationCount + " Fittest: " + population.fittest);
        }

        myWriter.write("\nSolution found in generation " + generationCount);
       // System.out.println("\nSolution3 found in generation " + generationCount);

       myWriter.write("\nFitness: "+population.getFittest().fitness);
     //  System.out.println("Fitness4: "+population.getFittest().fitness);
       
       
       //myWriter.write("\nGenes : ");
       myWriter.close();
       //System.out.print("Genes: ");
                   
                   
        

        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < nT * nS; i++) {
            result.add(population.getFittest().genes[i]);
        }

        System.out.println(result);
        return result;
    }

    //Selection
    void selection() {

        //Select the most fittest individual
        fittest = population.getFittest();

        //Select the second most fittest individual
        secondFittest = population.getSecondFittest();
    }

    //Crossover
    void crossover() {
        Random rn = new Random();

        //Select a random crossover point
        int crossOverPoint = rn.nextInt(population.individuals[0].geneLength);

        //Swap values among parents
        for (int i = 0; i < crossOverPoint; i++) {
            int temp = fittest.genes[i];
            fittest.genes[i] = secondFittest.genes[i];
            secondFittest.genes[i] = temp;

        }

    }

    //Mutation
    void mutation() {
        Random rn = new Random();

        //Select a random mutation point
        int mutationPoint = rn.nextInt(population.individuals[0].geneLength);

        //Flip values at the mutation point
        if (fittest.genes[mutationPoint] == 0) {
            fittest.genes[mutationPoint] = 1;
        } else {
            fittest.genes[mutationPoint] = 0;
        }

        mutationPoint = rn.nextInt(population.individuals[0].geneLength);

        if (secondFittest.genes[mutationPoint] == 0) {
            secondFittest.genes[mutationPoint] = 1;
        } else {
            secondFittest.genes[mutationPoint] = 0;
        }
    }
    
    

    //Get fittest offspring
    Individual getFittestOffspring() {
        if (fittest.fitness > secondFittest.fitness) {
            return fittest;
        }
        return secondFittest;
    }


    //Replace least fittest individual from most fittest offspring
    void addFittestOffspring() {

        //Update fitness values of offspring
        fittest.calcFitness();
        secondFittest.calcFitness();

        //Get index of least fit individual
        int leastFittestIndex = population.getLeastFittestIndex();

        //Replace least fittest individual from most fittest offspring
        population.individuals[leastFittestIndex] = getFittestOffspring();
    }
   
}
