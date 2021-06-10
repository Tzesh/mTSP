package edu.anadolu;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;
import edu.anadolu.core.mTSP;
import edu.anadolu.utils.Approach;
import edu.anadolu.utils.Params;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class App {
    private static AtomicReference<mTSP> best;

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8)); // to avoid OS dependent outputs
        Params params;
        try {
            params = CliFactory.parseArguments(Params.class, args);
        } catch (ArgumentValidationException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (params.getNumSalesmen() < 1 || params.getNumDepots() < 1 || params.getNumberOfIterations() < 1 || params.getPlateNumberOfMainDepot() > 81 || params.getPlateNumberOfMainDepot() < 1) {
            System.out.println("Number of salesmen, number of iterations, and number of depots should be a positive integer value. And also make sure that plate number is in the interval [1, 81].");
            System.exit(0);
        }

        IntStream.range(0, params.getNumberOfIterations()).parallel().forEach(a -> {
            doAllSolutions(params);
        });

        System.out.println("\n**Best solution has cost " + best.get().currentSolution.cost);
        best.get().currentSolution.print(params.getVerbose(), true, true);
    }

    public static void doAllSolutions(Params params) {
        AtomicReference<mTSP> mTSP = new AtomicReference<>(new mTSP(params.getNumDepots(), params.getNumSalesmen(), Approach.RANDOM, false, params.getPlateNumberOfMainDepot(), params.getMaintainMain()));
        System.out.println("**Random Solution without Heuristics**");
        mTSP.get().currentSolution.print(params.getVerbose(), false, false);
        AtomicReference<edu.anadolu.core.mTSP> bestMTSP = mTSP;

        mTSP.set(new mTSP(params.getNumDepots(), params.getNumSalesmen(), Approach.RANDOM, true, params.getPlateNumberOfMainDepot(), params.getMaintainMain()));
        System.out.println("\n**Random Solution with Heuristics**");
        mTSP.get().currentSolution.print(params.getVerbose(), false, true);
        if (mTSP.get().currentSolution.cost < bestMTSP.get().currentSolution.cost) bestMTSP = mTSP;

        mTSP.set(new mTSP(params.getNumDepots(), params.getNumSalesmen(), Approach.NN, false, params.getPlateNumberOfMainDepot(), params.getMaintainMain()));
        System.out.println("\n**NN Solution without Heuristics**");
        mTSP.get().currentSolution.print(params.getVerbose(), false, false);
        if (mTSP.get().currentSolution.cost < bestMTSP.get().currentSolution.cost) bestMTSP = mTSP;

        mTSP.set(new mTSP(params.getNumDepots(), params.getNumSalesmen(), Approach.NN, true, params.getPlateNumberOfMainDepot(), params.getMaintainMain()));
        System.out.println("\n**NN Solution with Heuristics**");
        mTSP.get().currentSolution.print(params.getVerbose(), false, true);
        if (mTSP.get().currentSolution.cost < bestMTSP.get().currentSolution.cost) bestMTSP = mTSP;

        if (best == null) {
            best = bestMTSP;
            return;
        }

        if (bestMTSP.get().currentSolution.cost < best.get().currentSolution.cost) {
            best = bestMTSP;
        }
    }
}
