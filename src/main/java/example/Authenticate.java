package example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Authenticate
{
    private static final Logger LOGGER = LogManager.getLogger(JDBCSelectRecordBuilder.class.getName());

    public static boolean authenticateJWT(String JWT){
        Base64.Decoder decoder = Base64.getDecoder();
        LOGGER.info(" auth string "+JWT);
        String jwt = JWT;
        Jws<Claims> claims = null ;
        try {
            claims = Jwts.parser()
            .setSigningKey(new String(decoder.decode(Property.getJwtEncoded())).getBytes("UTF-8"))
            .parseClaimsJws(jwt);
            //LOGGER.info(" scope -"+claims.getSignature());
            Long exipiration = Long.parseLong(claims.getBody().get("exp").toString());
            LOGGER.info(" expiration "+exipiration);
            if(exipiration > System.currentTimeMillis() || ! (exipiration == null) ){
                return true;
            }
            //assertEquals(scope, "self groups/admins");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        LOGGER.info(" authentication failed for "+JWT);
        return false;
    }

}
