// Date Last Edited: 6/19/2024

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        PasswordGenerator passwordGen = new PasswordGenerator();
        Scanner scnr = new Scanner(System.in);

        ArrayList<String> userPasswords = new ArrayList<>();
        userPasswords = passwordGen.passwordList();

        boolean continuePassGen = true;
        boolean continuePassOptions = true;
        boolean continueCharOptions = true;

        while (continuePassGen) {
            passwordGen.menu();
            try {
                switch (scnr.nextInt()) {
                    case 1:
                        userPasswords = passwordGen.passwordList();
                        break;
                    
                    case 2:
                        while (continuePassOptions) {
                            passwordGen.passwordGenOptions();
                            try {
                                switch (scnr.nextInt()) {
                                    case 1:
                                        while (true) {
                                            System.out.print("  > Generation Amount #: ");
                                            try {
                                                passwordGen.setPasswordGenNumber(scnr.nextInt());
                                                break;
                                            }
                                            catch (InputMismatchException _) {
                                                System.out.println("\n>> Error: Invalid Input | Enter a whole number.");
                                                scnr.next();
                                            }
                                        }
                                        System.out.println("Generation Amount Set!");
                                        break;
        
                                    case 2:
                                        while (true) {
                                            System.out.print("  > Password Length #: ");
                                            try {
                                                passwordGen.setPasswordLength(scnr.nextInt());
                                                break;
                                            }
                                            catch (InputMismatchException _) {
                                                System.out.println("\n>> Error: Invalid Input | Enter a whole number.");
                                                scnr.next();
                                            }
                                        }
                                        System.out.println("Password(s) Length Set!");
                                        break;
        
                                    case 3:
                                        while (continueCharOptions) {
                                            passwordGen.passwordCharOptions();
                                            boolean setOption;
                                            try {
                                                switch (scnr.nextInt()) {
                                                    case 1:
                                                        setOption = passwordGen.getUpper() ? false : true;
                                                        passwordGen.setUpper(setOption);
                                                        break;
            
                                                    case 2:
                                                        setOption = passwordGen.getLower() ? false : true;
                                                        passwordGen.setLower(setOption);
                                                        break;
            
                                                    case 3:
                                                        setOption = passwordGen.getDigits() ? false : true;
                                                        passwordGen.setDigits(setOption);
                                                        break;
            
                                                    case 4:
                                                        setOption = passwordGen.getSpecialChars() ? false : true;
                                                        passwordGen.setSpecialChars(setOption);
                                                        break;
            
                                                    case 5:
                                                        continueCharOptions = false;
                                                        break;
            
                                                    default:
                                                        System.out.println("\n>> Error: Invalid Input | Enter a number corresponding to one of the options provided below.");
                                                        break;
                                                }
                                            }
                                            catch (InputMismatchException _) {
                                                System.out.println("\n>> Error: Invalid Input | Enter a number corresponding to one of the options provided below.");
                                                scnr.next();
                                            }
                                            
                                        }
                                        continueCharOptions = true;
                                        break;
                                    
                                    case 4:
                                        continuePassOptions = false;
                                        break;
            
                                    default:
                                        System.out.println("\n>> Error: Invalid Input | Enter a number corresponding to one of the options provided below.");
                                        break;
                                }
                            }
                            catch (InputMismatchException _) {
                                System.out.println("\n>> Error: Invalid Input | Enter a number corresponding to one of the options provided below.");
                                scnr.next();
                            }
                        }
                        continuePassOptions = true;
                        break;
    
                    // case 3:
                    //     passwordGen.saveToFile(userPasswords);
                    //     break;
                    
                    case 3:
                        passwordGen.copyToClipboard(userPasswords);
                        break;
    
                    case 4:
                        continuePassGen = false;
                        break;
    
                    default:
                        System.out.println("\n>> Error: Invalid Input | Enter a number corresponding to one of the options provided below.");
                        break;
                }
            }
            catch (InputMismatchException _) {
                System.out.println("\n>> Error: Invalid Input | Enter a number corresponding to one of the options provided below.");
                scnr.next();
            }
        }
        scnr.close();
    }
}
