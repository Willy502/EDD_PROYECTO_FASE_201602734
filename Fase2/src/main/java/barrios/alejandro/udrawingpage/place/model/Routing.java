package barrios.alejandro.udrawingpage.place.model;

public class Routing {

    private Town inicio;
    private Town finale;
    private int weight;

    public Routing(Town inicio, Town finale, int weight) {
        this.inicio = inicio;
        this.finale = finale;
        this.weight = weight;
    }

    public Town getInicio() {
        return inicio;
    }

    public Town getFinale() {
        return finale;
    }

    public int getWeight() {
        return weight;
    }
}
