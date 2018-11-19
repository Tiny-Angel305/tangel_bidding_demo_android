package com.test.tangel.biddingdemo.dummy;

public class Product {
    public int ID;
    public int ReservePrice;
    public int CurrentPrice;
    public String StartTime;
    public String EndTime;

    public  Product(int _ID, int _ReservePrice, int _CurrentPrice, String _StartTime, String _EndTime) {
        ID = _ID;
        ReservePrice = _ReservePrice;
        CurrentPrice = _CurrentPrice;
        StartTime = _StartTime;
        EndTime = _EndTime;
    }
}
