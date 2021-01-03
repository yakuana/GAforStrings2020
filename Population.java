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
    public void generateInitialPop(int popSize, String target) {
        // for each individual in poplation of size (size)
        // make a new indiivudal with random characters from ASCII table 
        final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
        final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String DIGITS = "0123456789";
        final String SYMBOLS = "!@#$%^&*()_+=-[]{}|;’:\",./<>?";
        final String SPACE = " ";
        final String ALL_ASCII = LOWER_CASE + UPPER_CASE + DIGITS + SYMBOLS + SPACE; 
         
        int lenTarget = target.length();
        Character nextChar; 
        Individual[] initialPopulation = new Individual[popSize]; 
        Random randomGenerator = new Random();  

        // create the number of individuals in the population (size)
        for (int i = 0; i < popSize - 1; i++) {
            String newIndividual = "";
            
            // create one string of random characters of the target length's size
            for (int j = 0; j < lenTarget - 1; j++) {
                int index = randomGenerator.nextInt(ALL_ASCII.length() - 1); 
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


    public void sortPop() {
        // sort individuals in population based on fitness
        //Arrays.sort(individualList, Comparator.comparing(Individual :: getFitness));   
        //selectionSort(); 

        Arrays.sort(individualList, Individual.IndvFitnessComparator);

    }

    // public void swap(int i, int j) {
    //     Individual temp = individualList[i];
    //     individualList[i] = individualList[j];
    //     individualList[j] = temp;
    // }

    // public void selectionSort() {
    //     for (int unsorted = individualList.length; unsorted > 0; unsorted--) {
            
    //         int largest = 0; // index of largest value this pass
    //         for (int i = 1; i < unsorted; i++) {
    //             if (individualList[largest].fitness < individualList[i].fitness {
    //                 largest = i; // found a new index of largest value
    //             }
    //         }
    //         swap(largest, unsorted - 1); // put the largest value into position 
    //     }
    // }


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