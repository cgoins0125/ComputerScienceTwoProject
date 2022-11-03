package com.compsci2.project;

public class SalesReport implements Comparable<SalesReport>{

    private final int saleId;
    private final int customerId;
    private final double subtotal;
    private final double salesTax;
    private final double totalSale;
    private final double costExpenditure;
    private final double profit;
    private final String sellDate;

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

    public int getSaleId() {
        return saleId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getSalesTax() {
        return salesTax;
    }

    public double getTotalSale() {
        return totalSale;
    }

    public double getCostExpenditure() {
        return costExpenditure;
    }

    public double getProfit() {
        return profit;
    }

    public String getSellDate() {
        return sellDate;
    }

    @Override
    public int compareTo(SalesReport o) {
            if (this.saleId < o.saleId) return -1;
            else if (this.saleId > o.saleId) return 1;
            else return 0;
    }
}
