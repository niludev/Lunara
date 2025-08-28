import java.time.LocalDate;

public class Record {

    private String id = "";
    private String username;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer painIntensity;
    private Integer mood;

    public Record(String username, LocalDate startDate, LocalDate endDate, Integer painIntensity, Integer mood) {
        this.username = username;
        this.startDate = startDate;
        this.endDate = endDate;
        this.painIntensity = painIntensity;
        this.mood = mood;
    }

    public Record(String id, String username, LocalDate startDate, LocalDate endDate, Integer painIntensity, Integer mood) {
        this.id = id;
        this.username = username;
        this.startDate = startDate;
        this.endDate = endDate;
        this.painIntensity = painIntensity;
        this.mood = mood;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getPainIntensity() {
        return painIntensity;
    }

    public void setPainIntensity(Integer painIntensity) {
        this.painIntensity = painIntensity;
    }

    public Integer getMood() {
        return mood;
    }

    public void setMood(Integer mood) {
        this.mood = mood;
    }

//    public Record fromCsvLine(String line) {};

    @Override
    public String toString() {
        String newLine = String.join(",",
//                this.id == null ? "" : this.id.toString(),
                this.id,
                this.username,
                this.startDate.toString(),
                this.endDate.toString(),
                this.painIntensity.toString(),
                this.mood.toString()
        );
        return newLine;
    }
}
