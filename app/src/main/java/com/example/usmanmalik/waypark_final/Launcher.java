package com.example.usmanmalik.waypark_final;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Launcher extends Activity {
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        imageView=findViewById(R.id.imageView);
        textView=findViewById(R.id.textView);
        Animation animation=AnimationUtils.loadAnimation(this,R.anim.mytransition);
        imageView.startAnimation(animation);
        textView.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), Navigation_view.class));
            }
        }, 1000);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(this,Navigation_view.class));

    }
}
