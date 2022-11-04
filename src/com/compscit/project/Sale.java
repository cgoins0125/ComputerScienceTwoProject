package com.compscit.project;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This class is used for making sales.
 * When a sale is created, the stock should decrease if and only if there are enough resources to complete the sale
 * It also calculates subtotal, tax, and total on the sale and returns the String representation of the object
 * formatted for use in a CSV file.
 */

public class Sale {

    private static final double TAX_AMOUNT = 0.09;
    private static ArrayList<Stock> inventory = Data.inventory;
    private static int saleCount = 0; //we want this to update at the start of the program.
    private static DecimalFormat df = new DecimalFormat("#.##");
    private int[][] soldItemId_quantity;
    private int customerId;
    private String date;
    private int saleId;


/**
 * This constructor should be used to initialize the static variables of the sale class before any other sale is completed
 * @param saleCount the int value static this.saleCount should start at when the program starts
 *                  based on how many sales have been completed in previous life cycles of the program
 */
    public Sale (int saleCount) {
        //subtracting one because it gets added back before the next sale is completed
        this.saleCount = saleCount;
    }

    //Instead of printing "There are not enough resources to complete sale", we should consider throwing a custom exception
    //we can do this during integration of GUI to best fit the GUIs needs
    /**
     * constructs a Sale object
     * @param customerId the ID number of the customer sold to
     * @param soldItemId_quantity the first field of the 2d array should contain the item's id number
     *                            the second field of the 2d array should contain the quantity of the items sold
     *                            each row should represent a distinct item being sold
     */
    public Sale (int customerId, int[][] soldItemId_quantity) {
        this.soldItemId_quantity = soldItemId_quantity;
        if (saleable() == true) {
            setDate();
            updateStock();
            this.customerId = customerId;
            saleId = generateSaleId();
        }
        else System.out.println("There are not enough resources to complete sale");
    }

    /**
     * generates a saleId, and increments the static variable saleCount by one.
     * @return saleId, should be unique of other saleIds
     */
    private int generateSaleId() {
        saleId = saleCount;
        saleCount++;
        return saleId;
    }

    /**
     * @return int[][] of the sale's sold items' IDs and quantities
     */
    public int[][] getSoldItemId_quantity() {
        return soldItemId_quantity;
    }

    /**
     * checks that there are enough resources to initiate the sale
     * @return true if there are enough resources to complete sale
     */
    private boolean saleable() {
        int itemId = 0;
        int quantity = 1;
        for (int i = 0; i < soldItemId_quantity.length; i++) {
            itemId = soldItemId_quantity[i][0];
            quantity = soldItemId_quantity[i][1];
            for (Stock item : inventory) {
                if (itemId == item.getItemId()) {
                    //returns false if there is an item in the sale not in the inventory
                    if (item.getQuantityOnHand() < quantity) return false;
                }
            }
        }
        //returns true once all items have been checked
        return true;
    }

    /**
     * calculates how much money is invested in the sold items
     * @return the business's expenditure for all items being sold in the Sale
     */
    private double getCostExpenditure() {
        int quantity = 1;
        double total = 0;
        double itemPrice = 0;
        int itemId = 0;
        for (int i = 0; i < soldItemId_quantity.length; i++) {
            itemId = soldItemId_quantity[i][0];
            quantity = soldItemId_quantity[i][1];
            //compare itemId to inventory to get price. Multiply price by
            // quantity and sum to total.
            for (Stock item : inventory) {
                if (itemId == item.getItemId()) {
                    itemPrice = item.getCostExpenditure();
                    //breaks the for loop once item is found
                    break;
                }
            }
            total += (itemPrice*quantity);
        }
        double formattedTotal = Double.parseDouble(df.format(total));
        return formattedTotal;
    }

