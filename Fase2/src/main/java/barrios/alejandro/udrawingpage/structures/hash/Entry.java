package barrios.alejandro.udrawingpage.structures.hash;

public class Entry {

    long key;
    Object value;
    Entry next;

    public Entry(long key, Object value) {
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

    public Object getValue() {
        return value;
    }
}
