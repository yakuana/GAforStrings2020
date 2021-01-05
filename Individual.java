import java.util.Comparator;

public class Individual implements Comparable<Individual> {

    String target;         // target string  
    String indvString;     // individual's string  
    Integer fitness;       // number of characters that match target string in individual string  

    public Individual(String targetIn, String indv) {
            target = targetIn;
            indvString = indv; //make indv optional somehow for starting pop
            fitness = calculateFitness();
    }
    //calculates the fitness
    public int calculateFitness() {
        int charsSharedCount = 0;
        //count number of characters individual shares with target string, make this the fit
        for (int i = 0; i < indvString.length() - 1; i++) {
            char thisChar = indvString.charAt(i);   //character in this individuals string at i
            char targetChar = target.charAt(i);     //character in target string at i
            if (Character.valueOf(thisChar).compareTo(targetChar) == 0)  {
                charsSharedCount += 1;
            }
        } 
        fitness = charsSharedCount;  
        return fitness;
    }

    //returns fitness if we need it outside of this class
    public int getFitness() {
        fitness = calculateFitness();
        return fitness; 
    }
    
    public String getIndv() {
        return indvString;
    }

    @Override
    public int compareTo(Individual indv) {
        if (this.getFitness() > indv.getFitness()) {
            return 1;
        }
        else if (this.getFitness() < indv.getFitness()) {
            return -1;
        }
        else {
            return 0;
        }
    }
    
    public static Comparator<Individual> IndvFitnessComparator = new Comparator<Individual>() {
        public int compare(Individual indvA, Individual indvB) {
            if (indvA ==null|| indvB ==null) {
                return 0;
            }
            //int fitA = indvA.getFitness();
            //int fitB = indvB.getFitness();

            //ascending order
            return indvA.compareTo(indvB);
        }
    };

}

