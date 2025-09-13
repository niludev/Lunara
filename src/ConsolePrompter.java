import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsolePrompter {

    private final Scanner scanner = new Scanner(System.in);

    public String promptUsername() {
        String username = "";
        System.out.print("---------------------------------\n");
//        Main.printRecords(Main.CSV_PATH);
        System.out.print("---------------------------------\n");

        System.out.printf("Please enter the following information:\n");

        while (username == null || username.trim().isEmpty()) {
            System.out.println("Username cannot be empty. Your username:");
            username = scanner.nextLine();
        }
        return username;
    };

    public LocalDate promptStartDate() {
        LocalDate startDate = null;

        while (startDate == null) {
            System.out.println("Period start date (Format: YYYY-MM-DD, e.g. 2025-08-01):");
            String startDateInput = scanner.nextLine();
            try {
                startDate = LocalDate.parse(startDateInput);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }
        return startDate;
    };

    public LocalDate promptEndDate() {
        LocalDate endDate = null;
        while (endDate == null) {
            System.out.println("Period end date (Format: YYYY-MM-DD, e.g. 2025-08-01):");
            String endDateInput = scanner.nextLine();
            try {
                endDate = LocalDate.parse(endDateInput);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }
        return endDate;
    };

    public int promptPainIntensity() {
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
        return painIntensity;
    };

    public int promptMood() {
        int mood = -1;
        while (mood < 1 || mood > 5) {
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
        return mood;
    };
}
