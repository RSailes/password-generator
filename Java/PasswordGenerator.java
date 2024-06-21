// Date Last Edited: 6/19/2024

import java.io.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.awt.datatransfer.*;

public class PasswordGenerator {
    
    // Private fields
    private int passwordGenNumber = 5;
    private int passwordLength = 12;
    private boolean includeUpper = true;
    private boolean includeLower = true;
    private boolean includeDigits = true;
    private boolean includeSpecialChars = true;
    private SecureRandom secureRandom = new SecureRandom();

    // All character needed for the generated password
    private final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private final String DIGITS = "0123456789";
    private final String SPECIAL_CHARACTERS = "!@#$%^&*()-_+=<>?";

    // Setters
    public void setPasswordGenNumber(int passwordGenNumber) {
        this.passwordGenNumber = passwordGenNumber;
    }

    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
    }

    public void setUpper(boolean includeUpper) {
        this.includeUpper = includeUpper;
    }

    public void setLower(boolean includeLower) {
        this.includeLower = includeLower;
    }

    public void setDigits(boolean includeDigits) {
        this.includeDigits = includeDigits;
    }

    public void setSpecialChars(boolean includeSpecialChars) {
        this.includeSpecialChars = includeSpecialChars;
    }

    // Getters
    public boolean getUpper() {
        return includeUpper;
    }
    
    public boolean getLower() {
        return includeLower;
    }

    public boolean getDigits() {
        return includeDigits;
    }

    public boolean getSpecialChars() {
        return includeSpecialChars;
    }

    // Password generation methods
    public String generatePassword() {
        int[] randomNums = new int[passwordLength];
        boolean[] includes = {includeUpper, includeLower, includeDigits, includeSpecialChars};
        String[] allChars = {UPPERCASE, LOWERCASE, DIGITS, SPECIAL_CHARACTERS};
        
        String includedChars = "";

        for (int i = 0; i < passwordLength; i++) {
            int temp;
            for (int j = 0; j < 4; j++) {
                if (includes[j] == false) {
                    allChars[j] = "";
                }
            }
            for (String chars : allChars) {
                includedChars += chars;
            }
            temp = secureRandom.nextInt(includedChars.length());

            randomNums[i] = temp;
        }

        StringBuilder buildPassword = new StringBuilder();

        for (int i = 0; i < passwordLength; i++) {
            char tempChar;
            tempChar = includedChars.charAt(randomNums[i]);
            buildPassword.append(tempChar);
        }

        String newPassword = buildPassword.toString();

        return newPassword;
    }

    public ArrayList<String> passwordList() {
        ArrayList<String> passwords = new ArrayList<>();
        System.out.println("\n>>> Your Passwords <<<");
        for (int i = 1; i < passwordGenNumber + 1; i++) {
            String password = generatePassword();
            System.out.println(" " + i + ". " + password);
            passwords.add(password);
        }

        return passwords;
    }

    // Saving methods
    // public void saveToFile(ArrayList<String> passwords) throws FileNotFoundException, IOException {
    //     File file = new File(path);
    //     PrintWriter printWriter = null;
    //     if (file.createNewFile()) {
    //         System.out.println("New file created...");
    //         System.out.println("Path: " + path);
    //         printWriter = new PrintWriter(file);
    //         printWriter.println(">>> Your Passwords <<<");
    //     }
    //     else {
    //         System.out.println("File already exists, adding to file...");
    //         printWriter = new PrintWriter(new FileWriter(file, true));
    //     }

    //     for (String password : passwords) {
    //         printWriter.println(" " + password);
    //     }

    //     printWriter.close();

    //     System.out.println("Password(s) Saved!");
    // }

    public void copyToClipboard(ArrayList<String> passwords) {
        String allPasswords = "";
        
        for (String password : passwords) {
            allPasswords += password + "\n";
        }

        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection strSel = new StringSelection(allPasswords);
        clip.setContents(strSel, strSel);
        System.out.println("Password(s) copied to clipboard!");

    }

    // Option display methods
    public void menu() {
        System.out.println("\n>>> Options");
        System.out.println(" 1. Generate Passwords");
        System.out.println(" 2. Edit Generation Options");
        // System.out.println(" 3. Save Password(s) to File");
        System.out.println(" 3. Copy Password(s) to Clipboard");
        System.out.println(" 4. Exit");
        System.out.print("> Option #: ");
    }

    public void passwordGenOptions() {
        System.out.println("\n >> Password Generation Options");
        System.out.println("  1. Change Generation Amount");
        System.out.println("  2. Change Password(s) Length");
        System.out.println("  3. Change Characters Included");
        System.out.println("  4. Back");
        System.out.print(" > Option #: ");
    }

    public void passwordCharOptions() {
        System.out.println("\n  >> Password Character Inclusion/Exclusion");

        String upperMsg = "   1. " + setCharMsg(includeUpper) + " Uppercase Letters";
        System.out.println(upperMsg);

        String lowerMsg = "   2. " + setCharMsg(includeLower) + " Lowercase Letters";
        System.out.println(lowerMsg);

        String digitMsg = "   3. " + setCharMsg(includeDigits) + " Digits";
        System.out.println(digitMsg);

        String specialsMsg = "   4. " + setCharMsg(includeSpecialChars) + " Special Characters";
        System.out.println(specialsMsg);

        System.out.println("   5. Back");

        System.out.print("  > Option #: ");

        if (includeUpper == false && includeLower == false && includeDigits == false && includeSpecialChars == false) {
            System.out.println("\n   Error: Must include at least one type of character | Options Reset");
            this.setUpper(true);
            this.setLower(true);
            this.setDigits(true);
            this.setSpecialChars(true);
            passwordCharOptions();
        }
    }

    public String setCharMsg(boolean include) {
        String msgChange = include ? "Exclude" : "Include";
        return msgChange;
    }
}