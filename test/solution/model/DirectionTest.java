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

    @Test
    void testToStringUnion() {
        assertEquals("Northbound towards Finch", Direction.NORTHTOFINCH.toString());
        assertEquals("Northbound towards Vaughan Metropolitan Centre", Direction.NORTHTOVAUGHAN.toString());
    }
}