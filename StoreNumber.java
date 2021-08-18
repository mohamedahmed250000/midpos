package com.example.midpos;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StoreNumber extends AppCompatActivity {
    EditText e;
    Button b;
    FloatingActionButton g;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_number);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        e = findViewById(R.id.enter_id);
        b = findViewById(R.id.gogogogog);
        g = findViewById(R.id.fab22);

        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player = MediaPlayer.create(StoreNumber.this, R.raw.tinybuttonpush);
                player.start();
                SharedPrefManager.getInstans(StoreNumber.this).logout();
                finish();
                startActivity(new Intent(StoreNumber.this, MainActivity.class));
                Toast.makeText(StoreNumber.this, getString(R.string.logout_sucsses), Toast.LENGTH_SHORT).show();
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreId.setStore(e.getText().toString());
                if (TextUtils.isEmpty(e.getText())) {
                    e.setError("enter the id");
                    Toast.makeText(StoreNumber.this, getString(R.string.enter_id), Toast.LENGTH_LONG).show();
                    return;
                }
                if (Integer.parseInt(e.getText().toString()) != 1 && Integer.parseInt(e.getText().toString()) != 0) {
                    e.setError("enter corect store id ");
                    Toast.makeText(StoreNumber.this, getString(R.string.enter_corect_store_id), Toast.LENGTH_LONG).show();
                    return;
                }
                SharedPreferences test_name = getSharedPreferences("NAME", 0);
                SharedPreferences.Editor editor = test_name.edit();
                editor.putString("name", e.getText().toString());
                editor.commit();
                player = MediaPlayer.create(StoreNumber.this, R.raw.tinybuttonpush);
                player.start();
                Intent i = new Intent(StoreNumber.this, Start.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
