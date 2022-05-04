package barrios.alejandro.udrawingpage.utils;

import barrios.alejandro.udrawingpage.place.model.Town;
import barrios.alejandro.udrawingpage.structures.controller.BTreeV2;
import barrios.alejandro.udrawingpage.structures.hash.HashTable;
import barrios.alejandro.udrawingpage.users.model.Rol;
import barrios.alejandro.udrawingpage.users.model.User;

public class InitApp {

    TemporalInformation temporalInformation;

    public InitApp() {
        temporalInformation = TemporalInformation.getInstance();
        temporalInformation.setUsersTree(new BTreeV2());
        temporalInformation.getUsersTree().insert(
                new User(
                        3001161010101L,
                        "Alejandro",
                        "123",
                        Rol.ADMIN,
                        "w.alejandro.barrios@gmail.com",
                        "alejandro",
                        new Town(1, "Default", "Default", false),
                        "47114767",
                        "Ciudad"
                )
        );
        temporalInformation.setCourierHashTable(new HashTable<>(37));
    }

}
