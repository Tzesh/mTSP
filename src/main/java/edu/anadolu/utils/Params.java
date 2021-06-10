package edu.anadolu.utils;

import com.lexicalscope.jewel.cli.Option;

public interface Params {

    @Option(description = "number of depots", shortName = "d", longName = "depots", defaultValue = "5")
    int getNumDepots();

    @Option(description = "number of salesmen per depot", shortName = {"s"}, longName = {"salesmen", "vehicles"}, defaultValue = "2")
    int getNumSalesmen();

    @Option(description = "use city names when displaying/printing", shortName = "v", longName = "verbose")
    boolean getVerbose();

    @Option(description = "number of iterations (4 different solutions per each)", shortName = "i", longName = "iteration", defaultValue = "1")
    int getNumberOfIterations();

    @Option(description = "plate number of city which will be the main(initial) depot of NN", shortName = "p", longName = "mainDepot", defaultValue = "38")
    int getPlateNumberOfMainDepot();

    @Option(description = "maintain the main depot as main depot in NN solutions", shortName = "m", longName = "maintain")
    boolean getMaintainMain();

    @Option(helpRequest = true, description = "display help", shortName = "h")
    boolean getHelp();

}
