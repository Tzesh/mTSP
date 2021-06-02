package edu.anadolu;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;
import edu.anadolu.core.mTSP;
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

        mTSP mTSP = new mTSP(params.getNumDepots(), params.getNumSalesmen());
        IntStream.range(0, 5_000_000).parallel().forEach(a -> mTSP.randomSolution());

        mTSP.currentSolution.print(params.getNumSalesmen(), params.getVerbose(), true, true);
    }
}
