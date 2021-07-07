# mTSP (Multiple Traveling Salesman Problem)
The multiple traveling salesman problem (mTSP) is a generalization of the well-known traveling salesman problem (TSP), where more than one salesman is allowed to be used in the solution. In this project, we are using 4 different approaches to find a solution to this problem in the given Turkey's cities and their distances.
1. Random solution: Shuffles all the cities, then adds them randomly to make a path. And divides paths into depots then routes. Does 100.000 iterations and picks the best one.
2. Random solution with Heuristic algorithms: Same methodology of random solution but afterwards it does 'Improvement Heuristics' for 5.000.000 times (A.K.A Heuristic algorithms) randomly and if there is an improvement does not undo the Heuristic algorithm applied.
3. Nearest Neighbor solution: It is a Construction Heuristic it takes a city as parameter and makes that city the main depot in the first place. And then generates a path according to closest path that could be generated. Then subdivides the path into depots then routes. Main depot which is given as parameter should be center city (which has the minimum distance to all the cities except itself).
4. Nearest Neighbor solution with Heuristic algorithms: Applies the same solution with Nearest Neighbor then does the Heuristic algorithms for 5.000.000 iterations as mentioned in Random Solution with Heuristics.

In this project, we've tried to look for the best combination to solve the problem. The substructure of the project belongs to '[mTSP](https://github.com/AnadoluUniversityCeng/multiple-tsp)'. We didn't fork the repository to let other CENG candidates to think about the problem and gain experience.

For the tests that we've done so far, 'Nearest Neighbor solution with Heuristics algorithms' is the best solution that gives minimum cost. You can easily browse these solutions in 'example_solutions' folder.

## Usage
This application does both 4 solutions for once. You can easily change that value to how many iterations do you want to do. All you have to do is changing the -i parameter while executing. Just compile or download the latest version and then run the mTSP.jar with command `java -jar mTSP.jar`. By default application will build a route with 5 depots and 2 salesmen per depot. You can use these parameters:
* -d 'number of depots': to specify the number of depots (default 5)
* -s 'number of salesmen per depot': to set the number of salesmen per depot (default 2)
* -v: to use verbose mode which allows you to see city names instead of indexes (default false)
* -i: to specify how many iterations will be generated for both 4 approaches (default 1)
* -p: to change the main (initial) depot of NN solution by entering plate number (default 38 - center city of Turkey)
* -m: maintain the main depot as main depot in NN solutions (default false)
* -h: to see all the parameters
For instance, you can use `java -jar mTSP.jar -d 7 -s 5 -v` to generate a route with using these approaches which has 7 depots and 5 salesmen per depot, and also application will print city names instead of indexes.
![Footage 1](https://imgur.com/Hl62zNZ.png)
![Footage 2](https://i.imgur.com/hlkK7od.png)
