package com.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.models.Individual;

import javafx.scene.control.Alert;

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
			FileInputStream fis = new FileInputStream("results//ExcelResults.xlsx");
			Workbook wb = WorkbookFactory.create(fis);
			
			if(wb.getSheet("Genetic Population") == null) {
				wb.createSheet("Genetic Population");
			}
			else {
				wb.removeSheetAt(wb.getSheetIndex(wb.getSheet("Genetic Population")));
				wb.createSheet("Genetic Population");
			}
			
			Sheet sheet = wb.getSheet("Genetic Population");
			
			FileWriter myWriter = new FileWriter("results//GeneticAlgorithmPopulation.txt");
			String header = "Test cases array = "+testCasesArray.toString();
			Row row = sheet.createRow(1);
			Cell cell = row.createCell(0);
			cell.setCellValue("Test cases array = ");
			cell = row.createCell(1);
			cell.setCellValue(testCasesArray.toString());
			row = sheet.createRow(3);
			cell = row.createCell(0);
			cell.setCellValue("Genes");
			cell = row.createCell(1);
			cell.setCellValue("Fitness");
			
			myWriter.write(header+"\n\n");
			for(int i = 0; i < population.size(); i++) {
				try {
					Row outputRow = sheet.createRow(i+4);
					
					Cell outputCell = outputRow.createCell(0);
					outputCell.setCellValue(population.get(i).toString().replaceFirst("Genes=", ""));
					
					outputCell = outputRow.createCell(1);
					outputCell.setCellValue(population.get(i).getFitness());
					
					myWriter.write(population.get(i).toString()+"\t Fitness = "+population.get(i).getFitness()+"\n");
				} 
				catch (IOException e1) {
					AlertHandler.showAlert(Alert.AlertType.ERROR, "Error while writting to file", e1.getCause().toString(), e1.getMessage());
				}
			}
			
			myWriter.flush();
			myWriter.close();
			FileOutputStream fos = new FileOutputStream("results//ExcelResults.xlsx");
			wb.write(fos);
			fos.flush();
			fos.close();
			fis.close();
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
	
	public List<Individual> getPopulation(){
		return population;
	}
}
