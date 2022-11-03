package com.compsci2.project;

import java.util.ArrayList;

/**
 *A utility class that's purpose is to make the ArrayLists
 *accessible to all classes that might need to use/modify them
 * @inventory stores Stock data
 * @sales stores Sale data
 * @Users stores User data
 * @Suppliers stores Supplier data
 * @Customers stores customer data
 */

public class Data {

    public static ArrayList<Stock> inventory = new ArrayList<>();
    public static ArrayList<SalesReport> sales = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Supplier> suppliers = new ArrayList<>();
    public static ArrayList<Customer> customers = new ArrayList<>();


}
