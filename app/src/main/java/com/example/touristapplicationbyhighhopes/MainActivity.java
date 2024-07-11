    package com.example.touristapplicationbyhighhopes;

    import android.content.Intent;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.text.InputType;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.RelativeLayout;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import com.google.gson.Gson;

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
    import okhttp3.ResponseBody;

    public class MainActivity extends AppCompatActivity {

        private static final String TAG = "MainActivity";
        private EditText etEmail, etPassword;
        private ImageView ivShowHidePassword;
        private Button btnLogin;
        private TextView tvSignup;
        private boolean isPasswordVisible = false;
        OkHttpClient client;
        private RelativeLayout loader;
        private String url = "https://tourist-application-proxy.onrender.com";

        boolean isLogin = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            etEmail = findViewById(R.id.etEmail);
            etPassword = findViewById(R.id.etPassword);
            ivShowHidePassword = findViewById(R.id.ivShowHidePassword);
            btnLogin = findViewById(R.id.btnLogin);
            tvSignup = findViewById(R.id.tvSignup);
            client = new OkHttpClient();
            loader = findViewById(R.id.loadingPanel);



            // Initialize MongoDBService
            ivShowHidePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    togglePasswordVisibility();
                }
            });

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = etEmail.getText().toString().trim();
                    String password = etPassword.getText().toString().trim();

                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    }else{
                        try {
                            btnLogin.setEnabled(false);
                            loader.setVisibility(View.VISIBLE);
                            authenticateUser(email, password);
                        } catch (JSONException e) {
                            btnLogin.setEnabled(true);
                            loader.setVisibility(View.INVISIBLE);
                            throw new RuntimeException(e);
                        }
                    }
                }
            });

            tvSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Redirect to signup activity
                    Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                    startActivity(intent);
                }
            });
        }

        private void togglePasswordVisibility() {
            if (isPasswordVisible) {
                // Hide Password
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                ivShowHidePassword.setImageResource(R.drawable.ic_eye_closed);
            } else {
                // Show Password
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                ivShowHidePassword.setImageResource(R.drawable.ic_eye_open);
            }
            isPasswordVisible = !isPasswordVisible;
            // Move the cursor to the end of the text
            etPassword.setSelection(etPassword.getText().length());
        }

        private void authenticateUser(final String email, final String password) throws JSONException {

            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("password", password);

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, json.toString());
            Request request = new Request.Builder().url(url + "/api/user/login").post(body).build();

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
                                btnLogin.setEnabled(true);
                                int code = response.code();

                                if(code == 200){
                                    Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(MainActivity.this, "Email or password is incorrect.", Toast.LENGTH_SHORT).show();
                                }

                             Log.i("tae", response.body().string());
                            }  catch (IOException e) {
                                loader.setVisibility(View.INVISIBLE);
                                btnLogin.setEnabled(true);
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            });
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            // Ensure MongoDB client is closed when activity is destroyed

        }

        private class VoidVoidBooleanAsyncTask extends AsyncTask<Void, Void, Boolean> {
            private final String email;
            private final String password;

            public VoidVoidBooleanAsyncTask(String email, String password) {
                this.email = email;
                this.password = password;
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    // Query MongoDB to find user with matching email and password
    //                return mongoDBService.authenticateUser(email, password);
                    return false;
                } catch (Exception e) {
                    Log.e(TAG, "Error authenticating user from MongoDB: " + e.getMessage());
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean isAuthenticated) {
                if (isAuthenticated) {
                    // Proceed to home page
                    Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                    startActivity(intent);
                    finish(); // Optional: Prevent going back to MainActivity on back press
                } else {
                    Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
                // Close MongoDB client when done
    //            mongoDBService.close();
            }
        }
    }
