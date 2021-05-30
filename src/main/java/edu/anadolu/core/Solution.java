package edu.anadolu.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static edu.anadolu.utils.TurkishNetwork.cities;
import static edu.anadolu.utils.TurkishNetwork.distance;

public class Solution {
    private final Random rand = new Random();
    public List<Integer> depots;
    public List<List<Integer>> routes;
    public int cost;

    public Solution(List<Integer> depots, List<List<Integer>> routes) {
        this.depots = depots;
        this.routes = routes;
        cost();
    }

    public Solution(Solution solution) {
        List<Integer> depotList = new ArrayList<>();
        List<List<Integer>> routeList = new ArrayList<>();
        for (int i = 0; i < solution.depots.size(); i++) {
            depotList.add(solution.depots.get(i));
        }
        for (int i = 0; i < solution.routes.size(); i++) {
            routeList.add(new ArrayList<Integer>());
            for (int j = 0; j < solution.routes.get(i).size(); j++) {
                routeList.get(i).add(solution.routes.get(i).get(j));
            }
        }
        this.depots = depotList;
        this.routes = routeList;
        cost();
    }

    public void swapNodesInRoute() {
        final int i = rand.nextInt(routes.size());


        if (routes.get(i).size() > 3) {
            final List<Integer> collect = rand.ints(1, routes.get(i).size() - 1)
                    .distinct()
                    .limit(2)
                    .boxed()
                    .collect(Collectors.toList());

            Collections.swap(routes.get(i), collect.get(0), collect.get(1));


        }
        cost();
    }

    public void swapHubWithNodeInRoute() {


        int randomDepot = depots.size() == 0 ? 1 : rand.nextInt(depots.size());
        int randomhub = rand.nextInt(routes.size());
        int indexinrandomhub = rand.nextInt((routes.get(randomhub).size() - 1) - 1) + 1;


        final int elemWillBeDepot = routes.get(randomhub).get(indexinrandomhub);
        final int depotWillDelete = depots.get(randomDepot);
        depots.set(randomDepot, elemWillBeDepot);
//        System.out.println("randomDepot" + randomDepot);
        routes.get(randomhub).set(indexinrandomhub, depotWillDelete);

//        System.out.println("elemWillBeDepot" + elemWillBeDepot);
//        System.out.println("depotWillDelete" + depotWillDelete);


        for (int i = 0; i < routes.size(); i++) {
            if (routes.get(i).get(0) == depotWillDelete) {
                routes.get(i).set(0, elemWillBeDepot);
                routes.get(i).set(routes.get(i).size() - 1, elemWillBeDepot);
            }
        }


        cost();
    }


    public void swapNodesBetweenRoutes() {
        final List<Integer> collect = rand.ints(0, routes.size())
                .distinct()
                .limit(2)
                .boxed()
                .collect(Collectors.toList());
        if (routes.get(collect.get(0)).size() > 2 && routes.get(collect.get(1)).size() > 2) {
            int first = rand.nextInt(routes.get(collect.get(0)).size() - 1 - 1) + 1;
            int second = rand.nextInt(routes.get(collect.get(1)).size() - 1 - 1) + 1;


            int felem = routes.get(collect.get(0)).get(first);
            int selem = routes.get(collect.get(1)).get(second);


            routes.get(collect.get(0)).set(first, selem);
            routes.get(collect.get(1)).set(second, felem);


        }
        cost();
    }

    public void insertNodeInRoute() {
        final int i = rand.nextInt(routes.size());


        if (routes.get(i).size() > 4) {
            final List<Integer> collect = rand.ints(1, routes.get(i).size() - 2)
                    .distinct()
                    .limit(2)
                    .boxed()
                    .collect(Collectors.toList());


            Integer elemWillRemove = routes.get(i).get(collect.get(0));
            Integer second = routes.get(i).get(collect.get(1));


            routes.get(i).remove(elemWillRemove);

            routes.get(i).add((collect.get(1)), elemWillRemove);


        }

        cost();
    }

    public void insertNodeBetweenRoutes() {

        final List<Integer> collect = rand.ints(0, routes.size())
                .distinct()
                .limit(2)
                .boxed()
                .collect(Collectors.toList());
        if (routes.get(collect.get(0)).size() > 2 && routes.get(collect.get(1)).size() > 2) {
            int first = rand.nextInt(routes.get(collect.get(0)).size() - 1 - 1) + 1;
            int second = rand.nextInt(routes.get(collect.get(1)).size() - 1 - 1) + 1;


            int felem = routes.get(collect.get(0)).get(first);
            int selem = routes.get(collect.get(1)).get(second);


            if (routes.get(collect.get(0)).size() > 3) {

                routes.get(collect.get(0)).set(first, selem);
                routes.get(collect.get(1)).set(second, felem);

                Integer elemWillRemove = routes.get(collect.get(0)).get(first);

                routes.get(collect.get(0)).remove(elemWillRemove);

                routes.get(collect.get(1)).add(second + 1, elemWillRemove);


            }

        }

        cost();
    }

    public void print(int numSalesmen) {

        for (int i = 0; i < routes.size(); i++) {
            final List<Integer> list = routes.get(i);

            if ((i % numSalesmen) == 0) {
                System.out.println("Depot" + (i / numSalesmen + 1) + ": " + cities[depots.get(i / numSalesmen)]);
            }
            System.out.print("  Route:" + (i % numSalesmen + 1) + " ");

            for (int j = 0; j < list.size(); j++) {
                if (j != 0 && j != list.size() - 1)
                    System.out.print(cities[list.get(j)] + (j != list.size() - 2 ? ", " : " "));
            }
            System.out.println();
        }
    }

    void cost() {
        cost = 0;
        for (final List<Integer> list : routes) {
            for (int j = 0; j < list.size() - 1; j++) {
                cost += distance[list.get(j)][list.get(j + 1)];
            }
        }
    }
}