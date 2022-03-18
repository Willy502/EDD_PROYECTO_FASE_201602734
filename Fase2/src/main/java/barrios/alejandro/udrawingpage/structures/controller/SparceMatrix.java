package barrios.alejandro.udrawingpage.structures.controller;

public class SparceMatrix {

    public int id;
    private int maxX;
    private int maxY;
    private Node head;

    public SparceMatrix(int id, int maxX, int maxY) {
        this.id = id;
        this.maxX = maxX;
        this.maxY = maxY;
        buildMatrix();
    }

    private void buildMatrix() {
        int x = 1;
        int y = 1;
        head = new Node(1, 1, "#FFF");
        Node current = head;
        Node prevY = null;
        while (y <= maxY) {
            while (x < maxX) {
                x++;
                Node newNode = new Node(x, y, "#FFF");
                newNode.left = current;
                current.right = newNode;
                current = current.right;
                if (prevY != null) {
                    prevY = prevY.right;
                    current.top = prevY;
                    prevY.bottom = current;
                }
            }
            if (y == maxY) break;
            x = 1;
            y++;
            current = head;
            while (current.bottom != null) current = current.bottom;
            Node newNode = new Node(x, y, "#FFF");
            newNode.top = current;
            current.bottom = newNode;
            prevY = current;
            current = current.bottom;
        }
    }

    public void saveCell(int x, int y, String color) {

        Node current = head;
        for (int currentX = 1; currentX < maxX; currentX++) {
            if (current.x == x) break;
            current = current.right;
        }

        for (int currentY = 1; currentY < maxY; currentY++) {
            if (current.y == y) break;
            current = current.bottom;
        }
        current.color = color;
        System.out.println("(" + current.x + ", " + current.y + ") -> " + current.color);
    }

    static class Node {
        public int x, y;
        public String color;
        public Node top, bottom, right, left;

        public Node(int x, int y, String color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }

    }
}
