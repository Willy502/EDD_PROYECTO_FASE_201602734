package barrios.alejandro.udrawingpage.utils;

import barrios.alejandro.udrawingpage.structures.controller.BTree;
import barrios.alejandro.udrawingpage.users.model.User;

public class TemporalInformation {

    private static TemporalInformation temporalInformation = null;
    private BTree usersTree;
    private User loguedUser;

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

    public User getLoguedUser() {
        return loguedUser;
    }

    public void setLoguedUser(User loguedUser) {
        this.loguedUser = loguedUser;
    }
}
