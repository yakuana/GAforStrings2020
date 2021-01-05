# GAforStrings2020
 Code and experiment with a basic genetic algorithm (GA) that tries to evolve character strings to match a given target string.


CS 2400 FALL 2020
ASSIGNMENT 4: GENETIC ALGORITHMS
Kayla Stuhlmann and Ya'kuana Davis 

GOAL STRING: 
"I think this is a reasonable medium sized string!!"

Experiment Results:

SELECTION METHODS


CROSSOVER PROBABILITIES
0.0, 0.3, 0.7, 1.0

MUTATION
0.0, 0.001, 0.005, 0.01, 0.05, 0.1, 0.2

FITNESS MEASURES
make 3 of our own

## Summary of Code:
This code provides three different selection sorts for a given generation of individuals. In doing so, we are able to compare how close our genetic algorithm comes to producing the goal string, "I think this is a reasonable medium sized string!!" 

## How to run Code: 
1. Open your terminal 
2. Cd into GAFORSTRINGS2020 
3. In the terminal type "java GA " followed by a value for each of the following parameters in order 
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
        
        java GA 100 ts 0.7 0.01 10000 100
   
Happy Coding!
