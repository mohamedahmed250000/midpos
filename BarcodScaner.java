package com.example.midpos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.midpos.ListAdaptor;
import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Calendar;

import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class BarcodScaner extends AppCompatActivity implements BarcodeRetriever {
    EditText code, quntity, paid;
    TextView name, price, prod_total_price, total, item_postion, item_quntaity, nameOfUser, store_nu, back_cach;
    Button add, check, inc, dec, cancel_order;
    ListView list_user;
    String prname, prcode, prquntity, prprice, pr_total_price, id, ffinal, ch_quantity,barcode;
    ListAdaptor myadaptor;
    MediaPlayer player;
    ArrayList<Prodect> mymy;
    String currentDateandTime;
    double pro_i_price, x = 0, y;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    public void test_get() {
        RequestQueue queue = Volley.newRequestQueue(this);
        prcode = code.getText().toString();
        final String Url = "https://demo.midpos.com/productapi.php?barcode=" + prcode;
        JsonObjectRequest get = new JsonObjectRequest(Request.Method.GET, Url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String n, p, nn;
                try {
                    JSONObject g = response.getJSONObject("product");


                    ffinal = prcode;
                    name = findViewById(R.id.namep);
                    price = findViewById(R.id.pricep);
                    paid = findViewById(R.id.paid);
                    back_cach = findViewById(R.id.back_cache);
                    try {
                        n = (new String(g.getString("p_name").getBytes("utf-8")));
                        name.setText(n);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    p = g.getString("price");

                    ch_quantity = g.getString("totalquantity");
                    price.setText(p);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                player = MediaPlayer.create(BarcodScaner.this, R.raw.beep);
                player.start();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BarcodScaner.this, " s_a_d ", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(get);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcod_scaner);
        BarcodeCapture barcodeCapture = (BarcodeCapture) getSupportFragmentManager().findFragmentById(R.id.barcode);
        barcodeCapture.setRetrieval((BarcodeRetriever) this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.welcome));
        actionBar = getSupportActionBar();
        nameOfUser = findViewById(R.id.nameOfUser2);
        store_nu = findViewById(R.id.numberOfStore3);
        nameOfUser.setText(SharedPrefManager.getInstans(this).getUsername());

        SharedPreferences test_name = getSharedPreferences("NAME", 0);
        store_nu.setText(test_name.getString("name", ""));
    Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        currentDateandTime = sdf.format(c.getTime());

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#141d08"));
        actionBar.setBackgroundDrawable(colorDrawable);


        name = findViewById(R.id.namep);
        code = findViewById(R.id.codep);
        quntity = findViewById(R.id.quntityp);
        price = findViewById(R.id.pricep);
        prod_total_price = findViewById(R.id.myphoto);
        total = findViewById(R.id.total);
        add = findViewById(R.id.add);
        inc = findViewById(R.id.incr);
        dec = findViewById(R.id.decr);
        item_postion = findViewById(R.id.text_of_item_postion);
        item_quntaity = findViewById(R.id.quntatiy);
        back_cach = findViewById(R.id.back_cache);
        paid = findViewById(R.id.paid);
        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item_quntaity.getText().length() > 0) {
                    double n = Double.parseDouble(item_quntaity.getText().toString());
                    int pp = Integer.parseInt(item_postion.getText().toString());
                    double last_total = Double.parseDouble(total.getText().toString());
                    double last_total_of_prod = Double.parseDouble(myadaptor.getItem(pp).getProdect_total_price());
                    double updata = last_total - last_total_of_prod;
                    n++;
                    myadaptor.getItem(pp).setProdect_quntity(Double.toString(n));
                    myadaptor.notifyDataSetChanged();
                    item_quntaity.setText(Double.toString(n));
                    last_total = updata + (n * Double.parseDouble(myadaptor.getItem(pp).getProdect_price()));
                    total.setText(Double.toString(last_total));
                    double zz = (n * Double.parseDouble(myadaptor.getItem(pp).getProdect_price()));
                    myadaptor.getItem(pp).setProdect_total_price(Double.toString(zz));
                    myadaptor.notifyDataSetChanged();
                } else {
                    Toast.makeText(BarcodScaner.this, getString(R.string.select_item_to_change), Toast.LENGTH_LONG).show();
                }
            }
        });
        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item_quntaity.getText().length() > 0) {

                    double n = Double.parseDouble(item_quntaity.getText().toString());
                    int pp = Integer.parseInt(item_postion.getText().toString());
                    double last_total = Double.parseDouble(total.getText().toString());
                    double last_total_of_prod = Double.parseDouble(myadaptor.getItem(pp).getProdect_total_price());
                    double updata = last_total - last_total_of_prod;
                    n--;
                    if (n != 0) {
                        myadaptor.getItem(pp).setProdect_quntity(Double.toString(n));
                        myadaptor.notifyDataSetChanged();
                        item_quntaity.setText(Double.toString(n));
                        last_total = updata + (n * Double.parseDouble(myadaptor.getItem(pp).getProdect_price()));
                        total.setText(Double.toString(last_total));
                        double zz = (n * Double.parseDouble(myadaptor.getItem(pp).getProdect_price()));
                        myadaptor.getItem(pp).setProdect_total_price(Double.toString(zz));
                        myadaptor.notifyDataSetChanged();
                    } else {
                        Toast.makeText(BarcodScaner.this, getString(R.string.there_is_no_quantity), Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(BarcodScaner.this, getString(R.string.select_item_to_change), Toast.LENGTH_LONG).show();
                }
            }
        });
        check = findViewById(R.id.ch);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                test_req();
                test_get();
            }
        });
        cancel_order = findViewById(R.id.re);
        cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myadaptor.clear();
                myadaptor.notifyDataSetChanged();
                x = 0;
                total.setText(Double.toString(x));
                name.setText("");
                code.setText("");
                quntity.setText("");
                price.setText("");
                total.setText("");
                back_cach.setText("");

            }
        });

        list_user = findViewById(R.id.prun_list);
        final ArrayList<Prodect> prodects = new ArrayList<>();

        myadaptor = new ListAdaptor(this, R.layout.item_user, prodects);
        list_user.setAdapter(myadaptor);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                prname = name.getText().toString();
                prcode = code.getText().toString();
                prquntity = quntity.getText().toString();
                if (TextUtils.isEmpty(prcode)) {
                    code.setError(getString(R.string.error_code));

                    Toast.makeText(BarcodScaner.this, getString(R.string.error_code), Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(prquntity)) {
                    quntity.setError(getString(R.string.enter_the_quantity));
                    Toast.makeText(BarcodScaner.this, getString(R.string.enter_the_quantity), Toast.LENGTH_LONG).show();
                    return;
                }
                if (Double.parseDouble(prquntity) > Double.parseDouble(ch_quantity)) {
                    Toast.makeText(BarcodScaner.this, getString(R.string.there_is_no_quantity_in_store), Toast.LENGTH_LONG).show();
                } else if (Double.parseDouble(prquntity) <= Double.parseDouble(ch_quantity)) {
                    if (name.getText().length() > 0 && price.getText().length() > 0) {
                        pro_i_price = Double.parseDouble(price.getText().toString());
                        double my_quntity = Double.parseDouble(quntity.getText().toString());
                        double tito = pro_i_price * my_quntity;
                        prprice = Double.toString(pro_i_price);
                        x = x + tito;
                        total.setText(Double.toString(x));
                        pr_total_price = prod_total_price.getText().toString();
                        double me = Double.parseDouble(prquntity) * Double.parseDouble(prprice);

                        pr_total_price = Double.toString(me);
                        prodects.add(new Prodect(prname, ffinal, prquntity, prprice, pr_total_price));
                        myadaptor.notifyDataSetChanged();
                    } else {
                        Toast.makeText(BarcodScaner.this, getString(R.string.Enter_All_Data), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(BarcodScaner.this, "error json containt", Toast.LENGTH_LONG).show();
                }
            }
        });

        list_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, final int position, long id) {
                item_postion.setText(Integer.toString(position));
                item_quntaity.setText(myadaptor.getItem(position).getProdect_quntity());
            }
        });
        list_user.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(BarcodScaner.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + position);
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        double r = Double.parseDouble(myadaptor.getItem(position).getProdect_total_price());
                        double p = Double.parseDouble(total.getText().toString());
                        x = p - r;
                        total.setText(Double.toString(x));
                        if (myadaptor.getCount() == 0) {
                            x = 0;
                        }
                        myadaptor.remove(myadaptor.getItem(position));
                        myadaptor.notifyDataSetChanged();
                    }
                });
                adb.show();
                return false;
            }
        });


    }

    public void get_pill(final JSONArray pill) {
        RequestQueue queue = Volley.newRequestQueue(this);
    }

    public void test_req() {
        RequestQueue queue = Volley.newRequestQueue(this);
        prcode = code.getText().toString();
        final String Url = "https://demo.midpos.com/productapi.php?barcode=";
        StringRequest postRequest = new StringRequest(Request.Method.POST, Url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BarcodScaner.this, getString(R.string.error_code) + error, Toast.LENGTH_LONG).show();

            }

        }) {

            protected Map<String, String> getparam() {
                Map<String, String> param = new HashMap<String, String>();
                param.put("barcode", prcode);
                return param;
            }

        };
        queue.add(postRequest);

    }

    public void send_username() {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String Url = "https://demo.midpos.com/sellApi.php";
        StringRequest senddRequest = new StringRequest(Request.Method.GET, Url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(BarcodScaner.this, nameOfUser.getText().toString() + store_nu.getText().toString(), Toast.LENGTH_LONG).show();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BarcodScaner.this, getString(R.string.error_code) + error, Toast.LENGTH_LONG).show();

            }

        }) {

            protected Map<String, String> getparam() {
                Map<String, String> param = new HashMap<String, String>();
                param.put("username", nameOfUser.getText().toString());
                param.put("store_number", store_nu.getText().toString());
                return param;
            }

        };
        queue.add(senddRequest);

    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        char pressedKey = (char) e.getUnicodeChar();
        barcode += pressedKey;
        if (e.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            Toast.makeText(getApplicationContext(), "barcode--->>>" + barcode, Toast.LENGTH_LONG)
                    .show();
            code.setText(barcode);
            test_req();
            test_get();

        }

        return super.dispatchKeyEvent(e);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                if (TextUtils.isEmpty(paid.getText())) {
                    paid.setError(getString(R.string.enter_paid));
                    Toast.makeText(BarcodScaner.this, getString(R.string.enter_paid), Toast.LENGTH_LONG).show();
                } else {

                    JSONObject my_pill = new JSONObject();
                    JSONArray l = new JSONArray();
                    id = store_nu.getText().toString();

                    if (myadaptor.getCount() == 0) {
                        Toast.makeText(BarcodScaner.this, " there is no pill ", Toast.LENGTH_LONG).show();
                    } else {
                        for (int i = 0; i < myadaptor.getCount(); i++) {
                            try {
                                my_pill.put("barcode", myadaptor.getItem(i).getProdect_barcode());
                                my_pill.put("name", myadaptor.getItem(i).getProdect_name());
                                my_pill.put("p_price", myadaptor.getItem(i).getProdect_price());
                                my_pill.put("quantity", myadaptor.getItem(i).getProdect_quntity());
                                my_pill.put("total_price", total.getText());
                                my_pill.put("username", nameOfUser.getText().toString());
                                my_pill.put("store_number", store_nu.getText().toString());
                                my_pill.put("date", currentDateandTime);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            l.put(my_pill);
                        }
                        back_cach.setText(Double.toString((Double.parseDouble(paid.getText().toString()) - Double.parseDouble(total.getText().toString()))));
                        final JSONArray pill;
                        pill = l;
                        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://demo.midpos.com/sellApi.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(BarcodScaner.this, "there is problem concetion", Toast.LENGTH_LONG).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("the_pill", pill.toString());
                                params.put("username", nameOfUser.getText().toString());
                                params.put("store_number", store_nu.getText().toString());
                                return params;
                            }
                        };
                        Volley.newRequestQueue(this).add(stringRequest);
                        Toast.makeText(this, getString(R.string.Order_Done) + pill, Toast.LENGTH_LONG).show();
                        myadaptor.clear();
                        myadaptor.notifyDataSetChanged();
                        x = 0;
                        total.setText(Double.toString(x));
                        name.setText("");
                        code.setText("");
                        quntity.setText("1");
                        price.setText("");
                        total.setText("");
                        paid.setText("");

                    }
                }
                return true;

            case R.id.item2:
                SharedPrefManager.getInstans(this).logout();
                finish();
                startActivity(new Intent(BarcodScaner.this, MainActivity.class));
                Toast.makeText(this, getString(R.string.logout_sucsses), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                showChangeLanguageDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onPermissionRequestDenied() {

    }

    @Override
    public void onRetrievedFailed(String reason) {

    }

    @Override
    public void onRetrieved(final Barcode barcode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                code.setText(barcode.displayValue);

                test_req();
                test_get();

            }
        });

    }

    @Override
    public void onRetrievedMultiple(Barcode closetToClick, List<BarcodeGraphic> barcode) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    private void showChangeLanguageDialog() {

        final String[] languages = {"English", "العربية"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("choose_language");
        mBuilder.setSingleChoiceItems(languages, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (i == 0) {

                    Intent intent = new Intent(BarcodScaner.this, SplashScreen.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    setLocale("EN", BarcodScaner.this);


                } else if (i == 1) {

                    Intent intent = new Intent(BarcodScaner.this, SplashScreen.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    setLocale("AR", BarcodScaner.this);


                }

                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();
    }

    public static void setLocale(String lang, Context context) {

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        context.getResources().updateConfiguration(configuration, context
                .getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = context.getSharedPreferences("SettingsOfLang", MODE_PRIVATE).edit();
        editor.putString("my_lang", lang);
        editor.apply();
    }

    public static void loadLocale(Context context) {

        SharedPreferences preferences = context.getSharedPreferences("SettingsOfLang", MODE_PRIVATE);
        String language = preferences.getString("my_lang", "");
        setLocale(language, context);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("d", mymy);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mymy = (ArrayList<Prodect>) savedInstanceState.getSerializable("d");
            myadaptor = new ListAdaptor(BarcodScaner.this, 0, mymy);
            list_user = findViewById(R.id.prun_list);
            list_user.setAdapter(myadaptor);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }
}