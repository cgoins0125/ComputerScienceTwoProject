
package com.compscit.project;

import java.util.ArrayList;


/**
 * @author cecily holland
 * user class to store data sets username, password, and checks to make sure that the username is available
 * and the password is correct
 * @username
 * @password
 */
public class User {

    private static ArrayList<User> users = Data.users;
    private String username;
    private String password;

    private String firstName;

    private String lastName;

    /**
     * constructs a User object
     * @param username
     * @param password
     */
    public User(String username, String password, String firstName, String lastName) {
        if (!usernameExists(username)) {
            this.username = username;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * sets username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * gets username
     * @return username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * set user password 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;

    }

    /**
     * gets password
     * @return password
     */
    public String getPassword() {
        return this.password;

    }

    /**
     *
     * @param username
     * @return
     */
    public static boolean usernameExists(String username) {
        for (User user : users) {
            if (username.equalsIgnoreCase(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public static boolean correctPassword(String username, String password) {
        for (User user : users) {
            if (username.equalsIgnoreCase(user.getUsername()) && password.equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }


//    /**
//     * Static method to set adminKey
//     */
//    public static void setAdminKey(){
//        adminKey = "1234567";
//    }   
////    /**
////     * Determines if the entered adminKey is correct .
////     * @param adminKey
////     * @return userType
////     */
//    public boolean isAdmin(String adminKey){
//        
//        if(adminKey.equals("1234567")){
//            isAdmin = true;
//    }
//                 
//        return this.isAdmin;
//    }


    
        
    
}
