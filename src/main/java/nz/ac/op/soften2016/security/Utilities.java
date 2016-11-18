package nz.ac.op.soften2016.security;

import org.apache.commons.codec.binary.Base64;


import org.apache.commons.codec.binary.Base64;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

/**
 * Created by thoml on 26/08/2016.
 */
public class Utilities {
    public static String CreateSalt() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[32];
            sr.nextBytes(salt);
            return Base64.encodeBase64String(salt);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String EncryptedPassword(String password, String salt) {
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512"); // Requires JDK 8 and above
            PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes("utf-8"), 1000, 256);
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
            byte[] encryptedPassword = secretKey.getEncoded();
            return Base64.encodeBase64String(encryptedPassword);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //To be removed when decided on encryption algorithm as it depends on working the JDK environment
    public static void CheckProviders() {
        for (Provider provider : Security.getProviders()) {
            System.out.println(provider);
            for (Provider.Service service : provider.getServices()) {
                if ("SecretKeyFactory".equals(service.getType())) {
                    System.out.println(service);
                }
            }
        }
    }

    public static boolean PasswordCheck(String hashedPassword, String salt, String plainTextPassword) {
        String encryptedPassword = EncryptedPassword(plainTextPassword, salt);
        if (hashedPassword.equals(encryptedPassword)) {
            return true;
        }
        return false;
    }
    
    public static Map SetUp(Map inputModel){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = (auth.isAuthenticated() && auth.getPrincipal().equals("root"));
        inputModel.put("isLoggedIn", isLoggedIn);
        return inputModel;
    }


}
