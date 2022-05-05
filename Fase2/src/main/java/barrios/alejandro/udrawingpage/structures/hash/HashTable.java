package barrios.alejandro.udrawingpage.structures.hash;

import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyLinkedList;

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

    long getHash(long key) {
        return key%size;
    }

    public void put(long key, Object value) {
        long hashIndex = getHash(key);
        Entry arrayValue = arrayHash.getPos(hashIndex);
        Entry newItem = new Entry(key, value);
        newItem.next = arrayValue.next;
        arrayValue.next = newItem;
    }

    public T get(int key) {
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

}
