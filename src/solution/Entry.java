package solution;

import solution.model.Direction;
import solution.model.Station;
import solution.model.Train;
import solution.util.Generator;
import solution.util.Parser;

import java.util.*;
import java.util.logging.Logger;

public class Entry {
    private static final Logger LOG;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
            "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        LOG = Logger.getLogger(Entry.class.getName());
    }

    public static void main(String[] args) {
        // Prepare the mock data
        List<String> ss = Generator.initMockData(Generator.stationNames);
        Generator.outputMockData(Parser.DEFAULTPATH, String.join("\n", ss));

        // Parse Schedule from mock data
        Map<String, Station> stations = Generator.initSchedule(Parser.DEFAULTPATH);

        // Schedule look up
        LOG.info("Schedule for Don Mills");
        for(Train t: stations.get("Don Mills").getSchedule()) {
            LOG.info(t.toString());
        }

        // Schedule look up
        LOG.info("Schedule for Sheppard-Yonge");
        for(Train t: stations.get("Sheppard-Yonge").getSchedule()) {
            LOG.info(t.toString());
        }

        // Find next train available
        LOG.info("Next train available:");

        Station jane = stations.get("Jane");
        LOG.info(jane.getNextTrain("2022-08-30 08:38", Direction.EAST));

        LOG.info(jane.getNextTrain("2022-08-30 12:38", Direction.WEST));

        // No train available after this request time
        LOG.info(jane.getNextTrain("2022-08-30 20:38", Direction.EAST));

        // This direction is not available
        LOG.info(jane.getNextTrain("2022-08-30 08:38", Direction.SOUTH));
    }
}
