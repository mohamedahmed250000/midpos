package com.example.midpos;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class Add_pro extends AppCompatActivity implements BarcodeRetriever {
    EditText bar, name, pric, quant,color,size,expier;
    String Barcod, Name, Price, Qunatity,color1, size1, expier1;
    Button add, goback;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pro);
        BarcodeCapture barcodeCapture = (BarcodeCapture) getSupportFragmentManager().findFragmentById(R.id.barcode_add2);
        barcodeCapture.setRetrieval((BarcodeRetriever) this);
        bar = findViewById(R.id.new_barcode);
        name = findViewById(R.id.new_name);
        pric = findViewById(R.id.new_price);
        quant = findViewById(R.id.new_quantity);
        add = findViewById(R.id.add_prod_info);
        color= findViewById(R.id.new_color);
        size = findViewById(R.id.new_size);
        expier = findViewById(R.id.new_expier);
        goback = findViewById(R.id.go__back);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        expier.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(Add_pro.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(bar.getText().toString())) {
                    bar.setError("enter the barcode");
                    Toast.makeText(Add_pro.this, "enter the barcode", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(name.getText().toString())) {
                    name.setError("enter the name");
                    Toast.makeText(Add_pro.this, "enter the name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(pric.getText().toString())) {
                    pric.setError("enter the price");
                    Toast.makeText(Add_pro.this, "enter the price", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(quant.getText().toString())) {
                    quant.setError("enter the quantity");
                    Toast.makeText(Add_pro.this, "enter the quantity", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(expier.getText().toString())) {
                    expier.setError("enter the expier");
                    Toast.makeText(Add_pro.this, "enter the expier", Toast.LENGTH_LONG).show();
                    return;
                }
                sen_pro_info();
            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Add_pro.this, Start.class));
            }
        });
    }

    @Override
    public void onRetrieved(final Barcode barcode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bar.setText(barcode.displayValue);
            }
        });
    }

    @Override
    public void onRetrievedMultiple(Barcode closetToClick, List<BarcodeGraphic> barcode) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onRetrievedFailed(String reason) {

    }

    @Override
    public void onPermissionRequestDenied() {

    }

    public void sen_pro_info() {
        RequestQueue queue = Volley.newRequestQueue(this);
        Barcod = bar.getText().toString();
        Name = name.getText().toString();
        Price = pric.getText().toString();
        Qunatity = quant.getText().toString();
        color1 = color.getText().toString();
        size1 = size.getText().toString();
        expier1 = expier.getText().toString();
        final String Url = "https://demo.midpos.com/productAddApi.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, Url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(Add_pro.this, "ok"+response, Toast.LENGTH_LONG).show();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Add_pro.this, getString(R.string.Fail_Conction) + error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("barcod", Barcod);
                param.put("name", Name);
                param.put("price", Price);
                param.put("quantity", Qunatity);
                param.put("color", color1);
                param.put("size", size1);
                param.put("expier", expier1);
                return param;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
        bar.setText("");
        name.setText("");
        pric.setText("");
        quant.setText("");
        color.setText("");
        size.setText("");
        expier.setText("");
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
    private void updateLabel() {
        String myFormat = "MM-dd-yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        expier.setText(sdf.format(myCalendar.getTime()));
    }


}

