import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Security {
    static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA3-256");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return new String(digest);
    }
    static UUID setUUID(String name){
        return UUID.nameUUIDFromBytes(name.getBytes());
    }
}
