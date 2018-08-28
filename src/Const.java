import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class Const {

    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    //random.nextBytes(salt);
    SecretKey key = new SecretKeySpec(salt, "AES");
}
