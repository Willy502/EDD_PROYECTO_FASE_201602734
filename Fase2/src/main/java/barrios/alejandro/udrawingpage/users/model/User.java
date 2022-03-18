package barrios.alejandro.udrawingpage.users.model;

public class User {

    public int id;
    private String name;
    private String dpi;
    private String password;

    public User(int id, String name, String dpi, String password) {
        this.id = id;
        this.name = name;
        this.dpi = dpi;
        this.password = password;
    }

}
