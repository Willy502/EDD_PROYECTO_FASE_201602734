package barrios.alejandro.udrawingpage.place.model;

public class Route {

    private Town town;
    private int weight;

    public Route(Town town, int weight) {
        this.town = town;
        this.weight = weight;
    }

    public Town getTown() {
        return town;
    }

    public int getWeight() {
        return weight;
    }
}
