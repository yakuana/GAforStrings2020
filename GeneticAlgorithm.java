public class GeneticAlogorithm {
    
//from here, we initialize the starting random population, also contains selection, recombination, and mutation functions I think
    public GeneticAlogorithm() {


        //initialize a population
        Population initialPop = new Population();
        initialPop.generateInitialPop();
        Individual mostFit = initialPop.getFittestIndv();

        //population of offspring might move this
        Population offspringPop = new Population();
        int generationCount = 0;
    }


    
    
    


    
    
    
    
    




    //DO THIS 50 TIMES: (or until offspring pop is 100)
    //if input string.equals('ts'), do tselection, if rs do rselection, etc
        //have selection methods return 2 parents each call (p1 and p2)

    //if probability says to do recombination
        //do recombination(p1, p2) , return 2 children 
        //else, return parents

    //mutation(child1) 
        //for char in child1:
          //new random 
          //if random is within probability
              //change that char (I'm thinking just randomly slect char from alphabet and make sure it's not same char)

    //repeat mutation(child2)

    //put children into offspring population

//call GA again with offspring population, repeat until GA is finished




    public static void main(String[] args) {
        System.out.println("This will be printed");

        
    }
}