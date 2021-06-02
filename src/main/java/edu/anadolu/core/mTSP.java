package edu.anadolu.core;

import edu.anadolu.utils.TurkishNetwork;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class mTSP {
    private int numDepots;
    private int numSalesmen;
    private static final Random rand = new Random();
    private static final String[] cities = TurkishNetwork.cities;
    private List<Integer> depots;
    public List<Solution> list;
    public Solution currentSolution;
    private final List<Integer> numbers0To80 = IntStream.range(0, 81).boxed().collect(Collectors.toList());

    public static int swapNodesInRoute;
    public static int swapHubWithNodeInRoute;
    public static int swapNodesBetweenRoutes;
    public static int insertNodeInRoute;
    public static int insertNodeBetweenRoutes;

    public mTSP(int numDepots, int numSalesmen) {
        this.numDepots = numDepots;
        this.numSalesmen = numSalesmen;
        depots = rand.ints(0, cities.length)
                .distinct()
                .limit(numDepots)
                .boxed()
                .collect(Collectors.toList());
        list = new ArrayList<>();
        currentSolution = new Solution(depots, toSublist(numbers0To80));
        list.add(currentSolution);
    }


    public void randomSolution() {
        Solution copy = new Solution(currentSolution);

        int i = rand.nextInt(5);
        if (i == 0) {
            copy.swapNodesInRoute();
            if (copy.cost < currentSolution.cost) {
                swapNodesInRoute++;
            }
        }

        if (i == 1) {
            copy.swapHubWithNodeInRoute();
            if (copy.cost < currentSolution.cost) {
                swapHubWithNodeInRoute++;
            }

        }

        if (i == 2) {
            copy.swapNodesBetweenRoutes();
            if (copy.cost < currentSolution.cost) {
                swapNodesBetweenRoutes++;
            }
        }

        if (i == 3) {
            copy.insertNodeInRoute();
            if (copy.cost < currentSolution.cost) {
                insertNodeInRoute++;
            }
        }

        if (i == 4) {
            copy.insertNodeBetweenRoutes();
            if (copy.cost < currentSolution.cost) {
                insertNodeBetweenRoutes++;
            }
        }

        if (copy.cost < currentSolution.cost) {
            currentSolution = copy;
        }
    }


    private List<List<Integer>> toSublist(List<Integer> lists) {
        Collections.shuffle(lists);
        return partition(lists, (numDepots * numSalesmen));
    }

    private List<List<Integer>> partition(List<Integer> iterable, int partitions) {
        iterable.removeAll(depots);
        List<List<Integer>> result = new ArrayList<>(partitions);
        for (int i = 0; i < partitions; i++) {
            result.add(new ArrayList<>());
        }
        Iterator<Integer> iterator = iterable.iterator();
        for (int i = 0; iterator.hasNext(); i++)
            result.get(i % partitions).add(iterator.next());
        for (int i = 0; i < result.size(); i++) {
            result.get(i).add(0, depots.get(i / numSalesmen));
            result.get(i).add(result.get(i).size(), depots.get(i / numSalesmen));
        }

        return result;
    }
}