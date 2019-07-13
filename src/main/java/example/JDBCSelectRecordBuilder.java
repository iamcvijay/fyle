package example;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.sql.*;
import java.util.List;

public class JDBCSelectRecordBuilder {

    private static final Logger LOGGER = LogManager.getLogger(JDBCSelectRecordBuilder.class.getName());
    private static Connection c;
    private  static Statement statement ;
    static StringBuilder pstmt = new StringBuilder();
    private List<String> fields;
    private static final String driverClass = "org.postgresql.Driver";

    static{
        loadProps();
    }

    /*public JDBCSelectRecordBuilder selectFields(List<String> fields){
        String fieldStr = "*,";
        if(fields != null){
            fieldStr ="";
            for(String field:fields){
                fieldStr += field+",";
            }
        }
        fieldStr = fieldStr.substring(0,fieldStr.length()-1);
        pstmt.append(" Select "+fieldStr);
        return this;
    }*/

    /**
     * loads connection and creates statement
     */
    private static void loadProps() {
        try {
            Class.forName(driverClass);
            c = DriverManager
                    .getConnection("jdbc:postgresql://"+Property.getDbEndpoint()+"/"+Property.getDbDatabase()+"?sslmode=require",
                            Property.getDbUser(), Property.getDbPassword());
            LOGGER.info("Accessed database successfully");
            statement = c.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * methods for making builder generic and simple to use.
     * @param query
     * @return
     */
    /*public JDBCSelectRecordBuilder where(String where){
        pstmt.append(" WHERE "+where);
        return this;
    }
    public JDBCSelectRecordBuilder limit(int limit){
        pstmt.append(" LIMIT "+limit);
        return this;
    }
    public JDBCSelectRecordBuilder offset(int offset){
        pstmt.append(" OFFSET "+offset);
        return this;
    }
    public JDBCSelectRecordBuilder table(String  table){
        pstmt.append(" SELECT * FROM public."+table);
        return this;
    }*/

    /**
     * this function can be used to execute custom string queries.
     * @param query postgres query
     * @return String JSON where each row is in column-name:value format
     */
    public String setCustomPstmt(String query) {
        loadProps();
        JSONObject object = new JSONObject();
        pstmt = new StringBuilder();
        pstmt.append(query);
        ResultSet resultSet = null;
        try {
            LOGGER.info(" log pstmt "+pstmt);
            resultSet = statement.executeQuery(pstmt.toString());
            ResultSetMetaData metadata = resultSet.getMetaData();
            int columnCount = metadata.getColumnCount();
            int j=0;
            while (resultSet.next()) {
                JSONObject bankObject = new JSONObject();
                for(int i=1; i<columnCount; i++){
                    bankObject.put(metadata.getColumnName(i),resultSet.getString(i));
                }
                object.put(j++,bankObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    /**
     * for generic execution
     */
        /*public List<Map<String,Object>> select(){
        return new ArrayList<Map<String, Object>>();
        }*/
}