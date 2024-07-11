package com.example.touristapplicationbyhighhopes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignupActivity extends AppCompatActivity {

    private EditText etSignupUsername, etSignupEmail, etSignupPassword, etAddress, etSignupPhone;
    private ImageView ivShowHideSignupPassword;
    private Button btnSignup;
    private boolean isPasswordVisible = false;
    private RelativeLayout loader;
    OkHttpClient client;
    private String url = "https://tourist-application-proxy.onrender.com";
   // private MongoDBService mongoDBService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etSignupUsername = findViewById(R.id.etSignupUsername);
        etSignupEmail = findViewById(R.id.etSignupEmail);
        etSignupPassword = findViewById(R.id.etSignupPassword);
        etSignupPhone = findViewById(R.id.etSignupPhone);
        ivShowHideSignupPassword = findViewById(R.id.ivShowHideSignupPassword);
        etAddress = findViewById(R.id.etSignupAddress);
        btnSignup = findViewById(R.id.btnSignup);
        loader = findViewById(R.id.loadingPanel);
        client = new OkHttpClient();

        // Initialize MongoDBService
        //mongoDBService = new MongoDBService();

        // Set click listener on the eye icon to toggle password visibility
        ivShowHideSignupPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });

        // Set click listener on the signup button
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etSignupUsername.getText().toString().trim();
                String email = etSignupEmail.getText().toString().trim();
                String password = etSignupPassword.getText().toString().trim();
                String phone = etSignupPhone.getText().toString().trim();
                String address = etAddress.getText().toString().trim();

                if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    btnSignup.setEnabled(false);
                    loader.setVisibility(View.VISIBLE);

                    try {
                        signupUser(email,username,address,phone,password);
                    } catch (JSONException e) {
                        btnSignup.setEnabled(true);
                        loader.setVisibility(View.INVISIBLE);
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        // Initially, set password as hidden
        etSignupPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide Password
            etSignupPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ivShowHideSignupPassword.setImageResource(R.drawable.ic_eye_closed);
        } else {
            // Show Password
            etSignupPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            ivShowHideSignupPassword.setImageResource(R.drawable.ic_eye_open);
        }
        isPasswordVisible = !isPasswordVisible;
        // Move the cursor to the end of the text
        etSignupPassword.setSelection(etSignupPassword.getText().length());
    }

    private void signupUser(final String email, final String username, final String address, final String phone, final String password) throws JSONException {

        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("email", email);
        json.put("phone", phone);
        json.put("address", address);
        json.put("password", password);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request = new Request.Builder().url(url + "/api/user/signup").post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            loader.setVisibility(View.INVISIBLE);
                            btnSignup.setEnabled(true);
                            int code = response.code();

                            if(code == 200){
                                Toast.makeText(SignupActivity.this, "Signup successful, please login.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                intent.putExtra("email", email); // Pass email to login page
                                startActivity(intent);
                                finish(); // Close SignupActivity to prevent going back on back press
                            }else{
                                Toast.makeText(SignupActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                            }

                            Log.i("tae", response.body().string());
                        }  catch (IOException e) {
                            loader.setVisibility(View.INVISIBLE);
                            btnSignup.setEnabled(true);
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
    }
}
