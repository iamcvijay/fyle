package tables;

import exception.FieldMissingException;
import sql.Column;
import sql.Table;
import sql.Type;

import java.util.HashSet;
import java.util.Set;

public class BankTable extends Column {

    public static Table BANK_TABLE;

    private static Set<Column> allColumns = new HashSet<>();

    public static Set<Column> getAllColumns() {
        return new HashSet<>(allColumns);
    }

    static {
        try {
            BANK_TABLE = new Table("banks");
        } catch (FieldMissingException e) {
            e.printStackTrace();
        }
    }

    public static final BankTable NAME = new BankTable("name",Type.VARCHAR,100,false);
    public static final BankTable ID = new BankTable("id",Type.BIGINT,30,false);


    protected BankTable(String columnName, Type type, long size, boolean isNullable) {
        super(columnName, type, size, isNullable);
        allColumns.add(this);
    }

    @Override
    public String getTableName() {
        return BANK_TABLE.getName();
    }
}
