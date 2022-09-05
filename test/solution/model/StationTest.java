package solution.model;

import org.junit.jupiter.api.Test;
import solution.util.Generator;
import solution.util.Parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StationTest {

    private Map<String, Station> stations = Generator.initSchedule("test/data/mockdata.txt");

    @Test
    void addSchedule() {
        Station donMills = stations.get("Don Mills");
        assertNotNull(donMills);

        Train t;

        // train added to last
        t = new Train("Don Mills", "2022-08-30 20:00", Direction.WEST);
        donMills.addSchedule(t);
        List<Train> schedule = donMills.getSchedule();
        int idx = donMills.getSchedule().indexOf(t);
        assertTrue(idx >= 0);
        assertTrue(idx == schedule.size() - 1 || schedule.get(idx + 1).getDirection() != t.getDirection());

        // train added to middle
        t = new Train("Don Mills", "2022-08-30 07:20", Direction.WEST);
        donMills.addSchedule(t);
        schedule = donMills.getSchedule();
        idx = schedule.indexOf(t);
        assertTrue(idx > 0);
        assertTrue(
            schedule.get(idx - 1).getArrivalTime().isBefore(t.getArrivalTime()) &&
            schedule.get(idx + 1).getArrivalTime().isAfter(t.getArrivalTime())
        );

        // train added to front
        t = new Train("Don Mills", "2022-08-30 05:20", Direction.WEST);
        donMills.addSchedule(t);
        schedule = donMills.getSchedule();
        idx = schedule.indexOf(t);
        assertTrue(idx >= 0);
        assertTrue(idx == 0 || donMills.getSchedule().get(idx - 1).getDirection() != Direction.WEST);
    }

    @Test
    void addScheduleNull() {
        Station donMills = stations.get("Don Mills");
        assertNotNull(donMills);

        Train t = null;

        int originalSize = donMills.getSchedule().size();

        assertDoesNotThrow(() -> donMills.addSchedule(t));

        assertEquals(originalSize, donMills.getSchedule().size());
    }

    @Test
    void getNextTrainNoDir() {
        Station dundas = stations.get("Dundas");
        assertNotNull(dundas);
        LocalDateTime query = LocalDateTime.parse("2022-08-30 08:38", Parser.FORMATTER);
        assertThrows(
            IllegalArgumentException.class,
            () -> dundas.getNextTrain(query, Direction.WEST)
        );
    }

    @Test
    void getNextTrainNotAvailable() {
        Station dundas = stations.get("Dundas");
        assertNotNull(dundas);
        assertNull(dundas.getNextTrain(LocalDateTime.parse("2022-08-30 22:38", Parser.FORMATTER), Direction.NORTH));
        assertNotNull(dundas.getNextTrain(LocalDateTime.parse("2022-08-30 08:38", Parser.FORMATTER), Direction.NORTH));
    }

    @Test
    void getNextTrainNull() {
        Station dundas = stations.get("Dundas");
        assertNotNull(dundas);
        assertNull(dundas.getNextTrain(LocalDateTime.parse("2022-08-30 22:38", Parser.FORMATTER), null));
    }

    @Test
    void testGetNextTrainNoDir() {
        Station donMills = stations.get("Don Mills");
        assertNotNull(donMills);
        assertEquals("Southbound train is not available at Don Mills", donMills.getNextTrain("2022-08-30 08:38", Direction.SOUTH));

        Station dundas = stations.get("Dundas");
        assertNotNull(dundas);
        assertEquals("Westbound train is not available at Dundas", dundas.getNextTrain("2022-08-30 08:38", Direction.WEST));
    }

    @Test
    void testGetNextTrain() {
        Station donMills = stations.get("Don Mills");
        assertNotNull(donMills);
        assertEquals("Westbound train to Don Mills will arrive at 2022-08-30 06:00.", donMills.getNextTrain("2022-08-30 05:38", Direction.WEST));

        Station dundas = stations.get("Dundas");
        assertNotNull(dundas);
        assertEquals("Northbound train to Dundas will arrive at 2022-08-30 06:31.", dundas.getNextTrain("2022-08-30 06:20", Direction.NORTH));
    }

    @Test
    void testGetNextTrainNotAvailable() {
        Station donMills = stations.get("Don Mills");
        assertNotNull(donMills);
        assertEquals("No train available for the request time.", donMills.getNextTrain("2022-08-30 22:38", Direction.WEST));

        Station dundas = stations.get("Dundas");
        assertNotNull(dundas);
        assertEquals("No train available for the request time.", dundas.getNextTrain("2022-08-30 21:20", Direction.NORTH));
    }

    @Test
    void testGetNextTrainNull() {
        Station donMills = stations.get("Don Mills");
        assertNotNull(donMills);
        assertNull(donMills.getNextTrain("2022-08-30 10:12", null));
    }

    @Test
    void testGetNextTrainInvalid() {
        Station donMills = stations.get("Don Mills");
        assertNotNull(donMills);
        assertEquals("Invalid date time format", donMills.getNextTrain("2022-08-30", Direction.EAST));
    }

    @Test
    void getSchedule() {
        Station donMills = stations.get("Don Mills");
        assertNotNull(donMills);
        assertEquals(Generator.NUM_OF_TRAIN * 2, donMills.getSchedule().size());

        Station sheppardYonge = stations.get("Sheppard-Yonge");
        assertNotNull(sheppardYonge);
        assertEquals(Generator.NUM_OF_TRAIN * 4, sheppardYonge.getSchedule().size());
    }
}