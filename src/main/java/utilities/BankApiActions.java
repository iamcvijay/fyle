package utilities;

import exception.FieldMissingException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import queryBuilders.GenericSelectRecordBuilder;
import queryBuilders.Operator;
import sql.Column;
import tables.BankTable;
import tables.BranchesTable;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Path("/vijay")
public class BankApiActions {

    private static final Logger LOGGER = LogManager.getLogger(BankApiActions.class.getName());

    @QueryParam("offset")
    private int offset;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @QueryParam("limit")
    private int limit;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @HeaderParam("authorization")
    private String authstr;

    public String getAuthstr() {
        return authstr;
    }

    public void setAuthstr(String authstr) {
        this.authstr = authstr;
    }


    private String ifsc;

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    @GET
    @Path("/BankDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBankDetails(@QueryParam("ifsc") String ifsc) {
        if (!Authenticate.authenticateJWT(getAuthstr())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        Set<Column> field = BranchesTable.getAllColumns();
        field.addAll(BankTable.getAllColumns());
        String responseBody = "";
        try {
            GenericSelectRecordBuilder builder1 = new GenericSelectRecordBuilder().table(BranchesTable.BRANCH_TABLE).fields(field)
                    .innerJoin(BankTable.BANK_TABLE, BranchesTable.BANK_ID, BankTable.ID)
                    .andWhere(BranchesTable.IFSC, Operator.EQUALS, ifsc);
            if(getOffset() != 0){
                builder1.offset(getOffset());
            }
            if (getOffset() != 0) {
                builder1.limit(getLimit());
            }
            responseBody = getAsJSONResult(builder1.select()).toString();
        } catch (FieldMissingException e) {
            LOGGER.info("Exception occurred ",e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(responseBody).build();
    }

    @GET
    @Path("/branchDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBranches(@QueryParam("city") String city, @QueryParam("bankName") String bankName) {
        Set<Column> fields = BranchesTable.getAllColumns();
        fields.addAll(BankTable.getAllColumns());
        if (!Authenticate.authenticateJWT(getAuthstr())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String responseBody;
        GenericSelectRecordBuilder builder;
        try {
            builder = new GenericSelectRecordBuilder().table(BranchesTable.BRANCH_TABLE).fields(fields)
                    .innerJoin(BankTable.BANK_TABLE, BranchesTable.BANK_ID, BankTable.ID)
                    .andWhere(BranchesTable.CITY, Operator.EQUALS, city)
                    .andWhere(BankTable.NAME, Operator.EQUALS, bankName);

        if (getLimit() != 0) {
            builder.offset(getOffset())
                    .limit(getLimit());
        }
        responseBody = getAsJSONResult(builder.select()).toString();
        } catch (Exception e) {
            LOGGER.info("Exception occurred ",e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        System.out.println(responseBody);
        return Response.ok(responseBody).build();
    }

    /**
     * This method can be used to make results from rows selected usin {@link GenericSelectRecordBuilder} or any List-of-Map.
     * This method builds a {@link JSONObject} with the list with the key "results"
     * @param rows List<Map<String, Object>> rows, usually selected rows
     * @return a JSONObject with key "results"
     */
    private static JSONObject getAsJSONResult(List<Map<String, Object>> rows){
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        if (rows != null) {
            array.addAll(rows);
            result.put("results",array);
        }
        return result;
    }
}





//SELECT *, banks.name as bankname from branches INNER JOIN banks ON branches.bank_id = banks.id where branches.city='MUMBAI' and banks.name='STATE BANK OF INDIA';

// SELECT *, banks.name from branches where INNER JOIN banks ON branches.bank_is = banks.id where branches.city=''