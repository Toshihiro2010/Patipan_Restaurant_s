package com.ssru.toshihiro.patipan_restaurant;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private UserTABLE objUserTABLE;
    private FoodTABLE objFoodTABLE;
    private OrderTABLE objOrderTABLE;

    private MySQLite mySQLite;

    private EditText edtUser;
    private EditText edtPass;

    private String txtUser;
    private String txtPass;

    private Button btnSignIn;
    private Button btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //bindview
        binWidget();

        conectedSQLite();

        synAndDelete();

        testAddValue();



        mySQLite = new MySQLite(this);
    }

    public void clickSignInmain(View view) {
        txtUser = edtUser.getText().toString().trim();
        txtPass = edtPass.getText().toString().trim();

        if (txtUser.equals("") || txtPass.equals("")) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง", "อยามีช่องว่างสิไอ้โง่");
        } else {
            checkUser();
        }
    }

    private void checkUser() {
        try {
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MySQLiteOpenHelper.DATABASE_NAME, MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE WHERE User = " + "'" + txtUser + "'",null);
            cursor.moveToFirst();
            String[] resultStrings = new String[cursor.getColumnCount()];

            for (int i = 0 ; i<cursor.getColumnCount();i++) {
                resultStrings[i] = cursor.getString(i);
            }
            cursor.close();

            if (txtPass.equals(resultStrings[2])){
                Toast.makeText(MainActivity.this, "ยินดีต้อนรับ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ShowProduct.class);
                intent.putExtra("Result", resultStrings);
                startActivity(intent);
                finish();
            }

        } catch (Exception e) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "ไม่มี user ", "ไม่มี " + txtUser + " ในฐานข้อมูล");

        }
    }

    private void binWidget() {
        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPass = (EditText) findViewById(R.id.edtPass);


    }

    private void synAndDelete() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MySQLiteOpenHelper.DATABASE_NAME, MODE_PRIVATE, null);
        sqLiteDatabase.delete(MySQLite.user_table, null, null);
        MySynJSON mySynJSON = new MySynJSON();
        mySynJSON.execute();
    }

    public class MySynJSON extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                String strURL = "http://csclub.ssru.ac.th/s56122201044/csc4202/php_get_userTABLE.php";
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strURL).build();
                Response response = okHttpClient.newCall(request).execute();

                return response.body().string();
            } catch (IOException e) {
                Log.d("Toshihiro", "doInBack ==>" + e.toString());

                return null;
            }

        } // doInback

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Toshihiro", "strJSON ==> " + s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0 ; i <jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String strUser = jsonObject.getString(MySQLite.column_user);
                    String strPassword = jsonObject.getString(MySQLite.column_password);
                    String strName = jsonObject.getString(MySQLite.column_name);
                    mySQLite.addNewUser(strUser, strPassword, strName);


                }
                Toast.makeText(getApplicationContext(), "Synchronize mySQL to SQLite Finish", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                Log.d("Toshihiro" , "onPost ==> " + e.toString());
            }
        }
    }



    private void testAddValue() {


        objUserTABLE.addNewUser("testUser", "testPass", "testName");
        objFoodTABLE.addNewFood("testFood", "testSource", "testPrice");
        objOrderTABLE.addOrder("testOfficer", "testDesk", "testFood", "testItem");

    }

    private void conectedSQLite() {
        objUserTABLE = new UserTABLE(this);
        objFoodTABLE = new FoodTABLE(this);
        objOrderTABLE = new OrderTABLE(this);
    }
}
