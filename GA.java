
import java.util.Random;

public class GA {

    // target string
    final String TARGET = "I think this is a reasonable medium sized string!!";

    //Individual mostFit;     //most fit individual OVERALL in genetic algorithm
    int popSize;            //population size 
    int maxGens;            //max num of generations before algo stops
    int printerval;         //interval of how many generation created between prints
    float crossoverProb;    //probability that we will crossover
    float mutationProb;     //probability that we will perform mutation
    String selType;         //type of selection we will do
    

    //Constructor method
    public GA(int populationSize, String selectionType, float crossoverProbability, float mutationProbability, int maxGenerations, int printInterval) {
        popSize = populationSize;
        selType = selectionType;
        crossoverProb = crossoverProbability;
        mutationProb = mutationProbability;
        maxGens = maxGenerations;
        printerval = printInterval;
    }

    
    //return an individual selected from population using rank selection  
    public Individual rankSelection(Population currentPop) { 
        //generates random double to select from individuals with different probability to be chosen
        //an individual's probability to be chosen = that individuals rank/sum of all individuals' rank
        
        currentPop.sortPop();           //make sure in order from least to most fitness
        Individual parent = currentPop.getFittestIndv();        //make sure parent is initialized, will be changed later
        int probabilitySelected;
        int[] rankValues = new int[popSize];
        int rankSum = 0;

        for (int i = 0; i <= popSize - 1; i++) {            //i represents index of indiviudal in currentPop. starting from least fit to most fit
            rankValues[i] = i + 1;
            rankSum += i + 1;
        };

        //make random int within rankSum to determine probability
        Random randRS = new Random();
        probabilitySelected = randRS.nextInt(rankSum);
        int probabilitySum = 0;

        //starting from least fit to most fit
        //for each individual in population
        for (int i = 0; i <= popSize - 1; i++) {        
            float indvProbability = rankValues[i];
            probabilitySum += indvProbability;
            if (probabilitySelected <= probabilitySum) {
                parent = currentPop.individualList[i];
                i = popSize;
            } 
        }
        //System.out.println(parent.getIndv());
        return parent; 
    }
    
    // return an individual from the population using tournament selection  
    public Individual tournamentSelection(Population currentPop) {
        //get two random individuals from currentPop, chose one with highest fitness to be winningParent
        Individual winningParent;
        Random randTS = new Random();

        int parent1Index = randTS.nextInt(popSize - 1);
        int parent2Index = randTS.nextInt(popSize - 1);
        if (parent1Index == parent2Index) {
            parent2Index += 1;
        }
        
        int parent1Fitness = currentPop.individualList[parent1Index].getFitness();
        int parent2Fitness = currentPop.individualList[parent2Index].getFitness();
        
        if (parent1Fitness >= parent2Fitness) {
            winningParent = currentPop.individualList[parent1Index];
        } else {      //aka if (parent2Fitness > parent1Fitness)
            winningParent = currentPop.individualList[parent2Index];
        }
        return winningParent; 
    }

    // return an individual from the population using Boltzmann selection 
    public Individual boltzmannSelection(Population currentPop) {
        //generates random double to select from individuals with different probability to be chosen
        //an individual's probability to be chosen = e^that individuals fitness/sum of all individuals' e^fitness
        Individual parent = currentPop.individualList[0];
        double probabilitySelected;
        double eVar = 2.71828;
        double[] eFitValues = new double[popSize];
        double eFitSum = 0;

        for (int i = 0; i <= popSize - 2; i++) {            //i represents index of indiviudal in currentPop. starting from least fit to most fit
            Individual currentIndv = currentPop.individualList[i];
            int indvFitness = currentIndv.getFitness();

            double eToFitness = Math.pow(eVar, indvFitness);
            eFitValues[i] = eToFitness;
            eFitSum += eToFitness;
        }

        //make random float under 1 to determine probability
        Random randBS = new Random();
        probabilitySelected = randBS.nextDouble();
        double probabilitySum = 0;

        //starting from least fit to most fit
        for (int i = 0; i <= popSize - 1; i++) {
            double indvProbability = (eFitValues[i] / eFitSum);
            probabilitySum += indvProbability;
            if (probabilitySelected <= probabilitySum) {
                parent = currentPop.individualList[i];
                i = popSize;
            } 
        }
        return parent;
    }
    
    //gives you char in alphabet (for mutation of children strings)
    public char getChar(int index) {
        String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
        String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String DIGITS = "0123456789";
        String SYMBOLS = "!@#$%^&*()_+=-[]{}|;’:\",./<>?";
        String SPACE = " ";
        String ALL_ASCII = LOWER_CASE + UPPER_CASE + DIGITS + SYMBOLS + SPACE; 

        char myChar = ALL_ASCII.charAt(index);
        return myChar;
    }

    //mutation method
    public Individual mutation(Individual indv) {
        String indvString = indv.getIndv();

        for (int i = 0; i <= indvString.length() - 1; i+=1) {       //for every character in indvString
            char oldChar = indvString.charAt(i);
            Random rand = new Random();
            float randProb = rand.nextFloat();              //makes random float between 0 and 1
            if (randProb <= mutationProb) {                 //if true, do mutation
                //change that char
                int randInt = rand.nextInt(91);         //92 = ALPAHBET LENGTH
                char newChar = getChar(randInt);
                if (newChar == oldChar) {                   //makes sure you are actually changing char
                    randInt += 1;
                    newChar = getChar(randInt);
                }
                String newCharString = String.valueOf(newChar);
                indvString = indvString.substring(0, i) + newCharString + indvString.substring(i+1, indvString.length());
            }
        }
        Individual mutatedIndv = new Individual(TARGET, indvString);
        return mutatedIndv;
    }

