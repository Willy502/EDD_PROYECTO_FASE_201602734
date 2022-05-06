package barrios.alejandro.udrawingpage.users.model;

import barrios.alejandro.udrawingpage.place.model.Order;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyLinkedList;

public class Courier extends Person {

    private String lastname;
    private String license;
    private Gender gender;
    private SinglyLinkedList<Order> orders;


    public Courier(long dpi, String name, String lastname, String address, String license, Gender gender) {
        super(dpi, name, address);
        this.lastname = lastname;
        this.license = license;
        this.gender = gender;
        orders = new SinglyLinkedList<>();
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public SinglyLinkedList<Order> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return name + " " + lastname;
    }
}
