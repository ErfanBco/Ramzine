package shady.bco.jadval;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Typeface typefaceComicSan=Typeface.createFromAsset(getAssets(), "fonts/comic.ttf");
        Typeface typefaceTitr=Typeface.createFromAsset(getAssets(), "fonts/IRTitr.ttf");

       final ImageView imageViewShady=findViewById(R.id.imageViewShadyEntertanment);
        ImageView imageViewProgrammer=findViewById(R.id.imageViewErfanGhaderi);
        ImageView imageViewInsta=findViewById(R.id.imageViewSocialMedia_Insta);
        TextView textViewVersion=findViewById(R.id.textViewVersion);
        TextView textViewLiteV = findViewById(R.id.textViewLiteV);




        Display DisplaySize=getWindowManager().getDefaultDisplay();
        final Point Size=new Point();
        DisplaySize.getSize(Size);
        int DisplayWidth=Size.x;
        int DisplayHeight=Size.y;


        final View decorView=getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        textViewVersion.setText("V"+BuildConfig.VERSION_NAME);
        textViewVersion.setTypeface(typefaceComicSan);

        imageViewShady.getLayoutParams().height=DisplayHeight/3;
        imageViewProgrammer.getLayoutParams().width=(DisplayWidth/4);
        imageViewProgrammer.getLayoutParams().height=(DisplayHeight/35);
        imageViewInsta.getLayoutParams().width=(imageViewShady.getLayoutParams().width)/4;
        imageViewInsta.getLayoutParams().height=(imageViewShady.getLayoutParams().width);
        imageViewProgrammer.setAdjustViewBounds(true);
        imageViewShady.setAdjustViewBounds(true);
        imageViewInsta.setAdjustViewBounds(true);

        if (Build.VERSION.SDK_INT<=18) {
            textViewLiteV.setVisibility(View.VISIBLE);
            textViewLiteV.setTextSize(0, ((DisplayHeight / DisplayWidth) * 30));
            textViewLiteV.setTypeface(typefaceTitr);
        }

        final AlphaAnimation alphaAnimation=new AlphaAnimation(0F,1F);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setFillAfter(true);
        imageViewShady.startAnimation(alphaAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final AlphaAnimation alphaAnimation2=new AlphaAnimation(1F,0F);
                alphaAnimation2.setDuration(800);
                alphaAnimation2.setFillAfter(true);
                imageViewShady.startAnimation(alphaAnimation2);
            }
        },6000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent StartApp = new Intent(SplashScreen.this, LevelActivity.class);
                startActivity(StartApp);

            }
        },7000);
    }
}
