package com.example.div.slider;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mPager;
    private int[] layouts = {R.layout.first_slide,R.layout.second_slide,R.layout.third_slide,R.layout.fourth_slide};
    private MpagerAdapter mpagerAdapter;
    private LinearLayout Dots_Layout;
    private ImageView[] dots;
    private Button bnnext,bnskip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if(new PrefrenceManager(this).checkPrefrence()){
            loadHome();
        }
        if(Build.VERSION.SDK_INT>=19){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        mPager = (ViewPager)findViewById(R.id.viewpager);
        mpagerAdapter=new MpagerAdapter(layouts,this);
        mPager.setAdapter(mpagerAdapter);

        //Dots_Layout = (LinearLayout)findViewById(R.id.dotslayout);
        bnnext=(AppCompatButton)findViewById(R.id.next);
        bnskip=(AppCompatButton)findViewById(R.id.skip);
        bnnext.setOnClickListener(this);
        bnskip.setOnClickListener(this);
        createDots(0);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
                if(position==layouts.length-1){
                    bnnext.setText("Start");
                    bnskip.setVisibility(View.INVISIBLE);
                }else{
                    bnnext.setText("next");
                    bnskip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void createDots(int current_position){

        if(Dots_Layout!=null){
            Dots_Layout.removeAllViews();
            dots = new ImageView[layouts.length];
            for(int i=0;i<layouts.length;i++){
                dots[i] = new ImageView(this);
                if(i==current_position){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.dots_selected));
                }else{
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.default_dots));
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(4,0,4,0);
                Dots_Layout.addView(dots[i],params);
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.skip:

                new PrefrenceManager(this).writePrefrence();
                break;
            case R.id.next:

                loadNextSlide();
                break;
        }
    }
    private void loadHome(){
        startActivity(new Intent(this,WelcomeActivity.class));
        finish();
    }

    private void loadNextSlide(){
        int next_slide = mPager.getCurrentItem()+1;

        if(next_slide<layouts.length){
            mPager.setCurrentItem(next_slide);
        }else{
            loadHome();
            new PrefrenceManager(this).writePrefrence();
        }
    }
}
