package edu.anadolu;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;
import edu.anadolu.core.mTSP;
import edu.anadolu.core.soluton.Approach;
import edu.anadolu.utils.Params;

import java.util.stream.IntStream;

public class App {

    public static void main(String[] args) {

        Params params;
        try {
            params = CliFactory.parseArguments(Params.class, args);
        } catch (ArgumentValidationException e) {
            System.out.println(e.getMessage());
            return;
        }

        mTSP mTSP = new mTSP(params.getNumDepots(), params.getNumSalesmen(), Approach.RANDOM, false);
        mTSP.currentSolution.print(params.getNumSalesmen(), params.getVerbose(), true, false);
    }
}
