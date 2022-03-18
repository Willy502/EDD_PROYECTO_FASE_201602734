package barrios.alejandro.udrawingpage.utils;

import barrios.alejandro.udrawingpage.structures.BTree;

public class InitApp {

    TemporalInformation temporalInformation;

    public InitApp() {
        temporalInformation = TemporalInformation.getInstance();
        temporalInformation.setUsersTree(new BTree(4));
    }

}
