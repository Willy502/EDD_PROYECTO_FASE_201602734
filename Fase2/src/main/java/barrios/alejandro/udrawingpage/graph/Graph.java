package barrios.alejandro.udrawingpage.graph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Graph {

    public static void GenerarImagen(String nombre, String txtDTO) {
        try {
            //-----------Generar el txt
            File file = new File("out/" + nombre + ".txt");

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(txtDTO);
            bw.close();

            //---------Compilar el dto
            String dotPath = "C:\\Program Files\\Graphviz\\bin\\dot.exe";
            String fileIn = file.getCanonicalPath();
            String fileOU = fileIn.replace(".txt", ".png");
            String tParam = "-Tpng";
            String tOparam = "-o";

            String[] cmd = new String[5];
            cmd[0] = dotPath;
            cmd[1] = tParam;
            cmd[2] = fileIn;
            cmd[3] = tOparam;
            cmd[4] = fileOU;

            Runtime rt = Runtime.getRuntime();
            rt.exec(cmd);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
