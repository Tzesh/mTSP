
package edu.anadolu.core.solution;

import java.util.ArrayList;
import java.util.List;

import static edu.anadolu.utils.TurkishNetwork.cities;
import static edu.anadolu.utils.TurkishNetwork.distance;

public class NNSolution extends Solution {
    private Integer mainDepot;

    public NNSolution(int numDepots, int numSalesmen, int mainDepot) {
        super(numDepots, numSalesmen);
        this.mainDepot = mainDepot;
        solve();
    }

    @Override
    public void solve() {
        List<Integer> primaryRoute = new ArrayList<>();
        primaryRoute.add(mainDepot);
        for (int i = 0; i < cities.length - 1; i++) {
            if (mainDepot == i) continue;
            Integer currentCity = primaryRoute.get(primaryRoute.size() - 1);
            Integer closestCity = getClosestCity(currentCity, primaryRoute);
            primaryRoute.add(closestCity);
        }
        this.routes = partition(primaryRoute, (numDepots * numSalesmen));
    }

    public Integer getClosestCity(Integer cityIndex, List<Integer> currentRoute) {
        int closestIndex = 0;
        int minDistance = Integer.MAX_VALUE;
        for (int i = 0; i < cities.length - 1; i++) {
            if (cityIndex == i || currentRoute.contains(i)) continue;
            int currentDistance = distance[cityIndex][i];
            if (currentDistance < minDistance) {
                closestIndex = i;
                minDistance = currentDistance;
            }
        }
        return closestIndex;
    }
}