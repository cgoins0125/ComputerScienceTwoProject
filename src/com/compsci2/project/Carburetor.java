package com.compsci2.project;

import java.util.ArrayList;
import java.util.Arrays;

public class Carburetor extends Stock{

    private final int[][] requiredStock;
    private ArrayList<Stock> inventory;

    //for Carburetors that already have an itemID
    public Carburetor(String carbName, int quantityOnHand, int[][] itemId_quantity, ArrayList<Stock> inventory, double salePrice) {
        super(carbName,quantityOnHand,0,0);
        requiredStock = itemId_quantity;
        //should store the arrayList as a reference location so that any updates made afterwards can also be found in the array
        this.inventory = inventory;
        if (stockIsCarb()) throw new IllegalArgumentException("Carburetor cannot be used to build a carburetor.");
        setCostExpenditure(getCarbCostExpenditure());
    }

    //Array should store items from stock that are necessary to build carburetor
    //first column should contain itemId and second column should contain quantity needed
    public Carburetor(int itemId, String carbName, int quantityOnHand, int[][] itemId_quantity, ArrayList<Stock> inventory, double salePrice) {
        super(itemId,carbName,quantityOnHand,0,0);
        requiredStock = itemId_quantity;
        //should store the arrayList as a reference location so that any updates made afterwards can also be found in the array
        this.inventory = inventory;
        if (stockIsCarb()) throw new IllegalArgumentException("Carburetor cannot be used to build a carburetor.");
        setCostExpenditure(getCarbCostExpenditure());
    }

    private boolean stockIsCarb() {
        int itemId = 0;
        for (int i = 0; i < requiredStock.length; i++) {
            itemId = requiredStock[i][0];
            for (Stock item : inventory) {
                if (itemId == item.getItemId()) {
                    if (item instanceof Carburetor) return true;
                break;
                }
            }
        }
        return false;
    }

    //calculates how much it costs to build a carburetor - if prices update this should too
    private double getCarbCostExpenditure() {
        int quantity = 1;
        double total = 0;
        double itemPrice = 0;
        int itemId = 0;
        for (int i = 0; i < requiredStock.length; i++) {
            itemId = requiredStock[i][0];
            quantity = requiredStock[i][1];
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
        return total;
    }

    //returns how many carburetors of this type can be made with given inventory
    public int ableToMake() {
        //necessary variables for calculations
        int neededQuantity = 0;
        int itemId = 0;
        int inventoryQuantity = 0;
        int[] values = new int[requiredStock.length];
        for (int i = 0; i < requiredStock.length; i++) {
            itemId = requiredStock[i][0];
            neededQuantity = requiredStock[i][1];
            for (Stock item : inventory) {
                if (itemId == item.getItemId()) {
                    inventoryQuantity = item.getQuantityOnHand();
                    //breaks the for loop once item is found
                    break;
                }
                // int/int yields whole number amount that is needed and stores it in the array
            }
            values[i] = inventoryQuantity/neededQuantity;
        }
        //Sorts the array and returns the lowest value
        Arrays.sort(values);
        return values[0];
    }

    //removes stock needed to build the carburetor and returns a message
    // of rather or not the task is completed successfully
    public void buildCarb(int quantity) {
        if (quantity <= ableToMake()) {
            setQuantityOnHand(getQuantityOnHand()+quantity);
            int neededStockQuantity = 0;
            int itemId = 0;
            int index = 0;
            for (int i = 0; i < requiredStock.length; i++) {
                itemId = requiredStock[i][0];
                neededStockQuantity = requiredStock[i][1];
                for (Stock item : inventory) {
                    if (itemId == item.getItemId()) {
                        index = inventory.indexOf(item);
                        //Decreases stock by the amount of resources required to build the carbs
                        inventory.get(index).useItems(neededStockQuantity * quantity);
                        //once item is found the rest of inventory need not be searched through
                        break;
                    }
                }
            }
        }
        else throw new IllegalArgumentException("Not enough resources to build carb");
    }

    //returns list of String values showing the quantity of items needed, itemName, and itemId
    public String getItemsRequiredToMake() {
        String requiredItems = "Item ID | Item Name | Needed Quantity \n";
        int itemId = 0;
        int neededStockQuantity = 0;
        for (int i = 0; i < requiredStock.length; i++) {
            itemId = requiredStock[i][0];
            neededStockQuantity = requiredStock[i][1];
            for (Stock item : inventory) {
                if (itemId == item.getItemId()) {
                    requiredItems += itemId + " | " + item.getItemName().toUpperCase() + " | " + neededStockQuantity + "\n";
                    //breaks out of for loop once item is found
                    break;
                }
            }
        }
        return requiredItems;
    }

    @Override
    public String toString() {
        return "Carb{" +
                "itemId= '" + getItemId() + '\'' +
                ", Name= '" + getItemName() + '\'' +
                ", Quantity= '" + getQuantityOnHand() + '\'' +
                ", costExpenditure= '$" + getCostExpenditure() + '\'' +
                ", ableToMake= '" + ableToMake() + '\'' +
                '}';
    }
}
