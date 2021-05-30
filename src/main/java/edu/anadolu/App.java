package edu.anadolu;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;
import edu.anadolu.core.Solution;
import edu.anadolu.core.mTSP;
import edu.anadolu.utils.Params;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
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

        mTSP.currentSolution.print(params.getNumSalesmen());
        Solution sol = mTSP.currentSolution;

//       sol.print(params.getNumSalesmen());

        List<String> routes = new ArrayList<>();

        for (int i = 0; i < sol.routes.size(); i++) {
            StringBuilder stringBuilder = new StringBuilder();
            StringJoiner stringJoiner = new StringJoiner(" ");
            if ((i % params.getNumSalesmen()) == 0) {

                stringBuilder.append("{depots : \"").append(sol.depots.get(i / params.getNumSalesmen())).append("\",");
            }


            for (int j = 0; j < sol.routes.get(i).size(); j++) {

                if (j != 0 && j != sol.routes.get(i).size() - 1) {
                    stringJoiner.add(String.valueOf(sol.routes.get(i).get(j)));

                }


            }
            //StringBuilder ready

            if (i % params.getNumSalesmen() != 0) {

            }
            routes.add(stringJoiner.toString());
            stringBuilder.append(stringJoiner);


            stringBuilder.append("}");

        }


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"" + "solution" + "\":[");

        for (int i = 0; i < sol.routes.size(); i++) {
            final List<Integer> list = sol.routes.get(i);

            if ((i % params.getNumSalesmen()) == 0) {
                stringBuilder.append("{\"depot\": \"" + sol.depots.get(i / params.getNumSalesmen()) + "\",");
                stringBuilder.append("\"routes\":" + "[");
            }


            stringBuilder.append(",\"");

            stringBuilder.append(routes.get(i));
            stringBuilder.append("\"");

            if ((i % params.getNumSalesmen() == params.getNumSalesmen() - 1)) {
                stringBuilder.append("]");
                stringBuilder.append("}");
            }

        }
        stringBuilder.append("]}");

        final String replace = stringBuilder.toString().replace("[,", "[").replace("}{", "},{");


        try {
            Files.write(Paths.get("solution_d" + params.getNumDepots() + "s" + params.getNumSalesmen() + ".json"), replace.getBytes());
        } catch (IOException e) {
            System.err.println("Unable to write json");
            e.printStackTrace();
        }


        System.out.println("**Total cost is " + mTSP.currentSolution.cost);


        System.out.println("{");
        System.out.println("    \"swapHubWithNodeInRoute\": " + mTSP.swapHubWithNodeInRoute + ",");
        System.out.println("    \"insertNodeBetweenRoutes\": " + mTSP.insertNodeBetweenRoutes + ",");
        System.out.println("    \"swapNodesInRoute\": " + mTSP.swapNodesInRoute + ",");
        System.out.println("    \"swapNodesBetweenRoutes\": " + mTSP.swapNodesBetweenRoutes + ",");
        System.out.println("    \"insertNodeInRoute\": " + mTSP.insertNodeInRoute);
        System.out.println("}");


    }
}
