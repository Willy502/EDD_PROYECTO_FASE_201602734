package barrios.alejandro.udrawingpage.utils;

import barrios.alejandro.udrawingpage.structures.controller.BTree;
import barrios.alejandro.udrawingpage.structures.controller.BTreeV2;
import barrios.alejandro.udrawingpage.users.model.User;

public class TemporalInformation {

    private static TemporalInformation temporalInformation = null;
    private BTreeV2 usersTree;
    private User loguedUser;

    public static TemporalInformation getInstance() {
        if (temporalInformation == null)
            temporalInformation = new TemporalInformation();
        return temporalInformation;
    }

    public BTreeV2 getUsersTree() {
        return usersTree;
    }

    public void setUsersTree(BTreeV2 usersTree) {
        this.usersTree = usersTree;
    }

    public User getLoguedUser() {
        return loguedUser;
    }

    public void setLoguedUser(User loguedUser) {
        this.loguedUser = loguedUser;
    }
}
