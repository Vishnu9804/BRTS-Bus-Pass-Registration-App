package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class walkthrough1 extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotsLayout;
    Slider_Adapter sliderAdapter;
    TextView[] dots;
    Button letsGetStarted;
    Animation animation;
    int currentPos;

//    ImageView img;

//    @SuppressLint("MissingInflatedId")
//    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_walkthrough1);

        viewPager= findViewById(R.id.slider);
        dotsLayout= findViewById(R.id.dots);
        letsGetStarted= findViewById(R.id.get_stared_btn);
//        img = findViewById(R.id.slider_image);

        sliderAdapter= new Slider_Adapter(this);
        viewPager.setAdapter(sliderAdapter);
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);

        letsGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("first_time",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("flag",false);
                editor.apply();
                Intent intent = new Intent(walkthrough1.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void next(View view){
        viewPager.setCurrentItem(currentPos+1);
    }

    private void addDots(int position) {
        dots = new TextView[3];
        dotsLayout.removeAllViews();
        for (int i = 0; i<dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dotsLayout.addView(dots[i]);
        }
        if (dots.length>0) {
            dots[position].setTextColor(getResources().getColor(R.color.black));
        }


    }

    ViewPager.OnPageChangeListener changeListener= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDots(position);
            currentPos= position;
            if (position == 0){
                letsGetStarted.setVisibility(View.INVISIBLE);
//                Animation anim= AnimationUtils.loadAnimation(walkthrough1.this,R.anim.translate);
//                img.startAnimation(anim);
            } else if (position == 1){
                letsGetStarted.setVisibility(View.INVISIBLE);
            } else {
                animation = AnimationUtils.loadAnimation(walkthrough1.this,R.anim.bottom_animation);
                letsGetStarted.setAnimation(animation);
                letsGetStarted.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }


    };
}