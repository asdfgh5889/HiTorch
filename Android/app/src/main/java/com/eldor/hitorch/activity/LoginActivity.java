package com.eldor.hitorch.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.eldor.hitorch.R;
import com.eldor.hitorch.data.Common;
import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.body.JSONObjectBody;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    private EditText email_;
    private EditText password_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email_ = findViewById(R.id.email);
        password_ = findViewById(R.id.pwd);
    }


    public void check_login(View view) {
        String email = email_.getText().toString();
        String password = password_.getText().toString();

        Ion.with(LoginActivity.this).load("POST",Common.BASE_URL+"login")
                .setBodyParameter("email", email)
                .setBodyParameter("password",password)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try{
                            JSONObject object = new JSONObject(result);
                            //Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                            boolean isLogin = object.getBoolean("ok");
                            if(isLogin){
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, "Bye", Toast.LENGTH_SHORT).show();
                            }
                        }catch(JSONException ex){
                            ex.printStackTrace();
                        }
                    }
                });
    }
}
