package utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class Property {
    static {
        System.out.println("loading");
        loadProperties();
    }

    private static String DB_ENDPOINT;
    private static String DB_USER;
    private static String DB_PASSWORD;
    private static String DB_DATABASE;
    private static String JWT_ENCODED;


    static String getJwtEncoded() {
        return JWT_ENCODED;
    }

    static String getDbEndpoint() {
        return DB_ENDPOINT;
    }

    static String getDbUser() {
        return DB_USER;
    }

    static String getDbPassword() {
        return DB_PASSWORD;
    }

    static String getDbDatabase() {
        return DB_DATABASE;
    }

    private static final Logger LOGGER = LogManager.getLogger(Property.class.getName());

    private static final String PROPERTY_FILE = "conf/dbConfig.properties";

    private static void loadProperties() {
//        LOGGER.info(" loading properties ");
        Properties PROPERTIES = new Properties();
        URL resource = Property.class.getClassLoader().getResource(PROPERTY_FILE);
        if (resource != null) {
            try (InputStream stream = resource.openStream()) {
                PROPERTIES.load(stream);
                PROPERTIES.forEach((k, v) -> PROPERTIES.put(k.toString().trim(), v.toString().trim()));

                DB_ENDPOINT = PROPERTIES.getProperty("db.endpoint");
                DB_USER = PROPERTIES.getProperty("db.userName");
                DB_PASSWORD = PROPERTIES.getProperty("db.password");
                DB_DATABASE = PROPERTIES.getProperty("db.database");
                JWT_ENCODED = String.valueOf(PROPERTIES.get("jwt.encoded"));
            } catch (IOException e) {
                LOGGER.info("Exception occurred ",e);
            }
        }
    }

}
