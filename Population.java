//this class will hold all idnvidiuals in population in an array

import java.util.Random; 
import java.util.Arrays;

public class Population {

    Individual[] individualList;  //list of this population
    

    //Constructor method
    public Population(Individual[] population) {
        individualList = population;
        sortPop();
    }

    public Population(int size, String target) {
        generateInitialPop(size, target);
        sortPop();
    }
    
    //generates 100 random individuals for starting population
    public void generateInitialPop(int size, String target) {
        // for each individual in poplation of size (size)
        // make a new indiivudal with random characters from ASCII table 
        final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
        final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String DIGITS = "0123456789";
        final String SYMBOLS = "!@#$%^&*()_+=-[]{}|;’:\",./<>?";
        final String SPACE = " ";
        final String ALL_ASCII = LOWER_CASE + UPPER_CASE + DIGITS + SYMBOLS + SPACE; 
        final String TARGET = "I think this is a reasonable medium sized string!!";
        
        int i, j; 
        int lenTarget = target.length();
        Character nextChar; 
        Individual initialPopulation[] = new Individual[size]; 
        Random randomGenerator = new Random();  

        for (i = 0; i < size; i++) {
            String newIndividual = "";
            
            for (j = 0; j < lenTarget; j++) {
                int index = randomGenerator.nextInt(size); 
                nextChar = ALL_ASCII.charAt(index); 
                newIndividual += nextChar; 
            }

            Individual individual = new Individual(target, newIndividual);
            initialPopulation[i] = individual; 
        } 

        this.individualList = initialPopulation;
    }

    public Individual[] sortPop() {
        // sort individuals in population based on fitness
        Arrays.sort(this.individualList, (ind1, ind2) -> Integer.valueOf(ind1.fitness).compareTo(Integer.valueOf(ind2.fitness)));

        return this.individualList; 
    }

    // return individual with greatest fitness
    public Individual getFittestIndv() {
        this.sortPop();
        return individualList[0];
    }
    
    //one for adding a new individual(child) to pop

   

    

    //choose selection type to make breeding pool
    //can either select whole breeding pool then do recombination, or select pairs at a time

}