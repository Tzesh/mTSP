package edu.anadolu.core.solution;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.anadolu.utils.TurkishNetwork.cities;

public class RandomSolution extends Solution {

    public RandomSolution(int numDepots, int numSalesmen) {
        super(numDepots, numSalesmen);
        Random rand = new Random();
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

    private List<List<Integer>> randomizeRoutes() {
        List<Integer> cityIndexes = IntStream.range(0, 81).boxed().collect(Collectors.toList());
        Collections.shuffle(cityIndexes);
        return partition(cityIndexes, (numDepots * numSalesmen));
    }
}