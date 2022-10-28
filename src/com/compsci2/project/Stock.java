package com.compsci2.project;

public class Stock implements Comparable<Stock> {

    private static int stockCount = 0;
    private final int itemId;
    private String itemName;
    private int quantityOnHand;
    private double costExpenditure;
    private double salePrice;

    //this constructor is used when an itemId has already been generated
    public Stock(int itemId, String itemName, int quantityOnHand, double costExpenditurePerItem, double salePrice) {
        this.itemName = itemName;
        this.quantityOnHand = quantityOnHand;
        this.costExpenditure = costExpenditurePerItem;
        this.itemId = itemId;
        this.salePrice = salePrice;
        //stockCount should begin where last recorded inventory item ends
        stockCount=itemId;
    }

    public Stock(String itemName, int quantityOnHand, double costExpenditurePerItem, double salePrice) {
        this.itemName = itemName;
        this.quantityOnHand = quantityOnHand;
        this.costExpenditure = costExpenditurePerItem;
        this.itemId = generateItemId();
        this.salePrice = salePrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public int getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(int quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getCostExpenditure() {
        return costExpenditure;
    }

    public void setCostExpenditure(double costExpenditure) {
        this.costExpenditure = costExpenditure;
    }

    public int getItemId() {
        return itemId;
    }

    private int generateItemId() {
        stockCount++;
        return stockCount;
    }

    public void useItems(int amountUsed) {
        if (quantityOnHand >= amountUsed) quantityOnHand -= amountUsed;
        else throw new IllegalArgumentException("There are not enough items in the inventory to complete the request");
    }

    @Override
    public String toString() {
        return getItemId()+","
                +getItemName()+","
                +getQuantityOnHand()+","
                +getCostExpenditure()+","
                +getSalePrice()+",";
    }

    @Override
    public int compareTo(Stock o) {
        if (this.itemId < o.itemId) return -1;
        else if (this.itemId > o.itemId) return 1;
        else return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Stock)
            return (this.itemId == ((Stock) obj).itemId);
        else return false;
    }
}
