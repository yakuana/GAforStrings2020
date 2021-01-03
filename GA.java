import java.util.Random;

public class GA {

    // target string
    final String TARGET = "I think this is a reasonable medium sized string!!";

    Individual mostFit;        //most fit individual OVERALL in genetic algorithm
    int popSize; 
    int maxGens;            //max num of generations before algo stops
    int printerval;           //interval of how many generation created between prints
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

    

    
    //work in progess, will eventually return an individual selected from pop via rs
    public Individual rankSelection(Population currentPop) {
        int i; 
        int rankSum = 0; 
        float greatestProb = 0; 
        float randProb; 
        Random random = new Random(); 

        // sort the population from 
        // least to greatest rank 
        currentPop.sortPop(); 

        // get the sum of the population 
        rankSum = currentPop.rankSumPop(); 
        
        greatestProb = currentPop.getFittestIndv().probability / rankSum;
        
        // get random probability 
        randProb = random.nextFloat() * greatestProb; 
        
        // assign Individuals' probability to rank(i)/sum
        for (i = 0; i < currentPop.individualList.length; i++) {
            
            // get the probability of the Individual 
            currentPop.individualList[i].probability =  currentPop.individualList[i].fitness / rankSum; 

            // return the individual if it has a probability
            // greater than or equal to the randomly generated probability 
            if (currentPop.individualList[i].probability >= randProb) {
                return currentPop.individualList[i];
            }
        }
        
        // if individual has not been chosen, 
        // use the individual with the greatest fitness
        return currentPop.getFittestIndv();
    }
    

    public  Individual tournamentSelection(Population currentPop) {
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

        return winningParent; //will eventually return parent selected
    }


    public Individual boltzmannSelection(Population currentPop) {
        //generates random double to select from individuals with different probability to be chosen
        //an individual's probability to be chosen = e^that individuals fitness/sum of all individuals' e^fitness
        Individual parent = currentPop.individualList[0];
        double probabilitySelected;
        double eVar = 2.71828;
        double[] eFitValues = new double[popSize];
        double eFitSum = 0;

        for (int i = 0; i <= popSize; i++) {            //i represents index of indiviudal in currentPop. starting from least fit to most fit
            Individual currentIndv = currentPop.individualList[i];
            int indvFitness = currentIndv.getFitness();

            double eToFitness = Math.pow(eVar, indvFitness);
            eFitValues[i] = eToFitness;
            eFitSum += eToFitness;
        }

        //make random float under 1 to determine probability
        Random randBS = new Random();
        probabilitySelected = randBS.nextDouble();

        //starting from least fit to most fit
        for (int i = 0; i <= popSize; i++) {
            double indvProbability = (eFitValues[i] / eFitSum);
            if (probabilitySelected <= indvProbability) {
                parent = currentPop.individualList[i];
                i = -1;
                System.out.println("Boltzmann works!!! selected parent here");
            } 
        }
        return parent;
    }
    
    //selection method, determines type of selection and calls appropriate selection method, then returns 2 parents
    public Individual selection(Population currentPop) {
        Individual parent;
        if (selType.equals("ts")); {
            parent = tournamentSelection(currentPop);
        }  
        if (selType.equals("bs")); {
            parent = boltzmannSelection(currentPop);
        }
        if (selType.equals("rs")); {
            parent = rankSelection(currentPop);
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

        return ALL_ASCII.charAt(index);
    }

    //mutation method
    public Individual mutation(Individual indv) {
        String indvString = indv.getIndv();
        final String TARGET = "I think this is a reasonable medium sized string!!";

        for (int i = 0; i <= indvString.length(); i+=1) {       //for every character in indvString
            char oldChar = indvString.charAt(i);
            Random rand = new Random();
            float randProb = rand.nextFloat();              //makes random float between 0 and 1
            if (randProb <= mutationProb) {                 //if true, do mutation
                //change that char
                int randInt = rand.nextInt(92);  //92 = ALPAHBET LENGTH
                char newChar = getChar(randInt);
                if (newChar == oldChar) {                   //makes sure you are actually changing char
                    randInt += 1;
                    newChar = getChar(randInt);
                }
                indvString = indvString.substring(0, i) + newChar + indvString.substring(i+1);         //new mutated string
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
        } else {                        //we arent doing recombo, so children = parents
            children[0] = p1;  
            children[1] = p2;
        }
        return children;
    }

    public Population doGeneration(Population population) {
        Individual[] offspringList = new Individual[popSize]; 
        for(int i = 0; i < popSize; i+=2) {
            Individual p1;
            Individual p2;
            
            p1 = selection(population);
            p2 = selection(population);

            Individual[] children = recombination(p1, p2);
            Individual newChild1 = children[0];
            Individual newChild2 = children[1];

            Individual child1 = mutation(newChild1); 
            Individual child2 = mutation(newChild2);
        
            offspringList[i] = child1;
            offspringList[i+1] = child2;   
        }
        Population offspring = new Population(offspringList);
        return offspring;
    }

    public void generations(Population population) {
        //run ga through generations until it hits maxGen OR the individual of best fit for that generation is equal to the target 
        //print out data every time generation count hits printerval
        Population currentPop = population;  

        // create maxGens (max generation) amount of populations
        //might want to change to while loop
        for (int i = 0; i < maxGens; i++) {
            Population offspring = doGeneration(currentPop); 

            //if (offspring.getFittestIndv().getIndv()).equals(TARGET) {
                //end for loop
                //System.out.println("Hit the Target!!");


            // if at interval, print the fittest individual 

            if (i % this.printerval == 0) {

                mostFit = offspring.getFittestIndv();
                System.out.println(mostFit.getIndv());
                //String printThis = String.format("%x ( %x/ %x):  %s", i, mostFit
            } 

            currentPop = offspring; 
        }
    }




    public static void main(String[] args) {

        int popSize = Integer.parseInt(args[0]);
        String selType = args[1];                           // type of selection
        float crossoverProb = Float.parseFloat(args[2]);    // probability of crossover
        float mutationProb = Float.parseFloat(args[3]);     // probability of mutation
        int maxGens = Integer.parseInt(args[4]);            // max num of generations 
        int printInt = Integer.parseInt(args[5]);           // interval of generation  

        GA algorithm = new GA(popSize, selType, crossoverProb, mutationProb, maxGens, printInt); 
        

        //initaliazing first population and calling the genetic algorithm function that will do the actual algorithm
        Population initialPop = new Population(popSize, algorithm.TARGET);


        algorithm.generations(initialPop); 

        //  counter += 1 
        // for maxgenerations 1000 {
                // state, rPop = [] 
                // get parents from starterPop 
                //  do the 50  times thins 
                    // set offspring as starterPop 

        // }

        




    //initialize a population
    //Population initialPop = new Population();
    //initialPop.generateInitialPop();
    //Individual mostFit = initialPop.getFittestIndv();

    //population of offspring might move this
    //Population offspringPop = new Population();
    //int generationCount = 0;

    //THIS CHUNK MAKES OFFSPRING POP through selection, recombo and mutation (maybe it goes in main? is this main?)
    //DO THIS 50 TIMES: (or until offspring pop is 100)
    
    //p1 = selection(); 
    //p2 = selection();

    //do recombination(p1, p2) , return child1, child2 
    //Individual[] children = new Individual[2];
    //Individual[] children = recombination(p1, p2);
    //Individual child1 = children[0];
    //Individual child2 = children[1];

    //Individual child1 = mutation(child1); 
    //Individual child2 = mutation(child2);
        
    //put children into offspring population


//genCount or whatever its called += 1
//call GA again with offspring population, repeat until GA is finished
        
    }
}