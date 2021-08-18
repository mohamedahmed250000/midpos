package com.example.midpos;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import static java.lang.Thread.sleep;

public class SplashScreen extends Activity {
    private ImageView Logo ,bar;
    private Animation animationImage, animationProgressBar;
    @Override
    protected  void  onCreate (Bundle savedInstanceSate) {

        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_splash_screen);



        Logo = findViewById(R.id.logo);

        //  bar = findViewById(R.id.progress_bar);
        animationImage = AnimationUtils.loadAnimation(this, R.anim.splash_screen_image);
        //   animationProgressBar = AnimationUtils.loadAnimation(this, R.anim.splash_screen_progress_bar);

        Logo.setAnimation(animationImage);
        // bar.setAnimation(animationProgressBar);

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}



