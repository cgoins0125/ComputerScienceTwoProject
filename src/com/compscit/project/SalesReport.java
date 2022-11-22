package com.compscit.project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Used for keeping a record of Sales when they occur
 */
public class SalesReport implements Comparable<SalesReport>{

    private final int saleId;
    private final int customerId;
    private final double subtotal;
    private final double salesTax;
    private final double totalSale;
    private final double costExpenditure;
    private final double profit;
    private final String sellDate;

    /**
     * The parameters should be directly taken from the Sale
     * @param saleId
     * @param customerId
     * @param subtotal
     * @param salesTax
     * @param totalSale
     * @param costExpenditure
     * @param profit
     * @param sellDate
     */
    public SalesReport(int saleId, int customerId, double subtotal, double salesTax, double totalSale,
                       double costExpenditure, double profit, String sellDate) {
        this.saleId = saleId;
        this.customerId = customerId;
        this.subtotal = subtotal;
        this.salesTax = salesTax;
        this.totalSale = totalSale;
        this.costExpenditure = costExpenditure;
        this.profit = profit;
        this.sellDate = sellDate;
    }

    /**
     *
     * @return saleId
     */
    public int getSaleId() {
        return saleId;
    }

    /**
     *
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     *
     * @return subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     *
     * @return salesTax
     */
    public double getSalesTax() {
        return salesTax;
    }

    /**
     *
     * @return subtotal + salesTax
     */
    public double getTotalSale() {
        return totalSale;
    }

    /**
     *
     * @return The amount invested in the items sold
     */
    public double getCostExpenditure() {
        return costExpenditure;
    }

    /**
     *
     * @return profit made from the sell
     */
    public double getProfit() {
        return profit;
    }

    /**
     *
     * @return the date the Sell occurred
     */
    public String getSellDate() {
        return sellDate;
    }

    /**
     * Determines if the sale occurred within a specific range
     * @param fromMonth
     * @param fromDay
     * @param fromYear
     * @param toMonth
     * @param toDay
     * @param toYear
     * @return true if the sale is in the given range
     * @throws ParseException
     */
    public boolean inDateRange(int fromMonth, int fromDay, int fromYear, int toMonth, int toDay, int toYear) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date fromDate = sdf.parse(fromMonth+"/"+fromDay+"/"+fromYear);
        Date toDate = sdf.parse(toMonth+"/"+toDay+"/"+toYear);
        Date sellDate = sdf.parse(this.sellDate);

        if (fromDate.before(sellDate) && toDate.after(sellDate)) {
            return true;
        } if (fromDate.equals(sellDate)) {
            return true;
        } if (toDate.equals(sellDate)) {
            return true;
        }
        else return false;
    }

    /**
     *
     * @return String representation of a sale formatted for CSV file
     */
    @Override
    public String toString() {
        return getSaleId() + ","
                + getCustomerId() + ","
                + getSubtotal() + ","
                + getSalesTax() + ","
                + getTotalSale() + ","
                + getCostExpenditure() + ","
                + getProfit() + ","
                + getSellDate() + ',';
    }

    /**
     *
     * @param o the object to be compared.
     * @return if this sale happened before or after another
     */
    @Override
    public int compareTo(SalesReport o) {
            if (this.saleId < o.saleId) return -1;
            else if (this.saleId > o.saleId) return 1;
            else return 0;
    }
}
