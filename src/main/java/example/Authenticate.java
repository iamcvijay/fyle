package example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;

public class Authenticate
{
    private static final Logger LOGGER = LogManager.getLogger(JDBCSelectRecordBuilder.class.getName());

    public static boolean authenticateJWT(String JWT){
        String jwt = JWT;
        Jws<Claims> claims = null ;
        try {
            claims = Jwts.parser()
            .setSigningKey("Fyle@HeLlo123@VijAy857ujviudml26784jvukd@74hfn93CFndfikq@9en".getBytes("UTF-8"))
            .parseClaimsJws(jwt);
            System.out.println(" scope -"+claims.getSignature());
            Long exipiration = (Long) claims.getBody().get("exp");
            System.out.println(" scope "+exipiration);
            if(exipiration > System.currentTimeMillis()){
                return true;
            }
            //assertEquals(scope, "self groups/admins");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        LOGGER.info(" authentication failed for "+JWT);
        return false;
    }

    public static void main(String[] args) {
        System.out.println(authenticateJWT("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJmeWxlVGVzdCIsIm5hbWUiOiJ2aWpheSIsImV4cCI6MTU2MzM4MDQ2NjAwMH0.aGUrDEOf2XpeauVu3MDZ5Y10y8YaFD8zNifwltlTBn0"));
    }
}
