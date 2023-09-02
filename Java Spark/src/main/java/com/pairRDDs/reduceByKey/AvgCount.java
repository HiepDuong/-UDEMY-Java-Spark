package com.pairRDDs.reduceByKey;

import java.io.Serializable;

public class AvgCount implements Serializable {
    private int count;
    private double price;

    public AvgCount(int count, double price) {
        this.count = count;
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public double getprice() {
        return price;
    }
}
