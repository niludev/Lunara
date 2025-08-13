import java.util.Date;

public class Record {
    private Integer id = null;
    private String username;
    private Date startDate;
    private Date endDate;
    private Integer painIntensity;
    private Integer mood;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

    @Override
    public String toString() {
        return String.join(",", new String[]{
                this.id.toString(),
                this.username,
                this.startDate.toString(),
                this.endDate.toString(),
                this.painIntensity.toString(),
                this.mood.toString()
        });
    }
}
