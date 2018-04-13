package fyp.p4072699.holidayplanner;

public class Holiday {
    private String location, toDay, toMonth, toYear, fromDay, fromMonth, fromYear, completed;
    private double lon, lat;

    public Holiday(String l, double la, double lo, String td, String tm, String ty, String fd, String fm, String fy, String c) {
        location = l;
        lon = lo;
        lat = la;
        toDay = td;
        toMonth = tm;
        toYear = ty;
        fromDay = fd;
        fromMonth = fm;
        fromYear = fy;
        completed = c;
    }

    public Holiday() {

    }

    public String getToDay() {
        return toDay;
    }

    public void setToDay(String toDay) {
        this.toDay = toDay;
    }

    public String getToMonth() {
        return toMonth;
    }

    public void setToMonth(String toMonth) {
        this.toMonth = toMonth;
    }

    public String getToYear() {
        return toYear;
    }

    public void setToYear(String toYear) {
        this.toYear = toYear;
    }

    public String getFromDay() {
        return fromDay;
    }

    public void setFromDay(String fromDay) {
        this.fromDay = fromDay;
    }

    public String getFromMonth() {
        return fromMonth;
    }

    public void setFromMonth(String fromMonth) {
        this.fromMonth = fromMonth;
    }

    public String getFromYear() {
        return fromYear;
    }

    public void setFromYear(String fromYear) {
        this.fromYear = fromYear;
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
}
