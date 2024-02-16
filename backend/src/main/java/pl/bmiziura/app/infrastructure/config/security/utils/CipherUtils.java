package pl.bmiziura.app.infrastructure.config.security.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.charset.Charset;
import java.security.Key;
import java.util.Base64;

@Component
@RequiredArgsConstructor
@Slf4j
public class CipherUtils {
    private final SecretKey cipherKey;

    public String encrypt(String data) {
        try {
            Cipher c = Cipher.getInstance(cipherKey.getAlgorithm());
            c.init(Cipher.ENCRYPT_MODE, cipherKey);
            byte[] encVal = c.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encVal);
        } catch (Exception ex) {
            log.error("CipherException", ex);
            throw new RuntimeException("Problem with Cipher Encryption");
        }
    }

    public String decrypt(String encryptedData) {
        if(encryptedData == null) return null;

        try {
            Cipher c = Cipher.getInstance(cipherKey.getAlgorithm());
            c.init(Cipher.DECRYPT_MODE, cipherKey);
            byte[] decodedValue = Base64.getDecoder().decode(encryptedData);
            byte[] decVal = c.doFinal(decodedValue);
            return new String(decVal);
        } catch (Exception ex) {
            log.error("CipherException", ex);
            throw new RuntimeException("Problem with Cipher Decryption");
        }
    }
}
