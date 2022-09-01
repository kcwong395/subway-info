package solution.util;

import solution.model.Direction;
import solution.model.Station;
import solution.model.Train;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class Generator {

    private Generator() {}

    public static final String[][] stationNames = new String[][]{
        new String[]{
            "Vaughan Metropolitan Centre", "Highway 407", "Pioneer Village", "York University", "Finch West",
            "Downsview Park", "Sheppard West", "Wilson", "Yorkdale", "Lawrence West", "Glencairn", "Eglinton West",
            "St. Clair West", "Dupont", "Spadina", "St. George", "Museum", "Queen's Park", "St. Patrick", "Osgoode",
            "St. Andrew", "Union", "King", "Queen", "Dundas", "College", "Wellesley", "Bloor-Yonge", "Rosedale",
            "Summerhill", "St. Clair", "Davisville", "Eglinton", "Lawrence", "York Mills", "Sheppard-Yonge",
            "North York Centre", "Finch"
        }, // line 1: north - south
        new String[]{
            "Kipling", "Islington", "Royal York", "Old Mill", "Jane", "Runnymede", "High Park", "Keele", "Dundas West",
            "Lansdowne", "Dufferin", "Ossington", "Christie", "Bathurst", "Spadina", "St. George", "Bay", "Bloor-Yonge",
            "Sherbounme", "Castle Frank", "Broadview", "Chester", "Pape", "Donlands", "Greenwood", "Coxwell",
            "Woodbine", "Main Street", "Victoria Park", "Warden", "Kennedy"
        }, // line 2: west - east
        new String[]{
            "McCowan", "Scarborough Centre", "Midland", "Ellesmere", "Lawrence East", "Kennedy"
        }, // line 3: north - south
        new String[]{
            "Sheppard-Yonge", "Bayview", "Bessarion", "Leslie", "Don Mills"
        }, // line 4: west - east
    };

    public static final int NUM_OF_TRAIN = 10; // number of trains per day
    public static final int INTERVAL_OF_TRAIN = 60; // 60 mins

    private static final int UPPER_BOUND_INTERVAL = 3;

    private static final String CUR_DATE = "2022-08-30 00:00";

    private static final Random rd = new Random(0);

    private static final String[][] dirs = new String[][]{
        new String[]{"South", "North"},
        new String[]{"East", "West"},
    };

    public static List<String> initMockData(String[][] stationNames) {
        List<String> schedule = new ArrayList<>();

        StringBuilder sb = new StringBuilder();

        LocalDateTime startTime = LocalDateTime.parse(CUR_DATE, Parser.FORMATTER).withHour(6).withMinute(0);

        // process each line
        for(int i = 0; i < stationNames.length; i++) {

            LocalDateTime curTime = startTime;
            int[] ranIntervalOfStation = getRandInterval(stationNames[i].length - 1, UPPER_BOUND_INTERVAL);

            // Every day there are NUM_OF_TRAIN trains departing the terminal stations, each separate by intervalOfTrain hour(s)
            for(int k = 0; k < NUM_OF_TRAIN; k++) {

                if(k != 0) {
                    curTime = curTime.plusMinutes(INTERVAL_OF_TRAIN);
                }
                LocalDateTime arrivalTime = curTime;

                // from terminal 1 to terminal 2
                for(int j = 0; j < stationNames[i].length; j++) {
                    if(j != 0) {
                        arrivalTime = arrivalTime.plusMinutes(ranIntervalOfStation[j - 1]);
                    }
                    sb.append(stationNames[i][j]).append(",").append(arrivalTime.format(Parser.FORMATTER)).append(",").append(dirs[i % 2][0]);
                    schedule.add(sb.toString());
                    sb.setLength(0);
                }

                arrivalTime = curTime;

                // from terminal 2 to terminal 1
                for(int j = stationNames[i].length - 1; j >= 0; j--) {
                    if(j != stationNames[i].length - 1) {
                        arrivalTime = arrivalTime.plusMinutes(ranIntervalOfStation[j]);
                    }
                    sb.append(stationNames[i][j]).append(",").append(arrivalTime.format(Parser.FORMATTER)).append(",").append(dirs[i % 2][1]);
                    schedule.add(sb.toString());
                    sb.setLength(0);
                }
            }

        }
        return schedule;
    }

    // upper bound interval is inclusive
    private static int[] getRandInterval(int num, int upperBoundInterval) {
        if(num < 0) {
            return new int[0];
        }

        int[] ranIntervalOfStation = new int[num];
        for(int k = 0; k < num; k++) {
            ranIntervalOfStation[k] = rd.nextInt(upperBoundInterval) + 1;
        }

        return ranIntervalOfStation;
    }

    public static void outputMockData(String path, String content) {
        try (FileWriter fw = new FileWriter(path)) {
            fw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Station> initStations(List<Train> trains) {
        Map<String, Station> stationMap = new HashMap<>();
        for(Train t: trains) {
            String stationName = t.getStation();
            stationMap.putIfAbsent(stationName, new Station(stationName));
            stationMap.get(stationName).addSchedule(t);
        }
        return stationMap;
    }

    public static void connectStations(Map<String, Station> stationMap) {
        for(int i = 0; i < stationNames.length; i++) {

            String[] line = stationNames[i];

            for(int j = 0; j < line.length - 1; j++) {
                Station station = stationMap.get(line[j]);
                station.connect(Direction.valueOf(dirs[i % 2][0].toUpperCase()), stationMap.get(line[j + 1]));
            }

            for(int j = line.length - 1; j > 0; j--) {
                Station station = stationMap.get(line[j]);
                station.connect(Direction.valueOf(dirs[i % 2][1].toUpperCase()), stationMap.get(line[j - 1]));
            }
        }
    }

    public static Map<String, Station> initSchedule(String path) {
        List<String> strSchedule = Parser.parseFromFile(path);
        List<Train> schedule = Parser.getSchedule(strSchedule);
        Map<String, Station> stationMap = initStations(schedule);
        connectStations(stationMap);
        return stationMap;
    }
}
