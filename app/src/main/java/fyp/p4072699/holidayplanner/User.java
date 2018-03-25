package fyp.p4072699.holidayplanner;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;

    public User(String n, String e, String p) {
        name = n;
        email = e;
        password = p;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
