package edu.anadolu.core.solution;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomSolution extends Solution {

    public RandomSolution(int numDepots, int numSalesmen) {
        super(numDepots, numSalesmen);
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

    private List<List<Integer>> randomizeRoutes() {
        List<Integer> cityIndexes = IntStream.range(0, 81).boxed().collect(Collectors.toList());
        Collections.shuffle(cityIndexes);
        return partition(cityIndexes, (numDepots * numSalesmen));
    }
}