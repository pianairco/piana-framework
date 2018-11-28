package ir.piana.dev.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Mohammad Rahmati, 11/28/2018
 */
public class PropertiesFileUtil {
    public static Properties loadPropertiesFile() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("piana-server.properties");
            // load a properties file
            prop.load(input);
            System.out.println("propertiesFile loaded successfully.");
        } catch (IOException ex) {
            System.out.println("propertiesFile not loaded successfully.");
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("input stream of properties file close exception.");
                }
            }
        }
        return prop;
    }
}