    //recomibination method, will take in 2 parent indvs and return 2 children each call
    public Individual[] recombination(Individual p1, Individual p2) {
        Individual[] children = new Individual[2]; 

        //recombo part
        //use probability to determine if we do recombo:
        Random rand = new Random();
        float randProb = rand.nextFloat();  //will make random float between 0 and 1
        if (randProb <= crossoverProb) {     //means we are doing rombination
            int randInt = rand.nextInt(p1.getIndv().length());   //will be used to determine where to do crossover
            //make sure to make copy and not change the parent itself!!
             
            String p1Str = p1.getIndv();
            String p2Str = p2.getIndv();
            String child1Str = p1Str.substring(0, randInt) + p2Str.substring(randInt);
            String child2Str = p2Str.substring(0, randInt) + p1Str.substring(randInt);

            Individual child1 = new Individual(TARGET, child1Str);
            Individual child2 = new Individual(TARGET, child2Str);

            children[0] = child1;
            children[1] = child2;
        } else {                        //we aren't doing recombo, so children = parents
            children[0] = p1;  
            children[1] = p2;
        }
        return children;
    }

    public Population doGeneration(Population currentPop) {
        Individual[] offspringList = new Individual[popSize]; 

        for(int i = 0; i < popSize; i+=2) {
            Individual p1;
            Individual p2;
            
            if (selType.equals("ts")) {
                p1 = tournamentSelection(currentPop);
                p2 = tournamentSelection(currentPop);
            } else if (selType.equals("bs")) {
                p1 = boltzmannSelection(currentPop);
                p2 = boltzmannSelection(currentPop);
            } else if (selType.equals("rs")) {
                p1 = rankSelection(currentPop);
                p2 = rankSelection(currentPop);
            } else {
                System.out.print("There was an error! with selection!!");
                break;
            }
            while(p1.getIndv().equals(p2.getIndv())) {
                if (selType.equals("ts")) {
                    p2 = tournamentSelection(currentPop);
                } else if (selType.equals("bs")) {
                    p2 = boltzmannSelection(currentPop);
                } else if (selType.equals("rs")) {
                    p2 = rankSelection(currentPop);
                }
            }
            Individual[] children = recombination(p1, p2);
            Individual child1 = children[0];
            Individual child2 = children[1];

            Individual newChild1 = mutation(child1); 
            Individual newChild2 = mutation(child2);

            offspringList[i] = newChild1;
            offspringList[i+1] = newChild2;
        }
        Population offspring = new Population(offspringList);
        return offspring;
    }

    public void generations(Population population) {
        //run ga through generations until it hits maxGen OR the individual of best fit for that generation is equal to the target 
        //print out data every time generation count hits printerval

        Population currentPop = population;  
        currentPop.sortPop();

        Individual mostFit = currentPop.getFittestIndv();             //most fit individual OVERALL in genetic algorithm
        Individual fittestInPop = currentPop.getFittestIndv();        //most fit individual in currentPop
        
        int genCount = 0;       //counts all generations done
        int genMostFit = 0;     //counts generation that has the mostFit individual so far

        // create maxGens (max generation) amount of populations
        while (genCount <= maxGens - 1) {
            Population offspring = doGeneration(currentPop); 
            genCount += 1;
            fittestInPop = offspring.getFittestIndv();

            if (fittestInPop.fitness > mostFit.fitness) {
                mostFit = fittestInPop; 
                genMostFit = genCount;
            }
            if (fittestInPop.getIndv().equals(TARGET)) {
                System.out.println("Hit the Target!!");
                System.out.println(String.format("Target:               %s", TARGET));
                System.out.println(String.format("Best Individual:       %s", mostFit.indvString));
                System.out.println(String.format("Generation Found:      %d", genMostFit));
                System.out.println(String.format("Score: %d/ %d = %f%s", mostFit.fitness, TARGET.length(), ((float) mostFit.fitness/TARGET.length() * 100), "%"));
                break;
            }
            // if at interval, print the fittest individual in that currentPop
            if ((genCount % printerval) == 0) {
                System.out.println(String.format("%d ( %d/ %d):  %s", genCount, mostFit.fitness, TARGET.length(), mostFit.getIndv()));
            }
            //if we reach max generation, print missed target and other info
            if (genCount == maxGens) {
                System.out.println("Missed the target...");
                System.out.println(String.format("Target:                %s", TARGET));
                System.out.println(String.format("Best Individual:       %s", mostFit.indvString));
                System.out.println(String.format("Generation Found:      %d", genMostFit));
                System.out.println(String.format("Score: %d/ %d = %f%s", mostFit.fitness, TARGET.length(), ((float) mostFit.fitness/TARGET.length() * 100), "%"));
            }
            currentPop = offspring; 
        }
    }

    public static void main(String[] args) {
        int popSize = Integer.parseInt(args[0]);            // population size 
        String selType = args[1];                           // type of selection
        float crossoverProb = Float.parseFloat(args[2]);    // probability of crossover
        float mutationProb = Float.parseFloat(args[3]);     // probability of mutation
        int maxGens = Integer.parseInt(args[4]);            // max num of generations 
        int printInt = Integer.parseInt(args[5]);           // interval of generation  

        GA algorithm = new GA(popSize, selType, crossoverProb, mutationProb, maxGens, printInt); 
         
        //initaliazing first population and calling the genetic algorithm function that will do the actual algorithm
        Population initialPop = new Population(100, algorithm.TARGET);

        algorithm.generations(initialPop); 
    }
}
