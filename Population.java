/* The Population class stores all of the Individual Objects within an array. */

import java.util.Random; 
import java.util.Arrays;

public class Population {

    // list of current population
    Individual[] individualList;  
    int popSize;

    // constructor method for offspring populations  
    public Population(Individual[] population) {
        individualList = population;
        popSize = population.length;
        sortPop();
    }

    // constructor method for initial population 
    public Population(int size, String target) {
        generateInitialPop(size, target);
        popSize = size;
        sortPop();
    }
    
    // generates 100 random individuals for starting population
    public void generateInitialPop(int size, String target) {
        // for each individual in poplation of size (size)
        // make a new indiivudal with random characters from ASCII table 
        final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
        final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String DIGITS = "0123456789";
        final String SYMBOLS = "!@#$%^&*()_+=-[]{}|;’:\",./<>?";
        final String SPACE = " ";
        final String ALL_ASCII = LOWER_CASE + UPPER_CASE + DIGITS + SYMBOLS + SPACE; 
        
        int i, j; 
        int lenTarget = target.length();
        Character nextChar; 
        Individual initialPopulation[] = new Individual[size]; 
        Random randomGenerator = new Random();  

        // create the number of individuals in the population (size)
        for (i = 0; i < size; i++) {
            String newIndividual = "";
            
            // create one string of random characters of the taget length's size
            for (j = 0; j < lenTarget; j++) {
                int index = randomGenerator.nextInt(size); 
                nextChar = ALL_ASCII.charAt(index); 
                newIndividual += nextChar; 
            }

            // create the new individual 
            Individual individual = new Individual(target, newIndividual);

            // add the new individual to the population 
            initialPopulation[i] = individual; 
        } 

        // store the individuals in the Population object 
        this.individualList = initialPopulation;
    }

    public Individual[] sortPop() {
        // sort individuals in population based on fitness
        Arrays.sort(this.individualList, (ind1, ind2) -> Integer.valueOf(ind1.fitness).compareTo(Integer.valueOf(ind2.fitness)));

        return this.individualList; 
    }

    public Integer rankSumPop() {
        // returns the sum of all of the Individual 
        // fitnesses within the individualList 

        int i; 
        int sum = 0; 

        for (i = 0; i < this.individualList.length; i++) {
            sum += this.individualList[i].fitness; 
        }   

        return sum; 
    }

    // return individual with greatest fitness
    public Individual getFittestIndv() {
        this.sortPop();
        int mostFitIndex = popSize - 1;
        return individualList[mostFitIndex];
    }
}