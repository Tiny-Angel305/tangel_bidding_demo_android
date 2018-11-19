package com.test.tangel.biddingdemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.test.tangel.biddingdemo.dummy.DummyData;
import com.test.tangel.biddingdemo.dummy.Product;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

public class MainActivity extends Activity {

    private String TAG = MainActivity.class.getSimpleName();
    private static final String PREFS = "test.biddingdemo.prefs";

    private static final int PRODUCT_COUNT = 4;
    private JSONObject[] mProducts = new JSONObject[PRODUCT_COUNT];
    private static int mSelectedIndex = 0;

    private ListView lv;
    private ProductListAdapter lvAdapter = new ProductListAdapter();
    private Button[] mBidButtons = new Button[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 5; i++) {
            mBidButtons[i] = (Button) findViewById(R.id.bid1 + i);
            mBidButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;
                    try {
                        mProducts[mSelectedIndex].put("current_price", Integer.valueOf((String) b.getText()));
                        getSharedPreferences(PREFS, 0).edit().putString("product" + (mSelectedIndex+1), String.valueOf(mProducts[mSelectedIndex].getInt("id")) + ";"
                                + String.valueOf(mProducts[mSelectedIndex].getInt("reserve_price")) + ";"
                                + String.valueOf(mProducts[mSelectedIndex].getInt("current_price")) + ";"
                                + mProducts[mSelectedIndex].getString("start_time") + ";"
                                + mProducts[mSelectedIndex].getString("end_time") + ";")
                                .apply();

                        lv.setAdapter(lvAdapter);
                        updateBidValues();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        lv = (ListView) findViewById(R.id.list);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedIndex = position;
                lv.setAdapter(lvAdapter);
                updateBidValues();
            }
        });

        new LoadProductsWithJSON().execute();
    }

    private void updateBidValues() {
        for (int i = 0; i < 5; i++) {
            String text = "";
            try {
                text = String.valueOf(mProducts[mSelectedIndex].getInt("current_price") + 50 * (i+1));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mBidButtons[i].setText(text);
        }
    }

    private class LoadProductsWithJSON extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            createDummyData();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            lv.setAdapter(lvAdapter);
        }
    }

    private class ProductListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return PRODUCT_COUNT;
        }

        @Override
        public JSONObject getItem(int position) {
            return mProducts[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item, parent, false);

                if (position == mSelectedIndex) {
                    convertView.setBackgroundColor(Color.argb(80, 100, 100, 100));
                }else {
                    convertView.setBackgroundColor(Color.argb(80, 200, 200, 200));
                }

                try {
                    ((TextView)convertView.findViewById(R.id.id)).setText(String.valueOf(getItem(position).getInt("id")));
                    ((TextView)convertView.findViewById(R.id.reserve_price)).setText(String.valueOf(getItem(position).getInt("reserve_price")));
                    ((TextView)convertView.findViewById(R.id.current_price)).setText(String.valueOf(getItem(position).getInt("current_price")));
                    ((TextView)convertView.findViewById(R.id.start_time)).setText(getItem(position).getString("start_time"));
                    ((TextView)convertView.findViewById(R.id.end_time)).setText(getItem(position).getString("end_time"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return convertView;
        }
    }

    private void createDummyData() {
        DummyData dummy = new DummyData();

        SharedPreferences sp = getSharedPreferences(PREFS, 0);

        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();

        int id, reservePrice, currentPrice;
        String[] fields;

        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy", Locale.US);

        // -- product1 -- //
        id = 1;
        reservePrice = 120;
        currentPrice = 40;
        startTime.set(2018, 10, 18);
        endTime.set(2018, 10, 29);

        String productData = sp.getString("product1", String.valueOf(id) + ";" + String.valueOf(reservePrice) + ";" + String.valueOf(currentPrice) + ";" +
                sdf.format(startTime.getTime()) + ";" + sdf.format(endTime.getTime()));

        fields = productData.split(";");
        dummy.addProduct(new Product(Integer.valueOf(fields[0]), Integer.valueOf(fields[1]), Integer.valueOf(fields[2]), fields[3], fields[4]));

        // -- product2 -- //
        id = 2;
        reservePrice = 300;
        currentPrice = 70;
        startTime.set(2018, 11, 1);
        endTime.set(2018, 11, 31);

        productData = sp.getString("product2", String.valueOf(id) + ";" + String.valueOf(reservePrice) + ";" + String.valueOf(currentPrice) + ";" +
                sdf.format(startTime.getTime()) + ";" + sdf.format(endTime.getTime()));

        fields = productData.split(";");
        dummy.addProduct(new Product(Integer.valueOf(fields[0]), Integer.valueOf(fields[1]), Integer.valueOf(fields[2]), fields[3], fields[4]));

        // -- product3 -- //
        id = 3;
        reservePrice = 90;
        currentPrice = 14;
        startTime.set(2018, 10, 1);
        endTime.set(2018, 10, 25);

        productData = sp.getString("product3", String.valueOf(id) + ";" + String.valueOf(reservePrice) + ";" + String.valueOf(currentPrice) + ";" +
                sdf.format(startTime.getTime()) + ";" + sdf.format(endTime.getTime()));

        fields = productData.split(";");
        dummy.addProduct(new Product(Integer.valueOf(fields[0]), Integer.valueOf(fields[1]), Integer.valueOf(fields[2]), fields[3], fields[4]));

        // -- product4 -- //
        id = 4;
        reservePrice = 400;
        currentPrice = 110;
        startTime.set(2019, 0, 1);
        endTime.set(2019, 1, 27);

        productData = sp.getString("product4", String.valueOf(id) + ";" + String.valueOf(reservePrice) + ";" + String.valueOf(currentPrice) + ";" +
                sdf.format(startTime.getTime()) + ";" + sdf.format(endTime.getTime()));

        fields = productData.split(";");
        dummy.addProduct(new Product(Integer.valueOf(fields[0]), Integer.valueOf(fields[1]), Integer.valueOf(fields[2]), fields[3], fields[4]));

        // t@ngel : create 4 JSONObjects {
        try {
            for (int i = 0; i < PRODUCT_COUNT; i ++) {
                mProducts[i] = new JSONObject();
                mProducts[i].put("id", dummy.getProduct(i).ID);
                mProducts[i].put("reserve_price", dummy.getProduct(i).ReservePrice);
                mProducts[i].put("current_price", dummy.getProduct(i).CurrentPrice);
                mProducts[i].put("start_time", dummy.getProduct(i).StartTime);
                mProducts[i].put("end_time", dummy.getProduct(i).EndTime);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        updateBidValues();
        // t@ngel }
    }
}