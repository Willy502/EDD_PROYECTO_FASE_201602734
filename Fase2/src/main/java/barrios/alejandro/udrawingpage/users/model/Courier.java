package barrios.alejandro.udrawingpage.users.model;

public class Courier extends Person {

    private String lastname;
    private License license;
    private Gender gender;


    public Courier(long dpi, String name, String lastname, String phone, String address, License license, Gender gender) {
        super(dpi, name, phone, address);
        this.lastname = lastname;
        this.license = license;
        this.gender = gender;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
