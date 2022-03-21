package barrios.alejandro.udrawingpage.utils;

import barrios.alejandro.udrawingpage.structures.controller.BTree;
import barrios.alejandro.udrawingpage.users.model.Rol;
import barrios.alejandro.udrawingpage.users.model.User;

import java.math.BigInteger;

public class InitApp {

    TemporalInformation temporalInformation;

    public InitApp() {
        temporalInformation = TemporalInformation.getInstance();
        temporalInformation.setUsersTree(new BTree(4));
        temporalInformation.getUsersTree().insert(new User(3001161010101L, "Alejandro", "123", Rol.ADMIN));
    }

}
