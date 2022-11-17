package com.compscit.project;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The purpose of this class is to store data from CSV files in ArrayLists that can be modified during the life of the program
 * If one of the files is formatted incorrectly the program should stop because at the program's end the data is overwritten
 */

public class CSVReader {

    private String receiptsPath;
    private ArrayList<Stock> inventory;
    private ArrayList<SalesReport> sales;
    private ArrayList<User> users;
    private ArrayList<Supplier> suppliers;
    private ArrayList<Customer> customers;
    private File inventoryFile;
    private File salesFile;
    private File usersFile;
    private File suppliersFile;
    private File customersFile;

    public CSVReader() {
        this.receiptsPath = getReceiptPath();
        this.inventory = Data.inventory;
        this.sales = Data.sales;
        this.users = Data.users;
        this.suppliers = Data.suppliers;
        this.customers = Data.customers;
        this.inventoryFile = new File("inventory.txt");
        this.salesFile = new File ("sales.txt");
        this.usersFile = new File ("users.txt");
        this.suppliersFile = new File ("suppliers.txt");
        this.customersFile = new File ("customers.txt");
        initialize();
    }

    /**
     * This method calls all other initialization methods when a CSVReaderWriter is constructed
     */
    //Initializes the inventory management system
    private void initialize() {
        initializeReceipts();
        initializeInventory();
        initializeSales();
        initializeUsers();
        initializeSuppliers();
        initializeCustomers();
    }

    /**
     * asks user to select a new path if one has not already been chosen
     * if a path has been chosen, returns the stored path
     * @return path the path chosen by the user scans from the .txt file "receiptPath"
     */
    private String getReceiptPath() {
        String path = "";
        File receiptPath = new File("receiptPath.txt");
        try (Scanner in = new Scanner(receiptPath)) {
            if (in.hasNext()) path = in.nextLine();
            else {
                ReceiptPathSelector rps = new ReceiptPathSelector();
                path = rps.getReceiptPath();
                try (FileWriter fw = new FileWriter("receiptPath.txt")) {
                    fw.write(path);
                } catch (IOException | NullPointerException ex) {
                    getReceiptPath();
                }
            }
        } catch (FileNotFoundException e) {
            ReceiptPathSelector rps = new ReceiptPathSelector();
            path = rps.getReceiptPath();
            try (FileWriter fw = new FileWriter("receiptPath.txt")) {
                fw.write(path);
            } catch (IOException | NullPointerException ex) {
                getReceiptPath();
            }
        }
        return path;
    }

    /**
     * passes String receiptsPath to the Receipt Class
     */
    private void initializeReceipts() {
        Receipt initialize = new Receipt(receiptsPath);
    }

