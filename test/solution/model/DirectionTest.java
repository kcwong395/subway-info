package solution.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void testToStringNormal() {
        assertEquals("Northbound", Direction.NORTH.toString());
        assertEquals("Southbound", Direction.SOUTH.toString());
        assertEquals("Eastbound", Direction.EAST.toString());
        assertEquals("Westbound", Direction.WEST.toString());
    }
}