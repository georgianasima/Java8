package org.Java8;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
public class BirthdayMatcher {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java BirthdayMatcher <inputFile> <targetMonth> <outputFile>");
            return;
        }
        String inputFile = args[0];
        int targetMonth = Integer.parseInt(args[1]);
        String outputFile = args[2];

        try {
            List<Person> matchedPersons = findPersonsByMonth(inputFile, targetMonth);
            writeToFile(matchedPersons, outputFile);
            System.out.println("Matching persons have been written to the output file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    static List<Person> findPersonsByMonth(String inputFile, int targetMonth) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            return reader.lines()
                    .map(line -> line.split(","))
                    .filter(parts -> parts.length == 3)
                    .map(parts -> new Person(parts[0].trim(), parts[1].trim(), parts[2].trim()))
                    .filter(person -> person.getBirthDate().getMonthValue() == targetMonth)
                    .sorted(Comparator.comparing(Person::getLastName).thenComparing(Person::getFirstName))
                    .collect(Collectors.toList());
        }
    }
    static void writeToFile(List<Person> persons, String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            persons.forEach(person -> {
                try {
                    writer.write(person.getFirstName() + ", " + person.getLastName());
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
class Person {
    private final String firstName;
    private final String lastName;
    private final LocalDate birthDate;
    public Person(String firstName, String lastName, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = LocalDate.parse(birthDate);
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
}
