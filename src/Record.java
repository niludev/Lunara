import java.time.LocalDate;

public class Record {

    private Integer id;
    private LocalDate date;
    private Integer painIntensity;
    private Integer mood;

    public Record(LocalDate date, Integer painIntensity, Integer mood) {
        this.date = date;
        this.painIntensity = painIntensity;
        this.mood = mood;
    }

    public Record(Integer id, LocalDate date, Integer painIntensity, Integer mood) {
        this.id = id;
        this.date = date;
        this.painIntensity = painIntensity;
        this.mood = mood;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
                this.id == null ? "" : this.id.toString(),
                this.date.toString(),
                this.painIntensity.toString(),
                this.mood.toString()
        );
        return newLine;
    }
}
