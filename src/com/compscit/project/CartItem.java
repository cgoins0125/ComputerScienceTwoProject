package com.compscit.project;

/**
 * Class is used for keeping up with items added to the cart
 */
public class CartItem {
    private int itemId;
    private String itemName;
    private int quantity;
    private double price;

    /**
     * @param itemId Inventory Id of the Stock added to the Cart
     * @param itemName Name of Inventory Item added to the Cart
     * @param quantity Quantity of stock in the Cart
     * @param price Price per Item
     */
    public CartItem(int itemId, String itemName, int quantity, double price) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     *
     * @return CartItem id
     */
    public int getItemId() {
        return itemId;
    }

    /**
     *
     * @return CartItem Name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     *
     * @return CartItem quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity desired quantity of CartItem
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return CartItem price
     */
    public double getPrice() {
        return price;
    }

    /**
     *
     * @return subtotal of CartItem
     */
    public double getSubtotal () {
        double subtotal = price * quantity;
        return Double.parseDouble(Sale.df.format(subtotal));
    }
}
