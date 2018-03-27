package fyp.p4072699.holidayplanner;

public class Holiday {
    private String location;
    private String dateFrom;
    private String dateTo;

    public Holiday(String l, String from, String to) {
        location = l;
        dateFrom = from;
        dateTo = to;
    }

    public Holiday() {

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
