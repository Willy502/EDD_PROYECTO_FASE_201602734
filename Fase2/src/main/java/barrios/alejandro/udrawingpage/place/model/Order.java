package barrios.alejandro.udrawingpage.place.model;

import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyLinkedList;
import barrios.alejandro.udrawingpage.users.model.Courier;
import barrios.alejandro.udrawingpage.users.model.User;

public class Order {

    private Courier mensajero;
    private Town sucursal;
    private User user;
    private SinglyLinkedList<Route> route;
    private int totalWeight;

    public Order(User user, SinglyLinkedList<Route> route, int totalWeight, Courier mensajero, Town sucursal) {
        this.user = user;
        this.route = route;
        this.totalWeight = totalWeight;
        this.mensajero = mensajero;
        this.sucursal = sucursal;
    }

    public User getUser() {
        return user;
    }

    public SinglyLinkedList<Route> getRoute() {
        return route;
    }

    public int getTotalWeight() {
        return totalWeight;
    }
}
