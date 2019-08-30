package queryBuilders;

import exception.FieldMissingException;

/**
 * This class provides basic methods for Querybuilders which can be used for db operations
 */
public abstract class QueryBuilder {

    boolean checkUsage(String option) {
        return (option != null) && (!option.isEmpty());
    }

    public abstract String generateQuery() throws FieldMissingException;

    static final String SELECT = "SELECT ";
    static final String INSERT = "INSERT ";
    static final String UPDATE = "UPDATE ";
    static final String ORDER_BY = " ORDER BY ";
    static final String LIMIT = " LIMIT ";
    static final String GROUP_BY = " GROUP BY ";
    static final String WHERE = " WHERE ";
    static final String INNER_JOIN = " INNER JOIN ";
    static final String ON = " ON ";
    static final String FROM = " FROM ";
    static final String AND = " AND ";
    static final String ASCENDING = " ASC ";
    static final String QN = " ? ";
    static final String COMMA = ",";
    static final String DESCENDING = " DESC ";
    static final String OFFSET = " OFFSET ";
    static final String COUNT = " COUNT ";
    static final String AS = " as ";
    static final String INTO = " INTO ";
    static final String VALUES = " VALUES ";
    static final String IN = " IN ";
    static final String QN_PSTMT = "=?";
    static final String SET = " SET ";

}
