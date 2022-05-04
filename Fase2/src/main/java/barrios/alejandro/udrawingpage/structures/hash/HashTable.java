package barrios.alejandro.udrawingpage.structures.hash;

import barrios.alejandro.udrawingpage.structures.controller.SinglyLinkedList;

public class HashTable<T> {

    SinglyLinkedList<Entry> arrayHash;
    int size;

    public HashTable(int size) {
        this.size = size;
        arrayHash = new SinglyLinkedList<>();
        for (int i = 0; i < size; i++) {
            arrayHash.addToList(new Entry());
        }
    }

    int getHash(int key) {
        return key%size;
    }

    public void put(int key, Object value) {
        int hashIndex = getHash(key);
        Entry arrayValue = arrayHash.getPos(hashIndex);
        Entry newItem = new Entry(key, value);
        newItem.next = arrayValue.next;
        arrayValue.next = newItem;
    }

    public T get(int key) {
        T value = null;
        int hashIndex = getHash(key);
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

}
