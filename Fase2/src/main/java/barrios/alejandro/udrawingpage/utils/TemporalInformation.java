package barrios.alejandro.udrawingpage.utils;

import barrios.alejandro.udrawingpage.structures.BTree;

public class TemporalInformation {

    private static TemporalInformation temporalInformation = null;
    private int registeredUsers;
    private BTree usersTree;

    public static TemporalInformation getInstance() {
        if (temporalInformation == null)
            temporalInformation = new TemporalInformation();
        return temporalInformation;
    }

    public void startRegisteredUsers() {
        registeredUsers = 0;
    }

    public void setRegisteredUsers() {
        registeredUsers++;
    }

    public int getRegisteredUsers() {
        return registeredUsers;
    }

    public BTree getUsersTree() {
        return usersTree;
    }

    public void setUsersTree(BTree usersTree) {
        this.usersTree = usersTree;
    }
}
