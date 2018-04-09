package fyp.p4072699.holidayplanner;

public class Holiday {
    private String location, dateFrom, dateTo, completed;
    private double lon, lat;

    public Holiday(String l, String from, String to, double la, double lo, String c) {
        location = l;
        dateFrom = from;
        dateTo = to;
        lon = lo;
        lat = la;
        completed = c;
    }

    public Holiday() {

    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
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
