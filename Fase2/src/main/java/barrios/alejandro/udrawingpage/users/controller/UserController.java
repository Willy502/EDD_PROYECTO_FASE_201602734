package barrios.alejandro.udrawingpage.users.controller;

import barrios.alejandro.udrawingpage.users.model.Rol;
import barrios.alejandro.udrawingpage.users.model.User;
import barrios.alejandro.udrawingpage.utils.CustomAlert;
import barrios.alejandro.udrawingpage.utils.TemporalInformation;

public class UserController {

    TemporalInformation temporalInformation;

    public UserController() {
        temporalInformation = TemporalInformation.getInstance();
    }


    public void createClient(String name, long dpi, String password) {
        User user = new User(dpi, name, password, Rol.CLIENT);
        temporalInformation.getUsersTree().insert(user);

        new CustomAlert("Registro", "Usuario creado exitosamente");
    }

}
