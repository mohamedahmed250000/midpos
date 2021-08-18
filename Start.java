package com.example.midpos;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Start extends AppCompatActivity {
    Button sell, home, go_out;
    MediaPlayer player;
    TextView nameOfUser, store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        sell = findViewById(R.id.sell_button);
        home = findViewById(R.id.main_button);
        go_out = findViewById(R.id.go_out_button);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        nameOfUser = findViewById(R.id.nameOfUser1);
        store = findViewById(R.id.numberOfStore);
        nameOfUser.setText(SharedPrefManager.getInstans(this).getUsername());

        SharedPreferences test_name = getSharedPreferences("NAME", 0);
        store.setText(test_name.getString("name", ""));

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player = MediaPlayer.create(Start.this, R.raw.tinybuttonpush);
                player.start();
                Intent CCC = new Intent(Start.this, BarcodScaner.class);
                startActivity(CCC);

            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player = MediaPlayer.create(Start.this, R.raw.tinybuttonpush);
                player.start();
                startActivity(new Intent(Start.this, Home.class));

            }
        });
        go_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player = MediaPlayer.create(Start.this, R.raw.tinybuttonpush);
                player.start();
                SharedPrefManager.getInstans(Start.this).logout();
                finish();
                startActivity(new Intent(Start.this, MainActivity.class));
                Toast.makeText(Start.this, getString(R.string.logout_sucsses), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
