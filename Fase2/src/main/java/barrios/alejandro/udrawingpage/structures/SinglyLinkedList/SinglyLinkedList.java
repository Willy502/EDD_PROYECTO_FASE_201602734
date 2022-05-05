package barrios.alejandro.udrawingpage.structures.SinglyLinkedList;

public class SinglyLinkedList<T> {

    private SinglyNode<T> head;
    private int size = 0;

    public void addToList(T data) {
        if (head == null) {
            this.head = new SinglyNode<T>(data);
            size++;
            return;
        }
        SinglyNode<T> current = this.head;
        while (current.next != null) current = current.next;
        current.next = new SinglyNode<T>(data);
        size++;
    }

    public void copyList(SinglyLinkedList<T> data) {
        head = null;
        size = 0;

        for (SinglyNode<T> node = data.getHead(); node != null; node = node.next) {
            if (head == null) {
                head = new SinglyNode<T>(node.data);
                size++;
                continue;
            }
            SinglyNode<T> current = this.head;
            while (current.next != null) current = current.next;
            current.next = new SinglyNode<T>(node.data);
            size++;
        }
    }

    public SinglyNode<T> getHead() {
        return head;
    }

    public T getPos(int position) {
        SinglyNode<T> current = this.head;
        for (int i = 0; i < position; i++) current = current.next;
        return current.data;
    }

    public T getPos(long position) {
        SinglyNode<T> current = this.head;
        for (int i = 0; i < position; i++) current = current.next;
        return current.data;
    }

    public void setFirstNode(SinglyNode<T> head) {
        this.head = head;
    }

    public int size() {
        return size;
    }

}
