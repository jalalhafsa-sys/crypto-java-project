package com.crypto.app;

import java.security.SecureRandom;
import java.util.Scanner;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Main {

    private static final String AES_ALGORITHM = "AES";
    private static final int AES_KEY_SIZE = 128;
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            displayMenu();
            choice = readIntegerChoice();

            switch (choice) {
                case 1:
                    encryptNewText();
                    break;
                case 2:
                    decryptStoredText();
                    break;
                case 3:
                    generateTextHash();
                    break;
                case 4:
                    compareTwoHashes();
                    break;
                case 5:
                    displayStoredData();
                    break;
                case 6:
                    clearStoredData();
                    break;
                case 0:
                    System.out.println("Fin du programme.");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez sélectionner une option correcte.");
            }

        } while (choice != 0);

        SCANNER.close();
    }

    private static void displayMenu() {
        System.out.println();
        System.out.println("=================================================");
        System.out.println("        APPLICATION DE CRYPTOGRAPHIE JAVA        ");
        System.out.println("=================================================");
        System.out.println("1. Saisir un texte puis le chiffrer");
        System.out.println("2. Déchiffrer le dernier texte chiffré");
        System.out.println("3. Calculer le SHA-256 d'un texte");
        System.out.println("4. Comparer deux hashes");
        System.out.println("5. Afficher les données mémorisées");
        System.out.println("6. Effacer les données mémorisées");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
    }

    private static int readIntegerChoice() {
        try {
            return Integer.parseInt(SCANNER.nextLine());
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    private static void encryptNewText() {
        try {
            System.out.print("Entrez le texte à chiffrer : ");
            final String plainText = SCANNER.nextLine();

            final SecretKey secretKey = generateSecretKey();
            final String encryptedText = EncryptionTools.encryptText(plainText, secretKey);

            CryptoStorage.saveOperation(plainText, encryptedText, secretKey);

            System.out.println("-------------------------------------------------");
            System.out.println("Texte original  : " + plainText);
            System.out.println("Texte chiffré   : " + encryptedText);
            System.out.println("Résultat : le texte et la clé ont été mémorisés.");
            System.out.println("-------------------------------------------------");

        } catch (Exception exception) {
            System.out.println("Erreur lors du chiffrement : " + exception.getMessage());
        }
    }

    private static void decryptStoredText() {
        try {
            if (!CryptoStorage.hasStoredEncryptedText()) {
                System.out.println("Aucun texte chiffré mémorisé. Veuillez d'abord utiliser l'option 1.");
                return;
            }

            final String encryptedText = CryptoStorage.getLastEncryptedText();
            final SecretKey secretKey = CryptoStorage.getLastSecretKey();

            final String decryptedText = DecryptionTools.decryptText(encryptedText, secretKey);

            System.out.println("-------------------------------------------------");
            System.out.println("Texte chiffré   : " + encryptedText);
            System.out.println("Texte déchiffré : " + decryptedText);
            System.out.println("-------------------------------------------------");

        } catch (Exception exception) {
            System.out.println("Erreur lors du déchiffrement : " + exception.getMessage());
        }
    }

    private static void generateTextHash() {
        try {
            System.out.print("Entrez le texte à hasher : ");
            final String text = SCANNER.nextLine();

            final String hash = HashTools.generateSHA256(text);

            System.out.println("-------------------------------------------------");
            System.out.println("Texte   : " + text);
            System.out.println("SHA-256 : " + hash);
            System.out.println("-------------------------------------------------");

        } catch (Exception exception) {
            System.out.println("Erreur lors du calcul du hash : " + exception.getMessage());
        }
    }

    private static void compareTwoHashes() {
        try {
            System.out.print("Entrez le premier hash : ");
            final String firstHash = SCANNER.nextLine();

            System.out.print("Entrez le deuxième hash : ");
            final String secondHash = SCANNER.nextLine();

            final boolean areIdentical = HashTools.compareHashes(firstHash, secondHash);

            System.out.println("-------------------------------------------------");
            if (areIdentical) {
                System.out.println("Résultat : les deux hashes sont identiques.");
            } else {
                System.out.println("Résultat : les deux hashes sont différents.");
                System.out.println("Conclusion : une modification est détectée.");
            }
            System.out.println("-------------------------------------------------");

        } catch (Exception exception) {
            System.out.println("Erreur lors de la comparaison : " + exception.getMessage());
        }
    }

    private static void displayStoredData() {
        System.out.println("-------------------------------------------------");
        if (!CryptoStorage.hasStoredData()) {
            System.out.println("Aucune donnée mémorisée.");
        } else {
            System.out.println("Dernier texte original mémorisé : " + CryptoStorage.getLastPlainText());
            System.out.println("Dernier texte chiffré mémorisé  : " + CryptoStorage.getLastEncryptedText());
            System.out.println("Clé mémorisée disponible        : " + (CryptoStorage.getLastSecretKey() != null ? "Oui" : "Non"));
        }
        System.out.println("-------------------------------------------------");
    }

    private static void clearStoredData() {
        CryptoStorage.clearStorage();
        System.out.println("Les données mémorisées ont été effacées.");
    }

    private static SecretKey generateSecretKey() throws Exception {
        final KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        final SecureRandom secureRandom = new SecureRandom();

        keyGenerator.init(AES_KEY_SIZE, secureRandom);

        return keyGenerator.generateKey();
    }
}