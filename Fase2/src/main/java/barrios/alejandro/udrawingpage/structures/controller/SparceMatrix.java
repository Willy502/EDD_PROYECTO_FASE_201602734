package barrios.alejandro.udrawingpage.structures.controller;

import barrios.alejandro.udrawingpage.graph.Graph;

public class SparceMatrix { // layer

    public int id;
    public int maxX;
    public int maxY;
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
        head = new Node(1, 1, "transparent");
        Node current = head;
        Node prevY = null;
        while (y <= maxY) {
            while (x < maxX) {
                x++;
                Node newNode = new Node(x, y, "transparent");
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
            Node newNode = new Node(x, y, "transparent");
            newNode.top = current;
            current.bottom = newNode;
            prevY = current;
            current = current.bottom;
        }
    }

    public void saveCell(int x, int y, String color) {
        Node current = searchCell(x, y);
        current.color = color;
        System.out.println("(" + current.x + ", " + current.y + ") -> " + current.color);
    }

    private Node searchCell(int x, int y) {
        Node current = head;
        for (int currentX = 1; currentX < maxX; currentX++) {
            if (current.x == x) break;
            current = current.right;
        }

        for (int currentY = 1; currentY < maxY; currentY++) {
            if (current.y == y) break;
            current = current.bottom;
        }
        return current;
    }

    public void graphMatrix(boolean showNodes) {

        int fila = 1;
        int columna = 1;

        String result = "digraph G {\n";
        if (!showNodes)
            result += "graph[nodesep=0, ranksep=\"0\", pad=\"0.5\"];\n";
        result += "node[shape=box];\n";
        if (!showNodes)
            result += "edge[style=invis];\n";
        result+= "bgcolor=\"transparent\";\n";

        // Columna
        for (int currentX = 1; currentX <= maxX; currentX++) {
            Node currentCell = searchCell(currentX, columna);
            result += "F" + currentX + "_C" + columna + "[label=\"\", width=.3 height=.3, group=" + fila + ", style=filled, color=\"" + currentCell.color + "\"];\n";
        }

        // Relaciones
        for (int currentX = 1; currentX <= maxX - 1; currentX++) {
            result += "F" + currentX + "_C" + columna + " -> F" + (currentX + 1) + "_C" + columna + ";\n";
            result += "F" + (currentX + 1) + "_C" + columna + " -> F" + currentX + "_C" + columna + ";\n";
        }

        while ( fila <= maxX) {
            // Fila
            for (int currentY = 2; currentY <= maxY; currentY++) {
                Node currentCell = searchCell(fila, currentY);
                result += "F" + fila + "_C" + currentY + "[label=\"\", width=.3 height=.3, group=" + currentY + ", style=filled, color=\"" + currentCell.color + "\"];\n";
            }

            // Relaciones
            for (int currentY = 1; currentY <= maxY - 1; currentY++) {
                result += "F" + fila + "_C" + currentY + " -> F" + fila + "_C" + (currentY + 1) + ";\n";
                result += "F" + fila + "_C" + (currentY + 1) + " -> F" + fila + "_C" + currentY + ";\n";
            }

            if (fila > 1) {
                for (int currentY = 1; currentY <= maxY - 1; currentY++) {
                    result += "F" + (fila - 1) + "_C" + (currentY + 1) + " -> F" + fila + "_C" + (currentY + 1) + ";\n";
                    result += "F" + fila + "_C" + (currentY + 1) + " -> F" + (fila - 1) + "_C" + (currentY + 1) + ";\n";
                }
            }

            result += "{\n";
            result += "rank=same;\n";
            for (int currentY = 1; currentY <= maxY; currentY++) {
                result += "F" + fila + "_C" + currentY + ";\n";
            }
            result += "}\n";

            fila++;
        }

        result += "}\n";

        Graph.GenerarImagen("capa_"+id, result);
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
