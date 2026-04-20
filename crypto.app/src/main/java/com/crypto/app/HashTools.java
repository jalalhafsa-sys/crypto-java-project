package com.crypto.app;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public final class HashTools {

    private HashTools() {}

    public static String generateSHA256(String text) throws Exception {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(text.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();

        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) sb.append('0');
            sb.append(hex);
        }

        return sb.toString();
    }

    public static boolean compareHashes(String h1, String h2) {
        return h1 != null && h1.equalsIgnoreCase(h2);
    }
}