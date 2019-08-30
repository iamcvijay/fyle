package tables;

import exception.FieldMissingException;
import sql.Column;
import sql.Table;
import sql.Type;

import java.util.HashSet;
import java.util.Set;

public class BranchesTable extends Column {

    private static Set<Column> allColumns = new HashSet<>();

    public static Set<Column> getAllColumns() {
        return new HashSet<>(allColumns);
    }

    public static Table BRANCH_TABLE;

    static {
        try {
            BRANCH_TABLE = new Table("branches");
        } catch (FieldMissingException e) {
            e.printStackTrace();
        }
    }

    public static final BranchesTable IFSC = new BranchesTable("ifsc",Type.VARCHAR,15,false);
    public static final BranchesTable BANK_ID = new BranchesTable("bank_id",Type.BIGINT,30,false);
    public static final BranchesTable BRANCH = new BranchesTable("branch",Type.VARCHAR,50,false);
    public static final BranchesTable ADDRESS = new BranchesTable("address",Type.VARCHAR,200,false);
    public static final BranchesTable CITY = new BranchesTable("city",Type.VARCHAR,50,false);
    public static final BranchesTable DISTRICT = new BranchesTable("district",Type.VARCHAR,50,false);
    public static final BranchesTable STATE = new BranchesTable("state",Type.VARCHAR,50,false);

    protected BranchesTable(String columnName, Type type, long size, boolean isNullable) {
        super(columnName, type, size, isNullable);
        allColumns.add(this);
    }

    @Override
    public String getTableName() {
        return BRANCH_TABLE.getName();
    }
}
