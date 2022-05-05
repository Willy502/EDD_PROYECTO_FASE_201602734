package barrios.alejandro.udrawingpage.utils;

import barrios.alejandro.udrawingpage.place.model.Town;
import barrios.alejandro.udrawingpage.structures.Adjacency.AdjacencyList;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyLinkedList;
import barrios.alejandro.udrawingpage.structures.controller.BTreeV2;
import barrios.alejandro.udrawingpage.structures.hash.HashTable;
import barrios.alejandro.udrawingpage.users.model.Rol;
import barrios.alejandro.udrawingpage.users.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class InitApp {

    TemporalInformation temporalInformation;

    public InitApp() {
        temporalInformation = TemporalInformation.getInstance();
        temporalInformation.setUsersTree(new BTreeV2());
        String hashed = BCrypt.hashpw("123", BCrypt.gensalt(12));
        temporalInformation.setTownSinglyLinkedList(new SinglyLinkedList<>());
        temporalInformation.getUsersTree().insert(
                new User(
                        3001161010101L,
                        "Alejandro",
                        hashed,
                        Rol.ADMIN,
                        "w.alejandro.barrios@gmail.com",
                        "alejandro",
                        new Town(0, "Default", "Default", false),
                        "47114767",
                        "Ciudad"
                )
        );
        temporalInformation.setCourierHashTable(new HashTable<>(37));
        temporalInformation.setRoutes(new AdjacencyList());
    }

}
