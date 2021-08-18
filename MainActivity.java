package com.example.midpos;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.midpos.DemoClass;
import com.example.midpos.R;
import com.example.midpos.SharedPrefManager;
import com.example.midpos.Start;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText Username, Password;
    CheckBox showe;
    Button login;
    ProgressBar p;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        p = findViewById(R.id.progress_bar);
        login = findViewById(R.id.login);
        if (SharedPrefManager.getInstans(this).isLogin()) {
            DemoClass.setMessage(Username.getText().toString());
            finish();
            startActivity(new Intent(MainActivity.this, Start.class));
            return;
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        showe = findViewById(R.id.show);
        showe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (showe.isChecked()) {
                    Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                p.setVisibility(View.VISIBLE);
                Login();
            }
        });

    }

    private void Login() {
        //first getting the values
        final String username = Username.getText().toString().trim();
        final String password = Password.getText().toString().trim();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            Username.setError("Please enter your username");
            Username.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Password.setError("Please enter your password");
            Password.requestFocus();
            return;
        }
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://demo.midpos.com/Api.php?apicall=login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("Login successfull")) {
                    DemoClass.setMessage(Username.getText().toString());
                    SharedPrefManager.getInstans(getApplicationContext()).userLogin("id", username);
                    Intent CCC = new Intent(MainActivity.this, StoreNumber.class);
                    startActivity(CCC);
                    Toast.makeText(getApplicationContext(),getString(R.string.Login__successfull), Toast.LENGTH_LONG).show();

                } else {
                    p.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), getString(R.string.data_Username_Or_Password_invalid) + response, Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                p.setVisibility(View.INVISIBLE);
                Log.i(TAG, " problem conction " + error.toString());

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);


    }
    @Override
    public void onBackPressed() {
        finish();
    }

}
