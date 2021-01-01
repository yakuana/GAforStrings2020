//this class will hold all idnvidiuals in population in an array


public class Population {

    Individual[] individualList;  //list of this population

    //Constructor method
    public Population() {
        sortPop();
    }
        

    //generates 100 random individuals for starting population
    public void generateInitialPop() {
        //for each individual in poplation (100)
        //make a new indiivudal with random characters in alphabet
    }

    public void sortPop(){
        //sort individuals in population based on fitness
    }

    //should return individual with greatest fitness
    //problem here is that it wont let me say what is being returned Object or indiivudal?
    public getFittestIndv {
        individualList.sortPop();
        return individualList[0];
    }
    
    //one for adding a new individual(child) to pop

    

    

    //choose selection type to make breeding pool
    //can either select whole breeding pool then do recombination, or select pairs at a time

}