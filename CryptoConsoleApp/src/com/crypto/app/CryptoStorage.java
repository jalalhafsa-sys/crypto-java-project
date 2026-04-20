package com.crypto.app;

import javax.crypto.SecretKey;

public final class CryptoStorage {

    private static String lastPlainText;
    private static String lastEncryptedText;
    private static SecretKey lastSecretKey;

    private CryptoStorage() {
        // Empêcher l'instanciation
    }

    public static void saveOperation(final String plainText, final String encryptedText, final SecretKey secretKey) {
        lastPlainText = plainText;
        lastEncryptedText = encryptedText;
        lastSecretKey = secretKey;
    }

    public static String getLastPlainText() {
        return lastPlainText;
    }

    public static String getLastEncryptedText() {
        return lastEncryptedText;
    }

    public static SecretKey getLastSecretKey() {
        return lastSecretKey;
    }

    public static boolean hasStoredEncryptedText() {
        return lastEncryptedText != null && lastSecretKey != null;
    }

    public static boolean hasStoredData() {
        return lastPlainText != null || lastEncryptedText != null || lastSecretKey != null;
    }

    public static void clearStorage() {
        lastPlainText = null;
        lastEncryptedText = null;
        lastSecretKey = null;
    }
}