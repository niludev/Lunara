import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.nio.file.Paths;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final Path CSV_PATH = Paths.get("/data/period.csv");
    private static final String[] CSV_HEADER = new String[]{
            "id",
            "date",
            "painIntensity",
            "mood",
    };

    public static void main(String[] args) {


        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://db:5432/lunara",
                    "appuser",
                    "apppass"
            );

//            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (name, email) VALUES (?, ?)");
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM period");

//            int rows = stmt.executeUpdate("DELETE FROM users WHERE id = 1");

            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDate date = rs.getDate("date").toLocalDate();
                int painIntensity = rs.getInt("painIntensity");
                int mood = rs.getInt("mood");

                System.out.println(id + " - " + date + " - " + painIntensity + " - " + mood);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }





//        RecordRepository repo = new RecordRepository(CSV_PATH);
//        ConsolePrompter prompter = new ConsolePrompter();
//
//        repo.ensureCsvHeader(CSV_HEADER);
//
//        String username = prompter.promptUsername();
//        LocalDate startDate = prompter.promptStartDate();
//        LocalDate endDate = prompter.promptEndDate();
//        int painIntensity = prompter.promptPainIntensity();
//        int mood = prompter.promptMood();
//
//        System.out.println("Thank you! Here's your data:");
//        System.out.println("Username: " + username);
//        System.out.println("Start Date: " + startDate);
//        System.out.println("End Date: " + endDate);
//        System.out.println("Pain Intensity: " + painIntensity);
//        System.out.println("Mood: " + mood);
//
//        Record record = new Record(username, startDate, endDate, painIntensity, mood);
//        repo.createCSVRecord(record);
//
//        System.out.print("---------------------------------\n");
//        repo.printAllRecords();
//        System.out.print("---------------------------------\n");
    }
}