    /**
     * Scans the inventory CSV file and stores data in the ArrayList inventory
     */
    private void initializeInventory() {
        try (Scanner in = new Scanner(inventoryFile)) {
            in.useDelimiter(",");
            //Necessary to skip the first line of the CSV file which contains the header - checks if it is blank
            if (in.hasNextLine()) in.nextLine();
            while (in.hasNext()) {
                try {
                    int itemId = Integer.parseInt(in.next().trim());
                    String itemName = in.next().trim();
                    int quantityOnHand = Integer.parseInt(in.next().trim());
                    double costExpenditure = Double.parseDouble(in.next().trim());
                    double salePrice = Double.parseDouble(in.next().trim());
                    inventory.add(new Stock(itemId, itemName, quantityOnHand, costExpenditure, salePrice));
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Please check that inventory CSV file is formatted correctly");
                }
            }
        } catch (FileNotFoundException e) {
            try (FileWriter fw = new FileWriter("inventory.txt")) {
                fw.write("ItemId,ItemName,Quantity,CostExpenditure,SalePrice,");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            initializeInventory();
        }
    }

    /**
     * Calculates sale count from salesFile and passes it as an argument to Sales class
     */
    private void initializeSales() {
        try (Scanner in = new Scanner(salesFile)) {
            in.useDelimiter(",");
            //Necessary to skip the first line of the CSV file which contains the header - checks if it is blank
            if (in.hasNextLine()) in.nextLine();
            while (in.hasNext()) {
                try {
                    int receiptId = Integer.parseInt(in.next().trim());
                    int customerId = Integer.parseInt(in.next().trim());
                    double subtotal = Double.parseDouble(in.next().trim());
                    double salesTax = Double.parseDouble(in.next().trim());
                    double totalSale = Double.parseDouble(in.next().trim());
                    double costExpenditure = Double.parseDouble(in.next().trim());
                    double profit = Double.parseDouble(in.next().trim());
                    String sellDate = in.next().trim();
                    sales.add(new SalesReport(receiptId, customerId, subtotal, salesTax, totalSale, costExpenditure, profit, sellDate));
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Please check that sales CSV file is formatted correctly");
                }
            }
        } catch (FileNotFoundException e) {
            try (FileWriter fw = new FileWriter("sales.txt")) {
                fw.write("receiptId, customerId, subtotal, salesTax, totalSale, costExpenditure, profit, sellDate,");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            initializeSales();
        }
    }

    /**
     * stores CSV data in the ArrayList<User> users
     */
    private void initializeUsers() {
        try (Scanner in = new Scanner(usersFile)) {
            in.useDelimiter(",");
            //Necessary to skip the first line of the CSV file which contains the header - checks if it is blank
            if (in.hasNextLine()) in.nextLine();
            while (in.hasNext()) {
                try {
                   String username = in.next().toLowerCase().trim();
                    String password = in.next().trim();
                    String firstName = in.next().trim();
                    String lastName = in.next().trim();
                    users.add(new User(username,password,firstName,lastName));
                } catch (InputMismatchException e) {
                    throw new InputMismatchException("Please check that user CSV file is formatted correctly");
                }
            }
        } catch (FileNotFoundException e) {
            try (FileWriter fw = new FileWriter("users.txt")) {
                fw.write("username,password,first name, last name,");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            initializeUsers();
        }
    }

    /**
     * Stores data from the suppliersFile in the ArrayList<Supplier> suppliers
     * if a String representation of null is found, it is passed to the array as null
     */
    private void initializeSuppliers() {
        try (Scanner in = new Scanner(suppliersFile)) {
            in.useDelimiter(",");
            //Necessary to skip the first line of the CSV file which contains the header - checks if it is blank
            if (in.hasNextLine()) in.nextLine();
            while (in.hasNext()) {
                try {
                    int id = Integer.parseInt(in.next().trim());
                    String name = in.next().trim();
                    String email = in.next().trim();
                    String number = in.next().trim();
                    String address = in.next().trim();
                    if (address.equals("null")) address = null;
                    else address += ","+in.next().trim()+","+in.next().trim()+","+in.next().trim();
                    if (name.equals("null")) name = null;
                    if (email.equals("null")) email = null;
                    if (number.equals("null")) number = null;
                    suppliers.add(new Supplier(id,name,email,number,address));
                } catch (InputMismatchException e) {
                    throw new InputMismatchException("Please check that supplier CSV file is formatted correctly");
                }
            }
        } catch (FileNotFoundException e) {
            try (FileWriter fw = new FileWriter("suppliers.txt")) {
                fw.write("ID, BusinessName, Email, Phone Number, Address, City, State, Zip Code,");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            initializeSuppliers();
        }
    }

    /**
     * Stores data from the customersFile in the ArrayList<Customer> customers
     * if a String representation of null is found, it is passed to the array as null
     */
    private void initializeCustomers() {
        try (Scanner in = new Scanner(customersFile)) {
            in.useDelimiter(",");
            //Necessary to skip the first line of the CSV file which contains the header
            if (in.hasNextLine()) in.nextLine();
            while (in.hasNext()) {
                try {
                    int id = Integer.parseInt(in.next().trim());
                    String name = in.next().trim();
                    String email = in.next().trim();
                    String number = in.next().trim();
                    String address = in.next().trim();
                    if (address.equals("null")) address = null;
                    else address += ","+in.next().trim()+","+in.next().trim()+","+in.next().trim();
                    if (name.equals("null")) name = null;
                    if (email.equals("null")) email = null;
                    if (number.equals("null")) number = null;
                    customers.add(new Customer(id,name,email,number,address));
                } catch (InputMismatchException e) {
                    throw new InputMismatchException("Please check that customer CSV file is formatted correctly");
                }
            }
        } catch (FileNotFoundException e) {
            try (FileWriter fw = new FileWriter("customers.txt")) {
                fw.write("ID, Customer Name, Email, Phone Number, Address, City, State, Zip Code,");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            initializeCustomers();
        }
    }
}
