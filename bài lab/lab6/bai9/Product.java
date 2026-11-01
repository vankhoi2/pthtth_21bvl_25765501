package bai9;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private double currentPrice;
    private String highestBidder;

    public Product(String name, double startPrice) {
        this.name = name;
        this.currentPrice = startPrice;
        this.highestBidder = "Chưa có";
    }

    public String getName() {
        return name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getHighestBidder() {
        return highestBidder;
    }

    public void setHighestBidder(String highestBidder) {
        this.highestBidder = highestBidder;
    }
}