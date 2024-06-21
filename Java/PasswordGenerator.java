// Date Last Edited: 6/21/2024

import java.io.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

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
                if (!includes[j]) {
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

    public void writeToFile(ArrayList<String> passwords, File file, boolean append) throws FileNotFoundException, IOException {
        PrintWriter pWrite = null;
        if (append) {
            pWrite = new PrintWriter(new FileWriter(file, true));
        }
        else {
            LocalDateTime today = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy | hh:mm a");
            String todayStr = today.format(formatter);
            pWrite = new PrintWriter(file);
            pWrite.println("Generated Passwords");
            pWrite.println("Date: " + todayStr);
            pWrite.println("Note: Handle this file with care. For your security, do not share these passwords with anyone.\n");
        }
        for (String password : passwords) {
            pWrite.println(password);
        }
        pWrite.close();

    }

    // Saving methods
    public void saveToFile(ArrayList<String> passwords) throws FileNotFoundException, IOException {
        File saveFile = new File("passwords.txt");
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);
        
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(saveFile);
        chooser.setFileFilter(new FileNameExtensionFilter("*txt", "txt"));
        
        int returnVal = chooser.showSaveDialog(frame);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            saveFile = chooser.getSelectedFile();
            if (!saveFile.createNewFile()) {
                int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to overwrite this file's contents? \n(Answering NO will append your new password(s) to this file)\n\n", "Overwrite File", JOptionPane.YES_NO_CANCEL_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    writeToFile(passwords, saveFile, false);
                }
                else if (choice == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(frame, "Appending to file...");
                    writeToFile(passwords, saveFile, true);
                    JOptionPane.showMessageDialog(frame, "File Saved!");
                }
                else {
                    System.out.println("\n>> File not saved. If this was done in error, try again.");
                }
            }
            else {
                writeToFile(passwords, saveFile, false);
                JOptionPane.showMessageDialog(frame, "File Saved!");
            }
        }
        else {
            System.out.println("\n>> File not saved. If this was done in error, try again.");
        }

        frame.dispose();
        

     }

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
        System.out.println(" 3. Save Password(s) to File");
        System.out.println(" 4. Copy Password(s) to Clipboard");
        System.out.println(" 5. Exit");
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

        if (!includeUpper && !includeLower && !includeDigits && !includeSpecialChars) {
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