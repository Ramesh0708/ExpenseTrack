package com.example.expensetrack;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {


    TextView text,text1,text2,text3,text4,text5,text6,text7,text8;
    ImageView image,image1,image2,image3,image4,image5,image6,image7,image8;
    Animation fromBottom,fromTop,fromright,fromleft,rotate,scale;

    private  static int    Splash_Time_out = 5000;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        image = (ImageView) findViewById(R.id.expenselogo);
        image1 = (ImageView) findViewById(R.id.shop);
        image2 = (ImageView) findViewById(R.id.fashion);
        image3 = (ImageView) findViewById(R.id.hospital);
        image4 = (ImageView) findViewById(R.id.phone);
        image5 = (ImageView) findViewById(R.id.beauty);
        image6 = (ImageView) findViewById(R.id.offer);
        image7 = (ImageView) findViewById(R.id.flight);
        image8 = (ImageView) findViewById(R.id.furniture);

        text = (TextView) findViewById(R.id.expensename);
        text1 = (TextView) findViewById(R.id.shopname);
        text2 = (TextView) findViewById(R.id.fashionname);
        text3 = (TextView) findViewById(R.id.hospitalname);
        text4 = (TextView) findViewById(R.id.phonename);
        text5 = (TextView) findViewById(R.id.beautyname);
        text6 = (TextView) findViewById(R.id.offername);
        text7 = (TextView) findViewById(R.id.flightname);
        text8 = (TextView) findViewById(R.id.furniturename);


        fromBottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromTop = AnimationUtils.loadAnimation(this,R.anim.fromtop);
        fromleft= AnimationUtils.loadAnimation(this,R.anim.fromleft);
        fromright = AnimationUtils.loadAnimation(this,R.anim.fromright);
        rotate = AnimationUtils.loadAnimation(this,R.anim.rotate);
        scale = AnimationUtils.loadAnimation(this,R.anim.scale);

          image.setAnimation(rotate);
        image1.setAnimation(fromTop);
        image2.setAnimation(fromTop);
        image3.setAnimation(fromTop);
        image4.setAnimation(fromTop);
        image5.setAnimation(fromTop);
        image6.setAnimation(fromTop);
        image7.setAnimation(fromTop);
        image8.setAnimation(fromTop);


        text.setAnimation(scale);
        text1.setAnimation(fromTop);
        text2.setAnimation(fromTop);
        text3.setAnimation(fromTop);
        text4.setAnimation(fromTop);
        text5.setAnimation(fromTop);
        text6.setAnimation(fromTop);
        text7.setAnimation(fromTop);
        text8.setAnimation(fromTop);









        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },Splash_Time_out);

    }


}
