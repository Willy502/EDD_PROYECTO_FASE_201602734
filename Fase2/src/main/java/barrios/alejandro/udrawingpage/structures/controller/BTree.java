package barrios.alejandro.udrawingpage.structures.controller;

import barrios.alejandro.udrawingpage.users.model.User;

public class BTree {
    // Delete missing

    int t;
    Node root;

    public BTree(int t) {
        this.t = t;
        root = new Node(t);
    }

    public void insert(User key) {
        Node r = root;

        if (r.noKeys == ((2 * t) - 1)) { // Si el nodo ya esta lleno
            Node s = new Node(t);
            root = s;
            s.leaf = false;
            s.noKeys = 0;
            s.child[0] = r;
            split(s, 0, r);
            insertInNonFullNode(s, key);
        } else { // Si el nodo no esta lleno
            insertInNonFullNode(r, key);
        }
    }

    private void insertInNonFullNode(Node node, User key) {

        if (node.leaf) { // Si es una hoja
            int i = node.noKeys; // Cuantos valores tiene el nodo
            while(i >= 1 && key.dpi < node.key[i - 1].dpi) {
                node.key[i] = node.key[i - 1]; // Desplazamos los valores mayores al que va a ingresar
                i--;
            }

            node.key[i] = key; // Insertamos
            node.noKeys++; // Aumenta la cantidad de elementos en el nodo
            System.out.println("Insercion realizada correctamente en el arbol B");

        } else {
            int j = 0;

            while (j < node.noKeys && key.dpi > node.key[j].dpi) j++; // Buscamos la posicion del nodo hijo

            if (node.child[j].noKeys == ((2 * t) - 1)) { // Si el nodo hijo esta lleno lo separamos
                split(node, j, node.child[j]);

                if (key.dpi > node.key[j].dpi) j++;
            }

            insertInNonFullNode(node.child[j], key);
        }
    }

    private void split(Node node1, int i, Node node2) {
        Node temp = new Node(t);
        temp.leaf = node2.leaf;
        temp.noKeys = t - 1;

        for (int j = 0; j < (t - 1); j++) temp.key[j] = node2.key[j + t]; // Copia las ultimas t-1 claves de node2 al inicio de temp

        // Reasignar nodos hijos si no es hoja
        if (!node2.leaf) {
            for (int k = 0; k < t; k++) {
                temp.child[k] = node2.child[k + t];
            }
        }

        node2.noKeys = t - 1; // Nuevo tamaño del node2

        // Se mueven los hijos de node1 para darle espacio a temp
        for (int j = node1.noKeys; j > 1; j--) {
            node1.child[j + 1] = node1.child[j];
        }

        // Reasigna el hijo i + 1 de X
        node1.child[i + 1] = temp;

        // Se mueven las claves de X
        for (int j = node1.noKeys; j > i; j--) {
            node1.key[j + 1] = node1.key[j];
        }

        // Arreglar la clave situada al medio
        node1.key[i] = node2.key[t - 1];
        node1.noKeys++;
    }

    public User searchUserByDpiAndPassword(long dpi, String password) {
        Node temp = search(root, dpi, password);

        if (temp != null) {
            User user = searchUserInNode(temp, dpi);
           if (user != null && user.getPassword().equals(password)) {
               return user;
           }
        }
        return null;
    }

    private User searchUserInNode(Node n, long dpi) {
        User user = n.searchUser(dpi);
        if (user != null) return user;

        //Si no es hoja
        if (!n.leaf) {
            //recorre los nodos para saber si tiene hijos
            for (int j = 0; j <= n.noKeys; j++) {
                if (n.child[j] != null) {
                    searchUserInNode(n.child[j], dpi);
                }
            }
        }
        return null;
    }

    private Node search(Node current, long dpi, String password) {
        int i = 0;

        while (i < current.noKeys && dpi > current.key[i].dpi) i++;

        if (i < current.noKeys && dpi == current.key[i].dpi) return current;

        if (current.leaf) {
            return null;
        } else {
            return search(current.child[i], dpi, password);
        }
    }

    static class Node {
        int noKeys; // Claves almacenadas en el nodo
        boolean leaf; // El nodo es hoja?
        User[] key; // Almacena las claves en el nodo
        Node[] child; // Arreglo con referencia a los hijos

        public Node(int t) {
            noKeys = 0;
            leaf = true;
            key = new User[(2 * t) - 1];
            child = new Node[2 * t];
        }

        public User searchUser(long dpi) {
            for (int i = 0; i < noKeys; i++) {
                if (key[i].dpi == dpi) return key[i];
            }
            return null;
        }

    }

}
