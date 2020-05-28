package tetris;

/*
Configuratie van de locatie van de sprites, zodat ie per persoon naar de juiste bestandslocatie wijst
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TetrisConfig {
    private Properties config = new Properties();
    public TetrisConfig() {
        String fileName = "ICTM2d1/src/app.config";
        InputStream is = null;
        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException ex) {
        }
        try {
            config.load(is);
        } catch (IOException ex) {
        }
    }

    public String getVariable(String var) {
        return config.getProperty(var);
    }
}
