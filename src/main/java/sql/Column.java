package sql;

public abstract class Column
{
    private String columnName;
    private Type type;
    private boolean isNullable;
    private long size;


    public long getSize() { return size; }

    private String tableColumnName;
    public String getTableColumnName() { return tableColumnName; }

    protected Column(String columnName, Type type, long size, boolean isNullable) {
        if(getTableName() != null && ! getTableName().isEmpty() ) {
            this.tableColumnName = getTableName() + "." + columnName;
        } else {
            this.tableColumnName = columnName;
        }
        this.columnName = columnName;
        this.type = type;
        this.size = size;
        this.isNullable = isNullable;
    }

    public abstract String getTableName();

    public String getColumn() { return columnName; }

    public Type getType() { return type; }

    public boolean isNullable() { return isNullable; }

    public String toString() {
        return getColumn();
    }

    public String getCreateDefinition(){
        StringBuilder builder = new StringBuilder(this.getColumn());
        //ID.getColumn() + " INTEGER PRIMARY KEY AUTOINCREMENT " + COMMA +
        builder.append(" ").append(this.getType()).append("(").append(this.getSize()).append(")");
        if( ! this.isNullable()){
            builder.append(" NOT NULL ");
        }
        return builder.toString();
    }
    public  String getPrimaryKeyCreateDefinition(){
        StringBuilder builder = new StringBuilder(this.getColumn());
        builder.append(" ").append(Type.INTEGER).append(" PRIMARY KEY AUTOINCREMENT ");
        return builder.toString();
    }
}
