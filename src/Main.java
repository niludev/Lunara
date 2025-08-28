import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Paths;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final Path CSV_PATH = Paths.get("/data/period.csv");
    private static final String[] CSV_HEADER = new String[]{
            "id",
            "username",
            "startDate",
            "endDate",
            "painIntensity",
            "mood",
    };

    public static void main(String[] args) {
        //docker compose run --rm -it lunara1

        Main.ensureCsvHeader(CSV_PATH, String.join(",", CSV_HEADER));

        Scanner scanner = new Scanner(System.in);

        System.out.print("---------------------------------\n");
        Main.printRecords(CSV_PATH);
        System.out.print("---------------------------------\n");

        System.out.printf("Please enter the following information:\n");

        System.out.println("your username:");
        String userName = scanner.nextLine();

        LocalDate startDate = null;

        while (startDate == null) {
            System.out.println("Period start date (E.g. 2025-08-01):");
            String startDateInput = scanner.nextLine();
            try {
                startDate = LocalDate.parse(startDateInput);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }

        LocalDate endDate = null;
        while (endDate == null) {
            System.out.println("Period end date (E.g. 2025-08-01):");
            String endDateInput = scanner.nextLine();
            try {
                endDate = LocalDate.parse(endDateInput);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }

        int painIntensity = -1;
        while (painIntensity < 1 || painIntensity > 10) {
            System.out.println("Pain intensity (From 1 to 10):");
            String painIntensityInput = scanner.nextLine();
            try {
                painIntensity = Integer.parseInt(painIntensityInput);
                if (painIntensity < 1 || painIntensity > 10) {
                    System.out.println("Value out of range. Try between 1 and 10:");
                }
            } catch(NumberFormatException e) {
                System.out.println("Invalid input! Please enter a digit.");
            }
        }

        int mood = -1;
        while (mood < 0 || mood > 5) {
            System.out.println("Your mood (From 1 to 5):");
            String moodInput = scanner.nextLine();
            try {
                mood = Integer.parseInt(moodInput);
                if (mood < 1 || mood > 5) {
                    System.out.println("Value out of range. Try between 1 and 5:");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a digit.");
            }
        }

        System.out.println("Thank you! Here's your data:");
        System.out.println("Username: " + userName);
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);
        System.out.println("Pain Intensity: " + painIntensity);
        System.out.println("Mood: " + mood);

        String record = String.format(
                "%s,%s,%s,%d,%d\n",
                userName, startDate, endDate, painIntensity, mood
        );
        Main.appendRecord(CSV_PATH, record);

        System.out.print("---------------------------------\n");
        Main.printRecords(CSV_PATH);
        System.out.print("---------------------------------\n");
    }

    public static void ensureCsvHeader(Path path, String headers) {

        try {
            Path parentPath = path.getParent();

            if (parentPath != null) {
                Files.createDirectories(parentPath);
            };

            if (Files.notExists(path)) {
                Files.write(path, headers.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
            } else {
                long size = Files.size(path);
                if (size == 0) {
                    Files.write(path, headers.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                };
            }
        } catch (IOException e) {
            System.err.println("Error while trying to write file: " + e.getMessage());
        }

    };

    public static void printRecords(Path path) {
        if (Files.exists(path)) {
            try {
                List<String> lines = Files.readAllLines(path);

                for (String line : lines) {
                    System.out.println(line);
                }
            }  catch (IOException e) {
                System.err.println("Error while trying to read file: " + e.getMessage());
            }
        } else  {
            System.err.println("No such file or directory: " + path);
        }
    }

    public static void appendRecord (Path path, String lineWithNewLine) {
        try {
            Files.write(
                    path,
                    lineWithNewLine.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e ) {
            System.out.println("Failed to append record: " + e.getMessage());
        }
    }
}