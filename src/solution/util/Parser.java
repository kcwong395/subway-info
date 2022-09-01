package solution.util;

import solution.model.Direction;
import solution.model.Train;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {

    private Parser() {}

    public static final String DEFAULTPATH = "src/data/mockdata.txt";

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static List<String> parseFromFile(String path) {
        List<String> schedule = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while((line = br.readLine()) != null) {
                schedule.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    public static Train parseTrainFromLine(String line) {
        String[] info = line.split(",");

        if(info.length != 3) {
            throw new IllegalArgumentException("Invalid train information");
        }

        return new Train(info[0], LocalDateTime.parse(info[1], FORMATTER), Direction.valueOf(info[2].toUpperCase()));
    }

    public static List<Train> getSchedule(List<String> strSchedule){
        return strSchedule.stream()
                .map(Parser::parseTrainFromLine)
                .collect(Collectors.toList());
    }
 }
