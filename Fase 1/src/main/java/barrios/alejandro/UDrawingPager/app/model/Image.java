package barrios.alejandro.UDrawingPager.app.model;

public class Image {

    private final PType type;
    private final Client client;

    public Image(PType type, Client client) {
        this.type = type;
        this.client = client;
    }

    public PType getType() {
        return type;
    }

    public Client getClient() {
        return client;
    }
}
