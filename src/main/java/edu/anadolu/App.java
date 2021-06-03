package edu.anadolu;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;
import edu.anadolu.core.mTSP;
import edu.anadolu.utils.Approach;
import edu.anadolu.utils.Params;

public class App {

    public static void main(String[] args) {

        Params params;
        try {
            params = CliFactory.parseArguments(Params.class, args);
        } catch (ArgumentValidationException e) {
            System.out.println(e.getMessage());
            return;
        }

        mTSP best;

        mTSP mTSP = new mTSP(params.getNumDepots(), params.getNumSalesmen(), Approach.RANDOM, false);
        mTSP.currentSolution.print(params.getNumSalesmen(), params.getVerbose(), false, false);
        best = mTSP;

        mTSP = new mTSP(params.getNumDepots(), params.getNumSalesmen(), Approach.RANDOM, true);
        mTSP.currentSolution.print(params.getNumSalesmen(), params.getVerbose(), false, true);
        if (mTSP.currentSolution.cost < best.currentSolution.cost) best = mTSP;


        mTSP = new mTSP(params.getNumDepots(), params.getNumSalesmen(), Approach.NN, false);
        mTSP.currentSolution.print(params.getNumSalesmen(), params.getVerbose(), false, false);
        if (mTSP.currentSolution.cost < best.currentSolution.cost) best = mTSP;


        mTSP = new mTSP(params.getNumDepots(), params.getNumSalesmen(), Approach.NN, true);
        mTSP.currentSolution.print(params.getNumSalesmen(), params.getVerbose(), false, true);
        if (mTSP.currentSolution.cost < best.currentSolution.cost) best = mTSP;

        System.out.println("Best solution has cost: " + best.currentSolution.cost);
        best.currentSolution.print(params.getNumSalesmen(), params.getVerbose(), true, true);
    }
}
