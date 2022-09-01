package solution.model;

import solution.util.Parser;

import java.text.MessageFormat;
import java.time.LocalDateTime;

public class Train {
    private String station;
    private Direction direction;
    private LocalDateTime arrivalTime;

    public Train(String station, LocalDateTime arrivalTime, Direction direction) {
        this.station = station;
        this.arrivalTime = arrivalTime;
        this.direction = direction;
    }

    public Train(String station, String arrivalTime, Direction direction) {
        this.station = station;
        this.arrivalTime = LocalDateTime.parse(arrivalTime, Parser.FORMATTER);
        this.direction = direction;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public String getStation() {
        return station;
    }

    public Direction getDirection() {
        return direction;
    }

    public String toString() {
        return MessageFormat.format(
            "{0} train to {1} will arrive at {2}.",
            direction.toString(),
            station,
            arrivalTime.format(Parser.FORMATTER)
        );
    }
}
