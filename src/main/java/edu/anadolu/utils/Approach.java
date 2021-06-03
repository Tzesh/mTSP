package edu.anadolu.utils;

public enum Approach {
    NN("NNSolution"),
    RANDOM("RandomSolution");

    String solution;

    Approach(String solution) {
        this.solution = solution;
    }

    public String getSolution() {
        return solution;
    }

    @Override
    public String toString() {
        return solution;
    }
}
