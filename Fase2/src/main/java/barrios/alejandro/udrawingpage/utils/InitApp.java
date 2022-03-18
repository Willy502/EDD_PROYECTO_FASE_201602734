package barrios.alejandro.udrawingpage.utils;

import barrios.alejandro.udrawingpage.structures.controller.BTree;

public class InitApp {

    TemporalInformation temporalInformation;

    public InitApp() {
        temporalInformation = TemporalInformation.getInstance();
        temporalInformation.setUsersTree(new BTree(4));
    }

}
