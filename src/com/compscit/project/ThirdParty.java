package com.compscit.project;

public class ThirdParty implements Comparable<ThirdParty> {

    private static int count = 0;
    private int id;
    private String name;
    private String email;
    private String phoneNum;
    private String address;


    public ThirdParty(int id, String name, String email, String phoneNum, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
        this.address = address;
        count = id;
    }

    public ThirdParty(String name, String email, String phoneNum, String address) {
        this.id = generateID();
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
        this.address = address;
    }

    /**
     Setter for the 3rd party ID
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     Getter for the 3rd party ID
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     Setter for the 3rd party name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     Getter for the 3rd party name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     Setter for the 3rd party email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     Getter for the 3rd party email
     * @return eMail
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for the 3rd party phone number
     * @param phone
     */
    public void setPhoneNumber(String phone) {
        this.phoneNum = phoneNum;
    }

    /**
     Getter for the 3rd party phone number
     * @return phone
     */
    public String getPhoneNumber() {
        return phoneNum;
    }

    /**
     * Setter for the 3rd party address
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for the supplier address
     @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     Generates the id
     @return count
     */
    public int generateID() {
        count++;
        return count;
    }

    /**
     Overriding the toString method to display all the 3rd party information
     */
    @Override
    public String toString() {
        return getId() + ", "
                + getName() + ", "
                + getEmail() + ", "
                + getPhoneNumber() + ", "
                + getAddress() + ",";
    }

    /**
     Compares two third parties by comparing their ID numbers.
     */
    @Override
    public int compareTo(ThirdParty o) {
        if (this.id < o.id) return -1;
        else if (this.id > o.id) return 1;
        else return 0;
    }

    /**
     *Overriding the equals method
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ThirdParty) return (this.id == ((ThirdParty) obj).id);
        else return false;
    }

}

/*
Required Additions
Allow the user to input the supplier name and generate the ID
*/
    
/*
Potential Additions
Allow the user to input and save notes about a particular supplier
*/


