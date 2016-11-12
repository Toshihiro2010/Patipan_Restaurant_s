package com.ssru.toshihiro.patipan_restaurant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtSignUser;
    EditText edtSignPass;
    EditText edtSignName;

    String strUser;
    String strPass;
    String strName;

    Button btnSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        bindWidget();
        btnSignUp.setOnClickListener(this);



    }

    private void getStr() {
        strUser = edtSignUser.getText().toString().trim();
        strPass = edtSignPass.getText().toString().trim();
        strName = edtSignName.getText().toString().trim();

    }


    private void bindWidget() {
        edtSignUser = (EditText) findViewById(R.id.editUser);
        edtSignPass = (EditText) findViewById(R.id.editPass);
        edtSignName = (EditText) findViewById(R.id.editName);

        btnSignUp = (Button) findViewById(R.id.buttonSignUp);
    }


    @Override
    public void onClick(View view) {
        if (view == btnSignUp) {
            signUpMain();
        }
    }

    private void signUpMain() {
        getStr();
        if (strUser.equals("") || strPass.equals("") || strName.equals("")) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "เกิดข้อผิดพลาด", "อย่ามีช่องว่างสิไอ้โง่");
        } else {
            Log.d("Restaurant->", "Data submit");
            updateValueToServer();
        }
    }

    private void updateValueToServer() {
        String strURL = "http://csclub.ssru.ac.th/s56122201044/csc4202/php_add_user.php";
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("User", strUser)
                .add("Password", strPass)
                .add("Name", strName)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(strURL).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("D-->", "onFailure");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    Log.d("Restaurant->", "Data Sucsess");
                    finish();
                } catch (Exception e) {
                    Log.d("Restaurant", "error " + e.toString());
                }

            }
        });

    }
}
