package barrios.alejandro.udrawingpage.structures.hash;

import barrios.alejandro.udrawingpage.users.model.Courier;

public class Entry {

    long key;
    Courier value;
    Entry next;

    public Entry(long key, Courier value) {
        this.key = key;
        this.value = value;
        next = null;
    }

    public Entry() {
        next = null;
    }

    public long getKey() {
        return key;
    }

    public Courier getValue() {
        return value;
    }
}
