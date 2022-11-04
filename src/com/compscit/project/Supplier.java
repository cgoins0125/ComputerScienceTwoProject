package com.compscit.project;

public class Supplier extends ThirdParty {

    /**
     * Constructs a new supplier object
     * @param id
     * @param name
     * @param email
     * @param phone
     * @param address
     */
    public Supplier(int id, String name, String email, String phone, String address) {
        super(id, name, email, phone, address);
    }

    /**
     * Constructs a new customer object without an ID
     * @param name
     * @param email
     * @param phone
     * @param address
     */

    public Supplier(String name, String email, String phone, String address) {
        super(name, email, phone, address);
    }

}



/*
Additional ideas:
Bills from supplier 
Payment due to supplier 
Payment supplier received.
*/