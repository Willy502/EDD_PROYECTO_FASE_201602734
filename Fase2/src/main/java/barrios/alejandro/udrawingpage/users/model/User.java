package barrios.alejandro.udrawingpage.users.model;

public class User {

    public long dpi;
    private String name;
    private String password;

    public User(long dpi, String name, String password) {
        this.name = name;
        this.dpi = dpi;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
