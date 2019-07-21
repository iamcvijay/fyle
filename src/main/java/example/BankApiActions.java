package example;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/vijay")
public class BankApiActions {
    @QueryParam("offset")
    private int offset;
    public int getOffset() { return offset; }
    public void setOffset(int offset) { this.offset = offset; }

    @QueryParam("limit")
    private int limit ;
    public int getLimit() { return limit; }
    public void setLimit(int limit) { this.limit = limit; }

    @HeaderParam("authorization")
    private String authstr;
    public String getAuthstr() { return authstr;
    }
    public void setAuthstr(String authstr) { this.authstr = authstr; }


    private String ifsc;
    public String getIfsc() { return ifsc; }
    public void setIfsc(String ifsc) { this.ifsc = ifsc; }

    @GET
    @Path("/BankDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBankDetails(@QueryParam("ifsc") String ifsc) {
        if (!Authenticate.authenticateJWT(getAuthstr())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String responseBody = new String();
        JDBCSelectRecordBuilder builder = new JDBCSelectRecordBuilder();
        if(getLimit() != 0){
             responseBody = builder.setCustomPstmt("SELECT *, banks.name from branches INNER JOIN banks on branches.bank_id = banks.id where branches.ifsc='" + ifsc + "' OFFSET " + getOffset() + " LIMIT " + getLimit());
        }else{
             responseBody = builder.setCustomPstmt("SELECT *, banks.name from branches INNER JOIN banks on branches.bank_id = banks.id where branches.ifsc='" + ifsc+"'");
        }
        return Response.ok(responseBody).build();
    }

    @GET
    @Path("/branchDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBranches(@QueryParam("city") String city, @QueryParam("bankName") String bankName) {
        {
            if (!Authenticate.authenticateJWT(getAuthstr())) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            String responseBody = new String();
            JDBCSelectRecordBuilder builder = new JDBCSelectRecordBuilder();
            if(getLimit() != 0){
                responseBody = builder.setCustomPstmt("SELECT *, banks.name as bankname from branches INNER JOIN banks ON branches.bank_id = banks.id where branches.city='" + city + "' and banks.name='" + bankName + "' OFFSET " + getOffset() + " LIMIT " + getLimit());
            }else{
                responseBody = builder.setCustomPstmt("SELECT *, banks.name as bankname from branches INNER JOIN banks ON branches.bank_id = banks.id where branches.city='" + city + "' and banks.name='" + bankName + "'");
            }
            System.out.println(responseBody);
            return Response.ok(responseBody).build();
        }
    }
}


//SELECT *, banks.name as bankname from branches INNER JOIN banks ON branches.bank_id = banks.id where branches.city='MUMBAI' and banks.name='STATE BANK OF INDIA';

// SELECT *, banks.name from branches where INNER JOIN banks ON branches.bank_is = banks.id where branches.city=''