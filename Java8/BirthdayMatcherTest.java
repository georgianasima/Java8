package org.Java8;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
public class BirthdayMatcherTest {
    @Test
    public void testFindPersonsByMonth() throws IOException {
        Path inputFile = Files.createTempFile("testInput", ".txt");
        Files.write(inputFile, List.of(
                "John, Doe, 1990-05-01",
                "Jane, Smith, 1985-05-15",
                "Michael, Johnson, 1992-06-10",
                "Alice, Brown, 1990-05-20",
                "Robert, Davis, 1991-07-25"
        ));
        int targetMonth = 5;
        List<Person> persons = BirthdayMatcher.findPersonsByMonth(inputFile.toString(), targetMonth);
        Assertions.assertEquals(3, persons.size());
        Assertions.assertTrue(persons.stream().allMatch(person -> person.getBirthDate().getMonthValue() == targetMonth));
    }
    @Test
    public void testWriteToFile() throws IOException {
        Path outputFile = Files.createTempFile("testOutput", ".txt");
        List<Person> persons = List.of(
                new Person("John", "Doe", "1990-05-01"),
                new Person("Jane", "Smith", "1985-05-15"),
                new Person("Michael", "Johnson", "1992-06-10")
        );
        BirthdayMatcher.writeToFile(persons, outputFile.toString());
        List<String> lines = Files.readAllLines(outputFile);

        Assertions.assertEquals(3, lines.size());
        Assertions.assertEquals("John, Doe", lines.get(0));
        Assertions.assertEquals("Jane, Smith", lines.get(1));
        Assertions.assertEquals("Michael, Johnson", lines.get(2));
    }
}
