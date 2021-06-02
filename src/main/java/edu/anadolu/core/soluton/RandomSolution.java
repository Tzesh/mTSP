package edu.anadolu.core.soluton;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.anadolu.utils.TurkishNetwork.cities;
import static edu.anadolu.utils.TurkishNetwork.distance;

public class RandomSolution extends Solution {
    private final Random rand = new Random();
    public int cost = Integer.MAX_VALUE;

    public RandomSolution(int numDepots, int numSalesmen) {
        super(numDepots, numSalesmen);
        depots = rand.ints(0, cities.length)
                .distinct()
                .limit(numDepots)
                .boxed()
                .collect(Collectors.toList());
        solve();
    }

    @Override
    public void solve() {
        IntStream.range(0, 100_000).parallel().forEach(a -> {
            List<List<Integer>> routes = randomizeRoutes();
            int tempCost = calculateCost(routes);
            if (tempCost < cost) {
                this.routes = routes;
                this.cost = tempCost;
            }
        });
        calculateCost();
    }


    private int calculateCost(List<List<Integer>> routes) {
        int cost = 0;
        for (final List<Integer> list : routes) {
            for (int j = 0; j < list.size() - 1; j++) {
                cost += distance[list.get(j)][list.get(j + 1)];
            }
        }
        return cost;
    }

    private List<List<Integer>> randomizeRoutes() {
        List<Integer> numbers0To80 = IntStream.range(0, 81).boxed().collect(Collectors.toList());
        Collections.shuffle(numbers0To80);
        return partition(numbers0To80, (numDepots * numSalesmen));
    }

    private List<List<Integer>> partition(List<Integer> iterable, int partitions) {
        List<Integer> depots = new ArrayList<>(this.depots);
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