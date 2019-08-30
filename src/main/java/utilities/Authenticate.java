package utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Base64;

class Authenticate
{
    private static final Logger LOGGER = LogManager.getLogger(JdbiConnector.class.getName());

    static boolean authenticateJWT(String JWT){
        System.out.println(" jwt "+JWT);
        Base64.Decoder decoder = Base64.getDecoder();
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
        } catch (Exception e) {
            LOGGER.info("Exception while authentication ",e);
        }
        LOGGER.info(" authentication failed ");
        return false;
    }

}
