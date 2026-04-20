package com.crypto.app;

import javax.crypto.SecretKey;

public final class CryptoStorage {

    private static String encryptedText;
    private static SecretKey secretKey;

    private CryptoStorage() {}

    public static void save(String text, SecretKey key) {
        encryptedText = text;
        secretKey = key;
    }

    public static String getEncryptedText() {
        return encryptedText;
    }

    public static SecretKey getSecretKey() {
        return secretKey;
    }

    public static boolean hasData() {
        return encryptedText != null && secretKey != null;
    }
}