package com.test.tangel.biddingdemo.dummy;

import java.util.ArrayList;

public class DummyData {
    private ArrayList<Product> products;
    private int count;

    public DummyData() {
        count = 0;
        products = new ArrayList<>();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public int getCount() {
        return count;
    }

    public Product getProduct(int index) {
        return products.get(index);
    }

    public void addProduct(Product product) {
        products.add(product);
        count ++;
    }

    public void removeProduct(int index) {
        products.remove(index);
        count --;
    }

    public void init() {
        products.clear();
        count --;
    }
}
