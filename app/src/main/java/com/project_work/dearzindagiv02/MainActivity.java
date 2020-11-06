package com.project_work.dearzindagiv02;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView app_name,app_slogan;
    ImageView app_logo;
    public static int SPLASH_SCREEN=5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app_name=findViewById(R.id.app_name);
        app_slogan=findViewById(R.id.app_slogan);
        app_logo=findViewById(R.id.app_logo);

        app_logo.setAnimation(AnimationUtils.loadAnimation(this,R.anim.fadein_top));
        app_name.setAnimation(AnimationUtils.loadAnimation(this,R.anim.fadein_bottom));
        app_slogan.setAnimation(AnimationUtils.loadAnimation(this,R.anim.fadein_bottom));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}