    /**
     * when a Sale is complete this modifies the total quantity on hand of the inventory array
     * for the items sold in this Sale
     * This method should only be called when saleable() == true
     */
    private void updateStock() {
        int saleQuantity = 0;
        int itemId = 0;
        int index = 0;
        for (int i = 0; i < soldItemId_quantity.length; i++) {
            itemId = soldItemId_quantity[i][0];
            saleQuantity = soldItemId_quantity[i][1];
            for (Stock item : inventory) {
                if (itemId == item.getItemId()) {
                    index = inventory.indexOf(item);
                    //Decreases stock by the amount of resources sold
                    inventory.get(index).useItems(saleQuantity);
                    //once item is found the rest of inventory need not be searched through
                    break;
                }
            }
        }
    }

    /**
     * calculates the profit made in this Sale
     * @return the profit made on this sale
     */
    public double getProfit() {
        double profit = (getSubtotal() - getCostExpenditure());
        return profit;
    }

    /**
     * calculates the subtotal of the items being sold by comparing the items from soldItemId_quantity[][]
     * to the inventory ArrayList, and multiplying each items' sale price by its quantity being sold
     * formats the subtotal to two decimal places as is standard for USD
     * @return the subtotal that is generated
     */
    public double getSubtotal() {
        int itemId = 0;
        int quantitySold = 0;
        double subtotal = 0;
        for (int i = 0; i < soldItemId_quantity.length; i++) {
            itemId = soldItemId_quantity[i][0];
            quantitySold = soldItemId_quantity[i][1];
            for (Stock item : inventory) {
                if (itemId == item.getItemId()) {
                    subtotal += (item.getSalePrice() * quantitySold);
                    break;
                }
            }
        }
        double formattedSubtotal = Double.parseDouble(df.format(subtotal));
        return formattedSubtotal;
    }

    /**
     * Calculates tax on the Sale using static constant TAX_AMOUNT
     * rounds the tax to two decimal places as is standard for USD
     * @return the tax on the sale
     */
    public double getTax() {
        double tax = getSubtotal() * TAX_AMOUNT;
        double formattedTax = Double.parseDouble(df.format(tax));
        return formattedTax;
    }

    /**
     * Calculates the Total of the sale by adding subtotal and tax
     * @return subtotal + tax
     */
    public double getTotal() {
        double total = (getSubtotal() + getTax());
        return total;
    }

    /**
     * Gets the stored date of when the Sale object was constructed
     * @return sale date
     */
    public String getDate() {
        return date;
    }

    /**
     * Generates the current date when the Sale object is constructed
     * this should be used during construction, but not when the
     * date is needed outside of construction
     * @return current date
     */
    private void setDate() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH)+1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        date = month + "/" + day + "/" + year;
    }

    /**
     * Not currently utilizing this method, may want to deprecate
     * @return ttemID, itemName, quantity, and SalePrice
     */
    public String getItemsSold() {
        String requiredItems = "Item ID | Item Name | Quantity Sold | Sale Price \n";
        int itemId = 0;
        int quantitySold = 0;
        for (int i = 0; i < soldItemId_quantity.length; i++) {
            itemId = soldItemId_quantity[i][0];
            quantitySold = soldItemId_quantity[i][1];
            for (Stock item : inventory) {
                if (itemId == item.getItemId()) {
                    requiredItems += itemId + " | " + item.getItemName().toUpperCase() + " | " + quantitySold + " | " + item.getSalePrice() + "\n";
                    //breaks out of for loop once item is found
                    break;
                }
            }
        }
        return requiredItems;
    }

    /**
     * @return the Sale object's id
     */
    public int getSaleId() {
        return saleId;
    }

    /**
     * @return the id of the customer associated with this Sale
     */
    private int getCustomerId() {
        return customerId;
    }

    /**
     * @return String CSV format of the Sale object
     */
    @Override
    public String toString() {
        return  getSaleId() + ","
                + getCustomerId() + ","
                + getSubtotal() + ","
                + getTax() + ","
                + getTotal() + ","
                + getCostExpenditure() + ","
                + getProfit() + ","
                + getDate() + ',';
    }

}
