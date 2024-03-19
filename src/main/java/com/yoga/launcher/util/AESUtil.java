package com.yoga.launcher.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@UtilityClass
@Slf4j
public class AESUtil {

    private static final String SECRET_KEY = "232DSA_DFA_DSF";
    private static final String SALT = "FOODZIEE_SALT";

    public static String encrypt(String input) {
        try {
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey temp = keyFactory.generateSecret(spec);
            SecretKeySpec secretKeySpec = new SecretKeySpec(temp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(input.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException |
                 InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            log.error("Exception occurred while encryption : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String input) {
        try {
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey temp = keyFactory.generateSecret(spec);
            SecretKeySpec secretKeySpec = new SecretKeySpec(temp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(input)));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException |
                 InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            log.error("Exception occurred while encryption : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
