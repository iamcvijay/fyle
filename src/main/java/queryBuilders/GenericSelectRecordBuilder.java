package queryBuilders;

import exception.FieldMissingException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sql.Column;
import sql.Table;
import utilities.JdbiConnector;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GenericSelectRecordBuilder extends  QueryBuilder{

    private static final Logger LOGGER = LogManager.getLogger(GenericSelectRecordBuilder.class.getName());

    private String tableName;
    private String groupBy;
    private String orderByColumn = null;
    private int limit = -1;
    private int offset = -1;
    private StringBuilder whereCondition = new StringBuilder();
    private String fields;
    private boolean isQueryGenerated = false;

    private StringBuilder query = new StringBuilder();

    public List<Object> getValues() {
        return values;
    }

    private List<Object> values = new LinkedList<>();
    private String countFields;
    private String orderByOrder = ASCENDING;
    private String joinTableName;
    private String on1, on2;
    private String inBetweenColumn;
    private Long inBetween1,inBetween2;

    public GenericSelectRecordBuilder() {
        query.append(SELECT);
    }

    public String getQuery() {
        return query.toString();
    }

    public GenericSelectRecordBuilder table(Table table)  {
            this.tableName = table.getName();
            return this;
    }



    /**
     *
     * @param whereCondition string condition without the word "WHERE"
     *                       example -
     *                       query (whereCondition) - "Select * FROM X WHERE ID = 1" ;
     *                       input parameter - ID = 1
     * @return SelectQueryBuilder
     */
    @Deprecated // injection problems - will be removed in future
    public GenericSelectRecordBuilder whereCondition(String whereCondition) {
        this.whereCondition.append(whereCondition);
        return this;
    }

    public GenericSelectRecordBuilder andWhere(Column column, Operator sign, Object value) {
        if (whereCondition != null && checkUsage(whereCondition.toString())) {
            whereCondition.append(AND);
        }
        if (whereCondition != null) {
            whereCondition.append(" ").append(column.getTableColumnName()).append(" ").append(sign.getOperator()).append(" ? ");
        }
        values.add(value);

        return this;
    }

    /**
     * To limit the size of selected rows.
     *
     * @param limit number of rows to be selected, as integer.
     * @return SelectQueryBuilder
     */
    public GenericSelectRecordBuilder limit(int limit) {
        this.limit = limit;
        return this;
    }

    /**
     * returns the selected rows in specified order
     * <p>
     * if Descending option is not used , Ascending will be default.
     * @param orderByColumn column name of the column with which selected rows should be ordered, as String.
     *
     * @param orderByColumn column name of the column with which selected rows should be ordered, as String.
     *                     <p>
     *                     example :
     *                     query - SELECT * FROM X ORDER BY ID DESC;
     *                     parameter (orderByField) - ID
     *                     NOTE -  .Descending() must be used  (Default ASC)
     * @return SelectQueryBuilder
     */
    public GenericSelectRecordBuilder orderBy(Column orderByColumn) {
        this.orderByColumn = orderByColumn.getTableColumnName();
        return this;
    }

    public GenericSelectRecordBuilder fields(Set<Column> columns) {
        StringBuilder columnBuilder = new StringBuilder();
        for (Column column : columns) {
            columnBuilder.append(column.getTableColumnName()).append(COMMA);
        }
        columnBuilder.deleteCharAt(columnBuilder.lastIndexOf(COMMA));
        this.fields = columnBuilder.toString();
        return this;
    }

    public GenericSelectRecordBuilder countOf(Set<Column> columns){
        StringBuilder columnBuilder = new StringBuilder();
        for (Column column : columns) {
            columnBuilder.append(COUNT).append("(").append(column.getTableColumnName()).append(")").append(AS).append(column.getColumn()).append(COMMA);
        }
        columnBuilder.deleteCharAt(columnBuilder.lastIndexOf(COMMA));
        this.countFields = columnBuilder.toString();
        return this;
    }


    /**
     * This method must be used for inner join selection
     *
     * @param joinTable other table that must be joined
     * @param on1           column1
     * @param on2           column2
     *                      Example -
     *                      query - SELECT * FROM X INNNER JOIN Y ON X.ID=Y.ID;
     *                      joinTableName - "X"
     *                      on1 - "X.ID"
     *                      on2 - "Y.ID"
     * @return
     */
    public GenericSelectRecordBuilder innerJoin(Table joinTable, Column on1, Column on2) throws FieldMissingException {
            this.joinTableName = joinTable.getName();
        if (joinTableName.equalsIgnoreCase(tableName)) {
            throw new FieldMissingException("Inner join table name ", " cant be same as table name");
        }
            this.on1 = on1.getTableColumnName();
            this.on2 = on2.getTableColumnName();
            return this;
    }

    public GenericSelectRecordBuilder Descending() {
        orderByOrder = DESCENDING;
        return this;
    }

    public GenericSelectRecordBuilder groupBy(Column groupBy) {
        this.groupBy = groupBy.getTableColumnName();
        return this;
    }

    public GenericSelectRecordBuilder offset(int offset) {
        this.offset = offset;
        return this;
    }

    public GenericSelectRecordBuilder inBetweenOf(Column column, Long from, Long to) throws FieldMissingException {
        if( from < to ) {
            inBetweenColumn = column.getTableColumnName();
            inBetween1 = from;
            inBetween2 = to;
            return  this;
        }
        throw new FieldMissingException(" Inbetween condition value "," value1 must be smaller than value2 ");
    }

    public String generateQuery() throws FieldMissingException {
        if (isQueryGenerated) {
            return query.toString();
        }
        if ( (! checkUsage(fields)) && (! checkUsage(countFields)) ) {
            throw new FieldMissingException("Columns to select ", " add columns using fields() or count() ");
        }
        if(checkUsage(countFields)){
            query.append(countFields);
        }
        else if ( checkUsage(fields) ) {
            query.append(fields);
        }
        if (checkUsage(tableName)) {
            query.append(FROM).append(tableName);
        } else {
            throw new FieldMissingException("TableName ", " add table name using .table()");
        }
        if (checkUsage(joinTableName) && checkUsage(on1) && checkUsage(on2)) {
            query.append(INNER_JOIN).append(joinTableName).append(ON).append(on1).append(Operator.EQUALS.getOperator()).append(on2);
        }
        if ((whereCondition != null) && checkUsage(whereCondition.toString()) && (!whereCondition.toString().contains(WHERE)) && (!whereCondition.toString().contains(WHERE))) {
            query.append(WHERE).append(whereCondition);
        } else if (checkUsage(inBetweenColumn)) {
            query.append(WHERE).append(inBetweenColumn).append(Operator.GREATER_THAN.getOperator()).append(inBetween1).append(AND).append(inBetweenColumn).append(Operator.LESSER_THAN.getOperator()).append(inBetween2);
        } else {
            throw new FieldMissingException("Where condition ", " add where condition just eliminating the word 'WHERE' to .whereCondition() ");
        }
        if (checkUsage(groupBy)) {
            query.append(GROUP_BY).append(groupBy);
        }
        if (checkUsage(orderByOrder)) {
            if (checkUsage(orderByColumn)) {
                query.append(ORDER_BY).append(orderByColumn).append(orderByOrder);
            }
            if (limit > 0) {
                query.append(LIMIT).append(limit);
            }
            if (offset > 0) {
                query.append(OFFSET).append(offset);
            }
            isQueryGenerated = true;
            LOGGER.info("Select-------- "+query.toString());
            return query.toString();
        }else {
            throw new FieldMissingException("Order in orderBy ");
        }
    }
    public GenericSelectRecordBuilder countRows(){
        return this;
    }

    public List<Map<String,Object>> select() throws FieldMissingException {
        return JdbiConnector.executeSelect(this);
    }

}
