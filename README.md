# GAforStrings2020
 Code and experiment with a basic genetic algorithm (GA) that tries to evolve character strings to match a given target string.


CS 2400 FALL 2020
ASSIGNMENT 4: GENETIC ALGORITHMS
Kayla Stuhlmann and Ya'kuana Davis 


GOAL STRING: 
"I think this is a reasonable medium sized string!!"

## Experiment Results: 

#### SELECTION METHODS

The chart below shows the percentage of characters correct of a GA run's most fit individual compared to the target string. Each selection type (labelled "selType" below) was run 5 times and averaged in the last column. The target string was "I think this is a reasonable medium sized string!!", and the genetic alogrorithm had a 0.7 crossover probability, 0.01 mutation probability, 50 indiviudals per population, and ran 500 times. (AKA:"java GA 50 (ts/rs/bs) 0.7 0.01 500 20")

    Percent Characters Correct:

| _selType_ | 1 | 2 | 3 | 4 | 5 | Average 
| -- | -- | -- | -- | -- | -- | -- |
| Tournament | 22% | 18% | 10% | 6% | 10% | 13.2% |
| Boltzmann | 20% | 14% | 14% | 22% | 10% | 16% |     
|   Rank    | 64% | 64% | 62% | 64% | 62% | 63.2% |     


Tournament selection does not perform very well. The selection is more random and less biased towards the most fit individuals, which makes it more difficult to narrow down on a string that matches the target string or has the best fitness. We can see that it introduces more randomness as the individual with the highest fitness changes more frequently even when the hgiest fitness itself doesn't change. Boltzmann selection, on the other hand, is very heavily biased towards individuals with the best fitness. It is so biased towards them that it converges on a solution more quickly. However, this isn't an advantage in this case as it converges prematurely and gets rid of randomness. Rank selection Is in the middle of these two. It is biased towards more fit individuals, but because it bases it's decisions off of rank rather than the actual fitness, it prevents one or a couple of individuals from completely dominating the breedign pool/



#### CROSSOVER PROBABILITIES

The chart below shows the percentage of characters correct of a GA run's most fit individual compared to the target string. Each crossover probability was run 5 times and averaged in the last column. The target string was "I think this is a reasonable medium sized string!!", and the genetic alogrorithm used rank selection, had a 0.01 mutation probability, 50 indiviudals per population, and ran 500 times. (AKA:"java GA 50 "ts" (0.0/0.3/0.7/1.0) 0.01 500 20")

        Percent Characters Correct:
        
| _crossProb_ | 1 | 2 | 3 | 4 | 5 | Average |
| -- | -- | -- | -- | -- | -- | -- |
| 0.0 | 52% | 54% | 60% | 54% | 44% | 52.8% |     
| 0.3 | 62% | 62% | 64% | 58% | 62% | 61.6% |     
| 0.7 | 66% | 66% | 68% | 66% | 62% | 65.6% |     
| 1.0 | 68% | 58% | 64% | 62% | 72% | 64.8% |     



#### MUTATION

0.0, 0.001, 0.005, 0.01, 0.05, 0.1, 0.2
The chart below shows the percentage of characters correct of a GA run's most fit individual compared to the target string. Each mutation probability was run 5 times and averaged in the last column. The target string was "I think this is a reasonable medium sized string!!", and the genetic alogrorithm used rank selection, had a 0.7 crossover probability, 50 indiviudals per population, and ran 500 times. (AKA:"java GA 50 "rs" 0.7 (0.0/0.001/0.005/0.01/0.01/0.1/0.1) 0.01 500 20")

        Percent Characters Correct:

| _mutationProb_ | 1 | 2 | 3 | 4 | 5 | Average |
| -- | -- | -- | -- | -- | -- | -- | 
| 0.0 |  6% |  4% |  6% |  6% |  6% | 5.6% |     
| 0.001 | 22% | 14% | 12% | 14% | 24% | 16.6% |     
| 0.005 | 50% | 50% | 46% | 48% | 48% | 48.4% |     
| 0.01 | 68% | 70% | 58% | 66% | 56% | 63.6% |     
| 0.05 | 44% | 42% | 46% | 40% | 42% | 42.8% |     
| 0.1 | 28% | 28% | 24% | 26% | 26% | 26.4% |     
| 0.2 | 16% | 16% | 16% | 16% | 16% | 16% |     



### FITNESS MEASURES
Here are 3 possible fitness measures:

1) Same number of character types (lowercase letter, uppercase letter, symbol, number, or space) between string and target string.

2) Equivalent word lengths, meaning the characters have the correct number of spaces and in the correct location (i.e. #$%* has the same length as This) 

3) Percentage of character pairs in the correct location (i.e. Th%3 would have 1 pair of characters (Th) in the correct location for the target string This)



### Summary of Code:
Given a population size (popSize), selection type (selType), crossover probability (crossoverProb), mutation probability (mutationProb), maximum generation (maxGen), and print interval (printerval), this program will run a genetic algorithm to try to reach a target string.

It will first create an inital population of size popSize full of individuals of random strings generated using the alphabet in string ALL_ASCII. The population is sorted in order from lowest to highest fitness. Parents are selected through either tournament, rank, or Boltzmann selection, and every two parents return two children. If reocmbination is done (decided using a random probability), then the children a the result of one-point crossover. If not, the parents are returned as children. The children then undergo mutation, which is determined probabilistically for each character in the string. 

Generations of populations are created until we either 1) hit the maximum number of generations or 2) the individual with the highest fitness in a population is equal to the target string.



## How to run Code: 
1. Open your terminal 
2. Cd into GAFORSTRINGS2020 
3. Change/set target string in GA.java at line 6
4. In the terminal type "java GA " followed by a value for each of the following parameters in order 
    1. number of individuals in population (> 0)
    2. type of selection for breeding pool (string):
        - [ ] ts = NOTE: same individual cannot compete against self
        - [ ] rs = rank based selection
        - [ ] bs = Boltzmann selection
    3. crossover probability (0.0 to 1.0)
    4. mutation probability (0.0 to 1.0)
    5. max number of generations to run (> 0)
    6. show status at interval (0 < disInterval < generations)
    
    The resulting terminal entry should look like this but with the parameters you choose:
        
        java GA 100 ts 0.7 0.01 500 100

    
Happy Coding!
