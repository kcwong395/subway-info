package solution.model;

import solution.util.Parser;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Station {

    private final String stationName;

    private Map<Direction, List<Train>> schedule;

    private Map<Direction, Station> connected;

    public Station(String stationName) {
        this.stationName = stationName;
        schedule = new HashMap<>();
        connected = new HashMap<>();
    }

    /*
     * Time Complexity: O(logN) where N is the total number of train that arrive at this station with the indicated dir.
     */
    /**
     * Return the inserted index to the train schedule. <strong>The list must be maintained in a sorted order such that
     * the arrival time is in increasing order</strong>
     *
     * @param t the new train to be inserted
     * @return the inserted index
     * @throws IllegalArgumentException when it violates the assumption that trains will not arrive at the same time.
     */
    public int addSchedule(Train t) {
        Direction dir = t.getDirection();
        schedule.putIfAbsent(dir, new ArrayList<>());
        List<Train> curSchedule = schedule.get(dir);
        int idx = Collections.binarySearch(curSchedule, t, Comparator.comparing(Train::getArrivalTime));
        // Assume no train will arrive at the same time
        // Always maintain the list in sorted order
        if(idx > 0) {
            throw new IllegalArgumentException("Violation of assumption that: no train will arrive at the same time");
        }

        int insertIdx = Math.abs(idx + 1);
        curSchedule.add(insertIdx, t);
        return insertIdx;
    }

    /*
     * Time Complexity: O(logN) where N is the total number of train that arrive at this station with the indicated dir.
     */
    /**
     * Returns the next train according to the given time and direction.
     *
     * @param   time    the desired time.
     * @param   dir     the desired direction.
     * @return  the next {@code Train}, {@code null} if there is no
     * train coming after {@code time}.
     * @throws IllegalArgumentException when the specified direction is not available for that station
     */
    public Train getNextTrain(LocalDateTime time, Direction dir) {
        if(!schedule.containsKey(dir)) {
            throw new IllegalArgumentException(MessageFormat.format("{0} train is not available at {1}", dir.toString(), stationName));
        }

        List<Train> scheduleByDir = schedule.get(dir);
        Train trainToSearch = new Train(stationName, time, dir);
        int idx = Collections.binarySearch(scheduleByDir, trainToSearch, Comparator.comparing(Train::getArrivalTime));

        Train next = null;
        if(idx >= 0) {
            next = scheduleByDir.get(idx);
        }
        else if(Math.abs(idx + 1) < scheduleByDir.size()) {
            next = scheduleByDir.get(Math.abs(idx + 1));
        }
        return next;
    }

    /**
     * Get the human-readable expression of train schedule, with an interface that accept the time input as String
     *
     * @param   time    the desired time.
     * @param   dir     the desired direction.
     * @return  the next {@code Train} in string format
     */
    public String getNextTrain(String time, Direction dir) {
        LocalDateTime converted = LocalDateTime.parse(time, Parser.FORMATTER);
        try {
            Train next = getNextTrain(converted, dir);
            if(next == null) {
                return "No train available for the request time.";
            }
            return next.toString();
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    /*
     *  Time Complexity: O(N)
     */
    public List<Train> getSchedule() {
        return schedule.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public void connect(Direction dir, Station nextStation) {
        this.connected.put(dir, nextStation);
    }

}
