package barrios.alejandro.udrawingpage.utils;

import barrios.alejandro.udrawingpage.structures.BTree;

public class TemporalInformation {

    private static TemporalInformation temporalInformation = null;
    private BTree usersTree;

    public static TemporalInformation getInstance() {
        if (temporalInformation == null)
            temporalInformation = new TemporalInformation();
        return temporalInformation;
    }

    public BTree getUsersTree() {
        return usersTree;
    }

    public void setUsersTree(BTree usersTree) {
        this.usersTree = usersTree;
    }
}
