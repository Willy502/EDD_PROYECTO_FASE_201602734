package barrios.alejandro.udrawingpage.structures.hash;

import barrios.alejandro.udrawingpage.graph.Graph;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyLinkedList;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyNode;
import barrios.alejandro.udrawingpage.users.model.Courier;

public class HashTable<T> {

    SinglyLinkedList<Entry> arrayHash;
    int size;
    private String result;

    public HashTable(int size) {
        this.size = size;
        arrayHash = new SinglyLinkedList<>();
        for (int i = 0; i < size; i++) {
            arrayHash.addToList(new Entry());
        }
    }

    long getHash(long key) {
        return key%size;
    }

    public void put(long key, Courier value) {
        long hashIndex = getHash(key);
        Entry arrayValue = arrayHash.getPos(hashIndex);
        Entry newItem = new Entry(key, value);
        newItem.next = arrayValue.next;
        arrayValue.next = newItem;
    }

    public T get(long key) {
        T value = null;
        long hashIndex = getHash(key);
        Entry arrayValue = arrayHash.getPos(hashIndex);
        while (arrayValue != null) {
            if (arrayValue.getKey() == key) {
                value = (T) arrayValue.getValue();
                break;
            }
            arrayValue = arrayValue.next;
        }

        return value;
    }

    public void graphHashTable() {
        result = "digraph G {\n";
        result += "node[shape=box];\n";


        for (int i = 0; i < arrayHash.size(); i++) {
            result += "a" +(i + 1) + "[label=\"" + (i + 1) + "\" group=" + (i + 1) + "];\n";
        }

        // Basic connections
        for (int i = 0; i < arrayHash.size() - 1; i++) {
            result += "a" + (i + 1) +" -> " + "a" + (i + 2) + ";\n";
        }

        // Sub graphs
        for (int i = 0; i < arrayHash.size(); i++) {
            Entry entry = arrayHash.getPos(i);
            while (entry != null) {
                result += "a" + (i + 1) + "a" + entry.getKey() + "[label=\"" + entry.getKey() + "\" group=" + (i + 1) + "];\n";
                entry = entry.next;
            }

            // Connections
            entry = arrayHash.getPos(i);
            result += "a" + (i + 1) + " -> " + "a" + (i + 1) + "a" + entry.getKey() + ";\n";
            while (entry != null) {

                if (entry.next != null) {
                    result += "a" + (i + 1) + "a" + entry.getKey() + " -> " + "a" + (i + 1) + "a" + entry.next.getKey() + ";\n";
                }
                entry = entry.next;

            }

            result += " { rank=same;\n";
            result += "a" + (i + 1) + ";\n";
            entry = arrayHash.getPos(i);
            while (entry != null) {
                result += "a" + (i + 1) + "a" + entry.getKey() + ";\n";
                entry = entry.next;
            }
            result += "};\n";

        }

        result += "}\n";
        Graph.GenerarImagen("COURIER_HASH", result);
    }

}
