package com.ssru.toshihiro.patipan_restaurant;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.widget.ListView;

import com.squareup.okhttp.OkHttpClient;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


import org.json.JSONArray;

import org.json.JSONObject;



public class ShowProduct extends AppCompatActivity {

    private ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
        bindWidget();



        SynJSON synJSON = new SynJSON();
        synJSON.execute();


    }



    private void bindWidget() {
        listView = (ListView) findViewById(R.id.listProduct);
        Log.d("Widget", "bind Widget");
    }//Bind Widget

    private class SynJSON extends AsyncTask<Void , Void ,String>{

        @Override
        protected String doInBackground(Void... params) {

            try {
                String strURL = "http://csclub.ssru.ac.th/s56122201044/csc4202/php_get_foodTABLE.php";
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strURL).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {
                Log.d("23/11/59", "doIn ==> " + e.toString());
            }

            return null;
        }//do Inback

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                Log.d("23/11/59", "Suces Onpost ==> " + s);

                JSONArray jsonArray = new JSONArray(s);


                String[] iconString = new String[jsonArray.length()];
                String[] titleString = new String[jsonArray.length()];
                String[] priceString = new String[jsonArray.length()];



                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    iconString[i] = jsonObject.getString("Source");
                    titleString[i] = jsonObject.getString("Food");
                    priceString[i] = jsonObject.getString("Price");
                }


                ProductAdapter productAdapter = new ProductAdapter(ShowProduct.this,iconString,titleString,priceString);
                listView.setAdapter(productAdapter);



            } catch (Exception e) {
                Log.d("23/11/59", "onPost ==> " + e.toString());
            }
        }//Post


    }//SynJson


}//Main class
