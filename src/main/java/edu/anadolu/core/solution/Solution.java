package edu.anadolu.core.solution;

import edu.anadolu.core.mTSP;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static edu.anadolu.utils.TurkishNetwork.cities;
import static edu.anadolu.utils.TurkishNetwork.distance;

public class Solution {
    private final Random rand = new Random();
    public int numDepots = 0;
    public int numSalesmen = 0;
    public List<Integer> depots;
    public List<List<Integer>> routes;
    public int cost = Integer.MAX_VALUE;

    public Solution(int numDepots, int numSalesmen) {
        this.numDepots = numDepots;
        this.numSalesmen = numSalesmen;
    }

    public Solution(Solution solution) {
        List<List<Integer>> routeList = new ArrayList<>();
        List<Integer> depotList = new ArrayList<>(solution.depots);
        for (int i = 0; i < solution.routes.size(); i++) {
            routeList.add(new ArrayList<Integer>());
            for (int j = 0; j < solution.routes.get(i).size(); j++) {
                routeList.get(i).add(solution.routes.get(i).get(j));
            }
        }
        this.depots = depotList;
        this.routes = routeList;
        this.numDepots = solution.numDepots;
        this.numSalesmen = solution.numSalesmen;
        calculateCost();
    }

    public void solve() {
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
        calculateCost();
    }

    public void swapHubWithNodeInRoute() {
        int randomDepot = depots.size() == 0 ? 1 : rand.nextInt(depots.size());
        int randomHub = rand.nextInt(routes.size());
        int IndexOfRandomHub = rand.nextInt((routes.get(randomHub).size() - 1) - 1) + 1;

        final int depot = routes.get(randomHub).get(IndexOfRandomHub);
        final int existing = depots.get(randomDepot);
        depots.set(randomDepot, depot);
        routes.get(randomHub).set(IndexOfRandomHub, existing);

        for (List<Integer> route : routes) {
            if (route.get(0) == existing) {
                route.set(0, depot);
                route.set(route.size() - 1, depot);
            }
        }
        calculateCost();
    }


    public void swapNodesBetweenRoutes(boolean maintainMain) {
        if (numDepots == 1 || maintainMain) return;
        final List<Integer> collect = rand.ints(0, routes.size())
                .distinct()
                .limit(2)
                .boxed()
                .collect(Collectors.toList());
        if (routes.get(collect.get(0)).size() > 2 && routes.get(collect.get(1)).size() > 2) {
            int first = rand.nextInt(routes.get(collect.get(0)).size() - 1 - 1) + 1;
            int second = rand.nextInt(routes.get(collect.get(1)).size() - 1 - 1) + 1;

            int firstNode = routes.get(collect.get(0)).get(first);
            int secondNode = routes.get(collect.get(1)).get(second);

            routes.get(collect.get(0)).set(first, secondNode);
            routes.get(collect.get(1)).set(second, firstNode);
        }

        calculateCost();
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

            routes.get(i).remove(elemWillRemove);
            routes.get(i).add((collect.get(1)), elemWillRemove);
        }

        calculateCost();
    }

    public void insertNodeBetweenRoutes(boolean maintainMain) {
        if (numDepots == 1 || maintainMain) return;
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

        calculateCost();
    }

    public void print(boolean verbose, boolean write, boolean heuristics) {
        List cityIndexes = Arrays.asList(cities);
        for (int i = 0; i < routes.size(); i++) {
            final List<Integer> list = routes.get(i);

            if ((i % numSalesmen) == 0) {
                System.out.println("Depot" + (i / numSalesmen + 1) + ": " + (verbose ? cities[depots.get(i / numSalesmen)] : cityIndexes.indexOf(cities[depots.get(i / numSalesmen)])));
            }

            System.out.print(" Route" + (i % numSalesmen + 1) + ": ");

            for (int j = 0; j < list.size(); j++) {
                if (j != 0 && j != list.size() - 1)
                    System.out.print((verbose ? cities[list.get(j)] : cityIndexes.indexOf(cities[list.get(j)])) + (j != list.size() - 2 ? ", " : " "));
            }
            System.out.println();
        }

        if (write) {
            List<String> routes = new ArrayList<>();
            int numberOfDepots = depots.size();

            for (int i = 0; i < this.routes.size(); i++) {
                StringBuilder stringBuilder = new StringBuilder();
                StringJoiner stringJoiner = new StringJoiner(" ");
                if ((i % numSalesmen) == 0) {

                    stringBuilder.append("{depots : \"").append(this.depots.get(i / numSalesmen)).append("\",");
                }

                for (int j = 0; j < this.routes.get(i).size(); j++) {

                    if (j != 0 && j != this.routes.get(i).size() - 1) {
                        stringJoiner.add(String.valueOf(this.routes.get(i).get(j)));

                    }
                }

                routes.add(stringJoiner.toString());
                stringBuilder.append(stringJoiner);

                stringBuilder.append("}");
            }

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{\n  \"" + "solution" + "\": [");

            for (int i = 0; i < this.routes.size(); i++) {

                if ((i % numSalesmen) == 0) {
                    stringBuilder.append("\n    {\n      \"depot\": \"" + depots.get(i / numSalesmen) + "\",");
                    stringBuilder.append("\n      \"routes\":" + " [\n");
                }

                stringBuilder.append(",\"");

                stringBuilder.append(routes.get(i));
                stringBuilder.append("\"");

                if ((i % numSalesmen == numSalesmen - 1)) {
                    stringBuilder.append("\n      ]");
                    stringBuilder.append(i == this.routes.size() - 1 ? "\n    }" : "\n    },");
                }
            }

            stringBuilder.append("\n  ]\n}");

            final String replace = stringBuilder.toString().replace("\n,", "\n        ").replace("}{", "},\n{").replace("\",\"", "\",\n        \"");

            try {
                Files.write(Paths.get("solution_d" + numberOfDepots + "s" + numSalesmen + ".json"), replace.getBytes());
            } catch (IOException e) {
                System.err.println("Unable to write json");
                e.printStackTrace();
            }
        }

        System.out.println("**Total cost is " + this.cost);

        if (heuristics) {
            System.out.println("Heuristically Statistics");
            System.out.println("    \"swapHubWithNodeInRoute\": " + mTSP.swapHubWithNodeInRoute + ",");
            System.out.println("    \"insertNodeBetweenRoutes\": " + mTSP.insertNodeBetweenRoutes + ",");
            System.out.println("    \"swapNodesInRoute\": " + mTSP.swapNodesInRoute + ",");
            System.out.println("    \"swapNodesBetweenRoutes\": " + mTSP.swapNodesBetweenRoutes + ",");
            System.out.println("    \"insertNodeInRoute\": " + mTSP.insertNodeInRoute);
        }
    }

    public void calculateCost() {
        cost = 0;
        for (final List<Integer> list : routes) {
            for (int j = 0; j < list.size() - 1; j++) {
                cost += distance[list.get(j)][list.get(j + 1)];
            }
        }
    }

    protected int calculateCost(List<List<Integer>> routes) {
        int cost = 0;
        for (final List<Integer> list : routes) {
            for (int j = 0; j < list.size() - 1; j++) {
                cost += distance[list.get(j)][list.get(j + 1)];
            }
        }
        return cost;
    }

    protected List<List<Integer>> partition(List<Integer> iterable, int partitions) {
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