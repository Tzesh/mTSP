package edu.anadolu.core;

import edu.anadolu.core.solution.NNSolution;
import edu.anadolu.core.solution.RandomSolution;
import edu.anadolu.core.solution.Solution;
import edu.anadolu.utils.Approach;

import java.util.Random;
import java.util.stream.IntStream;

public class mTSP {
    private static final Random rand = new Random();
    public Solution currentSolution;

    public static int swapNodesInRoute;
    public static int swapHubWithNodeInRoute;
    public static int swapNodesBetweenRoutes;
    public static int insertNodeInRoute;
    public static int insertNodeBetweenRoutes;

    public mTSP(int numDepots, int numSalesmen, Approach approach, boolean isHeuristic, int mainDepot, boolean maintainMain) {
        switch (approach.getSolution()) {
            case "NNSolution":
                currentSolution = new NNSolution(numDepots, numSalesmen, mainDepot - 1);
                break;
            case "RandomSolution":
                currentSolution = new RandomSolution(numDepots, numSalesmen);
                break;
            default:
                break;
        }
        currentSolution.solve();
        if (isHeuristic)
            IntStream.range(0, 5_000_000).parallel().forEach(a -> heuristicOperations(maintainMain));
    }

    public synchronized void heuristicOperations(boolean maintainMain) {
        Solution copy = new Solution(currentSolution);

        int i = rand.nextInt(5);
        if (i == 0) {
            copy.swapNodesInRoute();
            if (copy.cost < currentSolution.cost)
                swapNodesInRoute++;
        }
        if (i == 1) {
            copy.swapHubWithNodeInRoute();
            if (copy.cost < currentSolution.cost)
                swapHubWithNodeInRoute++;
        }
        if (i == 2) {
            copy.swapNodesBetweenRoutes(maintainMain);
            if (copy.cost < currentSolution.cost)
                swapNodesBetweenRoutes++;
        }
        if (i == 3) {
            copy.insertNodeInRoute();
            if (copy.cost < currentSolution.cost)
                insertNodeInRoute++;
        }
        if (i == 4) {
            copy.insertNodeBetweenRoutes(maintainMain);
            if (copy.cost < currentSolution.cost)
                insertNodeBetweenRoutes++;
        }
        if (copy.cost < currentSolution.cost)
            currentSolution = copy;
    }
}