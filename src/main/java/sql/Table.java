package sql;


import exception.FieldMissingException;

public class Table {

    private final String tableName;

    public Table(String tableName) throws FieldMissingException {
        if(checkForSemiColon(tableName)) {
            throw new FieldMissingException("Table name contains invalid character ';' " + tableName);
        }
        this.tableName = tableName;
    }

    public String getName() {
        return tableName;
    }

    private static boolean checkForSemiColon(String table) {
        return table.contains(";");
    }

    public String getCreateDefenition(){
        return "CREATE TABLE IF NOT EXISTS "+this.tableName;
    }
}
