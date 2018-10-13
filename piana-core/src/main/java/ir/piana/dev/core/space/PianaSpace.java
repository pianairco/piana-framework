package ir.piana.dev.core.space;

import ir.piana.dev.core.annotation.PianaSpaceProperty;

import java.util.Properties;

public class PianaSpace {
    private static Properties properties = new Properties();

    public static void setProperty(
            PianaSpaceProperty pianaSpaceProperty)
            throws Exception {
        if(properties.contains(pianaSpaceProperty.name()))
            throw new Exception("key " +
                    pianaSpaceProperty.name() +
                    " duplicated.");
        properties.put(pianaSpaceProperty.name(),
                pianaSpaceProperty.value());
    }

    public static String getProperty(
            String name)
            throws Exception {
        return properties.getProperty(name);
    }
}
