public class Individual {

    String target;  //just put this here for now, probs doesn't go here
    String indvString; 
    Integer fitness; 

    public Individual(String targetIn, String indv) {
            target = targetIn;
            indvString = indv; //make indv optional somehow for starting pop
            fitness = calculateFitness();
    }
    

    //calculates the fitness
    public int calculateFitness() {
        fitness = 0;
        int sharedCount = 0;

        //count number of characters individual shares with target string, make this the fit
        for (int i = 0; i < indvString.length(); i++) {
            char thisChar = indvString.charAt(i);   //character in this individuals string at i
            char targetChar = target.charAt(i);     //character in target string at i
            if (thisChar == targetChar);  {
                sharedCount += 1;
            }
        }

        fitness = sharedCount;  
        return fitness;
    }

    //returns fitness if we need it outside of this class
    public int getFitness() {
        return fitness; //or return this.calculateFitness()? idk how to java
    }

    public String getIndv() {
        return indvString;
    }
}

