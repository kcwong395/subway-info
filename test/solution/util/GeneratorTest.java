package solution.util;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GeneratorTest {

    @Test
    void initMockData() {
        List<String> ss = Generator.initMockData(Generator.stationNames);
        Generator.outputMockData("test/data/mockdata.txt", String.join("\n", ss));
        File file = new File("test/data/mockdata.txt");
        assertTrue(file.exists());
    }
}