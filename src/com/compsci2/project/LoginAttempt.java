package com.compsci2.project;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author cecil
 */
public class LoginAttempt {

    private static ArrayList<User> users = Data.users;

    public LoginAttempt(String username, String password) {
        isLoggedIn(username, password);
    }

    public LoginAttempt() {
        tryLogin();
    }

    //This is for testing should be replaced with GUI
    private void tryLogin () {
        Scanner in = new Scanner(System.in);
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("Enter your username: ");
            String username = in.next();
            System.out.println("Enter your password: ");
            String password = in.next();
            loggedIn = isLoggedIn(username, password);
        }
    }
    
    public boolean isLoggedIn(String username, String password) {
        for (User user : users) {
            if (user.usernameExists(username))
            {
                if (user.correctPassword(username, password)) {
                    System.out.print("Login successful");
                    return true;
                }
                else {
                    System.out.println("Incorrect Password");
                    break;
                }
            } 
            else {
                System.out.println("Incorrect username");
                break;
            }
                
            
        }
     return false;   
    }
}
