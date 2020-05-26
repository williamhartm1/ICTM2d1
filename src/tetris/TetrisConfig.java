package tetris;

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
