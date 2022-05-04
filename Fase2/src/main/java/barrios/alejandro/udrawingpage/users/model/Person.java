package barrios.alejandro.udrawingpage.users.model;

public class Person {

    protected long dpi;
    protected String name;
    protected String password;
    protected String phone;
    protected String address;

    public Person() {}

    public Person(long dpi, String name, String address) {
        this.dpi = dpi;
        this.name = name;
        this.address = address;
    }

    public Person(long dpi, String name, String password, String phone, String address) {
        this.dpi = dpi;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    public long getDpi() {
        return dpi;
    }

    public void setDpi(long dpi) {
        this.dpi = dpi;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
