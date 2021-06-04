package edu.anadolu;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;
import edu.anadolu.core.mTSP;
import edu.anadolu.utils.Approach;
import edu.anadolu.utils.Params;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class App {
    private static AtomicReference<mTSP> best;
    private static AtomicInteger[] minCost = {new AtomicInteger(Integer.MAX_VALUE)};
    public static void main(String[] args) {

        Params params;
        try {
            params = CliFactory.parseArguments(Params.class, args);
        } catch (ArgumentValidationException e) {
            System.out.println(e.getMessage());
            return;
        }


        IntStream.range(0, 1).parallel().forEach(a -> {
            doAllSolutions(params);
        });

        System.out.println("\n**Best solution has cost " + best.get().currentSolution.cost);
        best.get().currentSolution.print(params.getNumSalesmen(), params.getVerbose(), true, true);
    }

    public static void doAllSolutions(Params params) {
        System.out.println("**Random Solution without Heuristics**");
        AtomicReference<mTSP> mTSP = new AtomicReference<>(new mTSP(params.getNumDepots(), params.getNumSalesmen(), Approach.RANDOM, false));
        AtomicReference<edu.anadolu.core.mTSP> bestmTSP = mTSP;

        System.out.println("\n**Random Solution with Heuristics**");
        mTSP.set(new mTSP(params.getNumDepots(), params.getNumSalesmen(), Approach.RANDOM, true));
        if (mTSP.get().currentSolution.cost < bestmTSP.get().currentSolution.cost) bestmTSP = mTSP;

        System.out.println("\n**NN Solution without Heuristics**");
        mTSP.set(new mTSP(params.getNumDepots(), params.getNumSalesmen(), Approach.NN, false));
        if (mTSP.get().currentSolution.cost < bestmTSP.get().currentSolution.cost) bestmTSP = mTSP;

        System.out.println("\n**NN Solution with Heuristics**");
        mTSP.set(new mTSP(params.getNumDepots(), params.getNumSalesmen(), Approach.NN, true));

        if (bestmTSP.get().currentSolution.cost < minCost[0].get()) {
            minCost[0] = new AtomicInteger(bestmTSP.get().currentSolution.cost);
            best = bestmTSP;
        }
    }
}
