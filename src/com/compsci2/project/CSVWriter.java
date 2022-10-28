package com.compsci2.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class is responsible for updating CSV files when changes have been made to the ArrayLists that store data
 *CSV Files should follow a standard format for when the program is reopened
 * First line should include info about the data the column contains
 * no empty lines should follow at the end of the file
 * commas after all data including the last field: data, data, data,
 */

public class CSVWriter {

    /**
     * Stores the data from ArrayList<Stock> inventory in a CSV file: "inventory.txt"
     */
    public void updateInventory() {
        try (FileWriter inventoryWriter = new FileWriter("inventory.txt")) {
            inventoryWriter.write("ItemId,ItemName,Quantity,CostExpenditure,SalePrice,");
            for (Stock item : Data.inventory) {
                inventoryWriter.write("\n" + item.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stores data of salesFile to String fileContents
     * Concatenates data from the ArrayList<Sale> sales to fileContents
     * Stores fileContents in a CSV file: "sales.txt"
     */
    public void updateSales() {
        String fileContents = "";
        File salesFile = new File("sales.txt");
        try (Scanner in = new Scanner(salesFile)) {
            //don't want the first line if it exists
            if (in.hasNextLine()) in.nextLine();
            while (in.hasNextLine()) {
                fileContents += ("\n"+in.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (FileWriter salesWriter = new FileWriter("sales.txt")) {
            salesWriter.write("receiptId, customerId, subtotal, salesTax, totalSale, costExpenditure, profit, sellMade,");
            salesWriter.write(fileContents);
            for (Sale sale : Data.sales) {
                salesWriter.write("\n" + sale.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stores the data from ArrayList<User> users in a CSV file: "users.txt"
     */
    private void updateUsers() {
        try (FileWriter inventoryWriter = new FileWriter("users.txt")) {
            inventoryWriter.write("username,password");
            for (User user : Data.users) {
                inventoryWriter.write("\n" + user.getUsername() + "," + user.getPassword() + ",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stores the data from ArrayList<Supplier> suppliers in a CSV file: "suppliers.txt"
     */
    private void updateSuppliers() {
        try (FileWriter suppliersWriter = new FileWriter("suppliers.txt")) {
            suppliersWriter.write("ID, BusinessName, Email, Phone Number, Address");
            for (Supplier supplier : Data.suppliers) {
                suppliersWriter.write("\n" + supplier.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stores the data from ArrayList<Customer> customers in a CSV file: "customers.txt"
     */
    private void updateCustomers() {
        try (FileWriter customersWriter = new FileWriter("customers.txt")) {
            customersWriter.write("ID, CustomerName, Email, Phone Number, address");
            for (Customer customer : Data.customers) {
                customersWriter.write("\n" + customer.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
