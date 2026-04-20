package com.crypto.app;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.web.bind.annotation.*;

@RestController
public class CryptoController {

    @PostMapping("/crypt")
    public Map<String, Object> crypt(@RequestBody Map<String, String> request) {

        Map<String, Object> response = new HashMap<>();

        try {
            String text = request.get("text");

            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128, new SecureRandom());
            SecretKey key = kg.generateKey();

            String encrypted = EncryptionTools.encryptText(text, key);

            CryptoStorage.save(encrypted, key);

            response.put("encrypted", encrypted);
            response.put("original", text);

        } catch (Exception e) {
            response.put("error", e.getMessage());
        }

        return response;
    }

    @GetMapping("/decrypt")
    public Map<String, Object> decrypt() {

        Map<String, Object> response = new HashMap<>();

        try {
            if (!CryptoStorage.hasData()) {
                response.put("error", "Aucun texte stocké");
                return response;
            }

            String encrypted = CryptoStorage.getEncryptedText();
            SecretKey key = CryptoStorage.getSecretKey();

            String decrypted = DecryptionTools.decryptText(encrypted, key);

            response.put("encrypted", encrypted);
            response.put("decrypted", decrypted);

        } catch (Exception e) {
            response.put("error", e.getMessage());
        }

        return response;
    }

    @PostMapping("/hash")
    public Map<String, Object> hash(@RequestBody Map<String, String> request) {

        Map<String, Object> response = new HashMap<>();

        try {
            String text = request.get("text");
            String hash = HashTools.generateSHA256(text);

            response.put("text", text);
            response.put("hash", hash);

        } catch (Exception e) {
            response.put("error", e.getMessage());
        }

        return response;
    }

    @PostMapping("/compare")
    public Map<String, Object> compare(@RequestBody Map<String, String> request) {

        Map<String, Object> response = new HashMap<>();

        String h1 = request.get("hash1");
        String h2 = request.get("hash2");

        boolean same = HashTools.compareHashes(h1, h2);

        response.put("same", same);

        return response;
    }
}