/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp1;

/**
 *
 * @author ogund
 */
public class Logingithub {
   private String username;
    private String userPassword;
    private String cellNumber;

    // Username validation
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Password validation
    public boolean checkPasswordComplexity(String password) {
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        
        for (int i = 0; i < password.length(); i++){
            char c = password.charAt(i);
            
            if (Character.isUpperCase(c)){
                hasCapital = true;
            }else if (Character.isDigit(c)){
                hasNumber = true;
            }else if (!Character.isLetterOrDigit(c)){
                hasSpecial = true;
            }
        }
        return password.length() >= 8 && hasCapital && hasNumber && hasSpecial;
    }

    // Cell phone validation 
    public boolean checkCellPhoneNumber(String phoneNumber) {
        return phoneNumber.startsWith("+27")&& phoneNumber.length() <=13;
    }

    // Register user 
    public String registerUser(String username, String password, String phoneNumber) {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(phoneNumber)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        // Store values if valid
        this.username = username;
        this.userPassword = password;
        this.cellNumber = phoneNumber;

        return "User registered successfully.";
    }

    // Login check
    public boolean loginUser(String username, String password) {
        return username.equals(this.username) && password.equals(this.userPassword);
    }

    // Login status message
    public String returnLoginStatus(boolean loginStatus) {
        if (loginStatus) {
            return "Welcome "+username+" , it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }  
}
