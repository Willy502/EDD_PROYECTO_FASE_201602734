package barrios.alejandro.udrawingpage.structures.controller;

import barrios.alejandro.udrawingpage.graph.Graph;
import barrios.alejandro.udrawingpage.users.model.User;

public class BTreeV2 {

    int orden = 5;
    BBranch root;
    private String result;

    public BTreeV2() {
        root = null;
    }

    public void insert(User user) {
        Node node = new Node(user);
        if (root == null) {
            root = new BBranch();
            root.insert(node);
        } else {
            Node obj = insertInBranch(node, root);
            if (obj != null) {
                root = new BBranch();
                root.insert(obj);
                root.leaf = false;
            }
        }
    }

    private Node insertInBranch(Node node, BBranch branch) {
        if (branch.leaf) {
            branch.insert(node);
            if (branch.counter == orden) {
                return split(branch);
            } else {
                return null;
            }
        } else {
            Node temp = branch.first;
            do {
                if (node.user.getDpi() == temp.user.getDpi())
                    return null;
                if (node.user.getDpi() < temp.user.getDpi()) {
                    Node obj = insertInBranch(node, temp.left);
                    if (obj instanceof Node) {
                        branch.insert((Node) obj);
                        if (branch.counter == orden) {
                            return split(branch);
                        }
                    }
                    return null;
                } else if (temp.next == null) {
                    Node obj = insertInBranch(node, temp.right);
                    if (obj instanceof Node) {
                        branch.insert((Node) obj);
                        if (branch.counter == orden) {
                            return split(branch);
                        }
                    }
                    return null;
                }
                temp = (Node) temp.next;
            } while (temp != null);
        }
        return null;
    }

    public User loginByUsernameAndPassword(String username, String password) {
        Node find = searchInNodeByUsername(root.first, username);

        if (find != null) {
            if (find.user != null && find.user.getPassword().equals(password)) {
                return find.user;
            }
        }
        return null;
    }

    public User searchUserByDpi(long dpi) {
        Node find = searchInNode(root.first, dpi);

        if (find != null) {
            if (find.user != null) {
                return find.user;
            }
        }
        return null;
    }

    private Node searchInNode(Node current, long dpi) {

        if (current.user.getDpi() == dpi)
            return current;

        if (current.left != null)
            return searchInNode(current.left.first, dpi);

        if (current.right != null)
            return searchInNode(current.right.first, dpi);

        if (current.next != null)
            return searchInNode(current.next, dpi);

        return null;
    }

    private Node searchInNodeByUsername(Node current, String username) {

        if (current.user.getUsername() == username)
            return current;

        if (current.left != null)
            return searchInNodeByUsername(current.left.first, username);

        if (current.right != null)
            return searchInNodeByUsername(current.right.first, username);

        if (current.next != null)
            return searchInNodeByUsername(current.next, username);

        return null;
    }

    public SinglyLinkedList<User> searchByLevels() {
        SinglyLinkedList<User> users = new SinglyLinkedList<>();
        searchInNodeByLevels(root.first, users);
        return users;
    }

    private void searchInNodeByLevels(Node current, SinglyLinkedList<User> users) {

        if (current == null) return;
        boolean add = true;
        for (SinglyLinkedList.Node<User> user = users.getHead(); user != null; user = user.next) {
            if (user.data.getDpi() == current.user.getDpi()) add = false;
        }
        if (add) users.addToList(current.user);
        searchInNodeByLevels(current.next, users);

        if (current.left != null)
            searchInNodeByLevels(current.left.first, users);

        if (current.left != null)
            searchInNodeByLevels(current.right.first, users);

    }

    private void recursiveGraph(Node current, int group) {
            if (current == null) return;
            result += current.user.getDpi() + "[width=.5 height=.5, group="+group+"];\n";
            recursiveGraph(current.next, group);

            if (current.left != null)
                recursiveGraph(current.left.first, group+1);

            if (current.left != null)
                recursiveGraph(current.right.first, group+1);
    }
    private void recursiveAssociate(Node current) {
        if (current == null) return;

        if (current.next != null) {
            result += current.user.getDpi() + " -> " + current.next.user.getDpi() +";\n";
        }
        recursiveAssociate(current.next);

        if (current.left != null) {
            result += current.user.getDpi() + " -> " + current.left.first.user.getDpi() +";\n";
            recursiveAssociate(current.left.first);
        }

        if (current.right != null) {
            result += current.user.getDpi() + " -> " + current.right.first.user.getDpi() +";\n";
            recursiveAssociate(current.right.first);
        }

    }

    public void graphBTree() {

        result = "digraph G {\n";
        result += "node[shape=square];\n";

        recursiveGraph(root.first, 1);
        recursiveAssociate(root.first);

        result += "}\n";
        Graph.GenerarImagen("BTree", result);
    }

    private Node split(BBranch branch) {
        User user = new User();
        Node temp, newNode;
        Node aux = branch.first;
        BBranch bRight = new BBranch();
        BBranch bLeft = new BBranch();

        int count = 0;
        while (aux != null) {
            count++;
            if (count < 3) {
                temp = new Node(aux.user);
                temp.left = aux.left;
                if (count == 2) {
                    temp.right = aux.next.left;
                } else {
                    temp.right = aux.right;
                }
                if (temp.right != null && temp.left != null) {
                    bLeft.leaf = false;
                }

                bLeft.insert(temp);
            }else if (count == 3) {
                user = aux.user;
            } else {
                temp = new Node(aux.user);
                temp.left = aux.left;
                temp.right = aux.right;

                if (temp.right != null && temp.left != null) {
                    bRight.leaf = false;
                }
                bRight.insert(temp);
            }
            aux = aux.next;
        }
        newNode = new Node(user);
        newNode.right = bRight;
        newNode.left = bLeft;
        return newNode;
    }


    static class Node {
        User user;
        Node next;
        Node prev;
        BBranch left;
        BBranch right;

        public Node(User user) {
            this.user = user;
            next = null;
            prev = null;
            left = null;
            right = null;
        }
    }

    static class BBranch {

        boolean leaf;
        int counter;
        Node first;

        public BBranch() {
            first = null;
            leaf = true;
            counter = 0;
        }

        public void insert(Node node) {
            if (first == null) {
                first = node;
                counter++;
            } else {

                Node aux = first;
                while (aux != null) {
                    if (aux.user.getDpi() == node.user.getDpi())
                        break;

                    if (aux.user.getDpi() > node.user.getDpi()) {
                        if (aux == first) {
                            aux.prev = node;
                            node.next = aux;

                            aux.left = node.right;
                            node.right = null;

                            first = node;
                        } else {
                            node.next = aux;

                            aux.left = node.right;
                            node.right = null;

                            node.prev = aux.prev;
                            aux.prev.next = node;
                            aux.prev = node;
                        }
                        counter++;
                        break;
                    } else if (aux.next == null) {
                        aux.next = node;
                        node.prev = aux;
                        counter++;
                        break;
                    }

                    aux = aux.next;
                }

            }
        }

    }

}
