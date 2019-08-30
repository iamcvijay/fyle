package utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Query;
import queryBuilders.GenericSelectRecordBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JDBI connection layer which uses {@link queryBuilders.QueryBuilder object for db related operations }
 */
public class JdbiConnector {

    private static final Logger LOGGER = LogManager.getLogger(JdbiConnector.class.getName());

    private static Handle handle = null;
    private static final String driverClass = "org.postgresql.Driver";
    private static Jdbi jdbi;

    static {
        LOGGER.info("Loading db props");
        loadProps();
    }


    /**
     * loads jdbi connection
     */
    private static void loadProps() {
        try {
            Class.forName(driverClass);
            jdbi = Jdbi.create("jdbc:postgresql://"+Property.getDbEndpoint()+"/"+Property.getDbDatabase()+"?sslmode=require", Property.getDbUser(), Property.getDbPassword());
        } catch (Exception e) {
            LOGGER.info("Exception occurred ", e);
        }
    }

    /**
     * This method selects rows from database.
     * @param builder {@link GenericSelectRecordBuilder} object with the query built into them.
     * @return List of rows, selected as map.
     */
    public static List<Map<String, Object>> executeSelect(GenericSelectRecordBuilder builder) {
        Query finalQuery = null;
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            handle = jdbi.open();
            String sql = builder.generateQuery();
            LOGGER.info("SELECT QUERY " + sql);
            finalQuery = handle.createQuery(sql);
            for (int i = 0; i < builder.getValues().size(); i++) {
                finalQuery.bind(i, builder.getValues().get(i));
            }
            result = finalQuery.mapToMap().list();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info(" Exception occurred ", e);
        } finally {
            if (finalQuery != null) {
                finalQuery.close();
            }
            if (handle != null) {
                handle.close();
            }
        }
        return result;
    }

    // will contain methods to execute insert,update,delete.
}