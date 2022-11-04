package com.compscit.project;

public class Customer extends ThirdParty {

    /**
     * Constructs a new customer object
     * @param id
     * @param name
     * @param email
     * @param phone
     * @param address
     */
    public Customer(int id, String name, String email, String phone, String address) {
        super(id, name, email, phone, address);
    }

    /**
     * Constructs a new customer object without an ID
     * @param name
     * @param email
     * @param phone
     * @param address
     */
    public Customer(String name, String email, String phone, String address) {
        super(name, email, phone, address);
    }

}

/*
Additional Ideas
Customer satisfaction
Blacklist
Rewards program
*/