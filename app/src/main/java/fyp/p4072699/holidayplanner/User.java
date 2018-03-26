package fyp.p4072699.holidayplanner;

public class User {
    private String name;
    private String email;

    public User(String n, String e) {
        name = n;
        email = e;
    }

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
