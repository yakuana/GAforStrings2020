public class Individual {
    String target;  //just put this here for now, probs doesn't go here
    String indvString; 
    float fitness; 

    //make alpahabet to generate intial random string

    //calculates the fitness
    public void calculateFitness() {

        fitness = 0;
        sharedCount = 0;

        //count number of characters individua; shares with target string, make this the fit
        for (int i = 0; i < sentence.length(); i++) {
            thisChar = indvString.charAt(i);  //character in this individuals string at i
            targetChar = target.chatAt(i);  //character in target string at i
            if(thisChar == targetChar);  {
                sharedChar += 1;
            }
        }

        fitness = sharedChar / indvString.length();  //might have to typecast string length?
    }

    //returns fitness if we need it outside of this class
    public float getFitness() {
        return fitness; //or return this.calculateFitness()? idk how to java
    }
}
