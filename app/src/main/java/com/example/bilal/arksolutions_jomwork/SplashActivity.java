package com.example.bilal.arksolutions_jomwork;

/**
 * Created by Bilal on 7/26/2017.
 */

        import android.app.Activity;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.database.Cursor;
        import android.graphics.Typeface;
        import android.os.Bundle;
        import android.os.Handler;

        import android.support.v7.app.AlertDialog;
        import android.text.Html;
        import android.text.method.LinkMovementMethod;
        import android.util.DisplayMetrics;
        import android.view.Window;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

        import java.io.IOException;

public class SplashActivity extends Activity {
    TextView splas, textsplash;
    RelativeLayout rellay;
    public static Activity activity = null;
    Handler hand = new Handler();
    Runnable r;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics dm = new DisplayMetrics();
        context=this;
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_splash);




        r = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,
                        Login.class));

              /*  if (!((GlobalClass) getApplication()).isPurchase) {
                    InterstitialAdSingleton mInterstitialAdSingleton = InterstitialAdSingleton
                            .getInstance(context);
                    mInterstitialAdSingleton.showInterstitial();

                }*/
                finish();

            }


        };

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub

        super.onPause();
        hand.removeCallbacks(r);
        //finish();
    }


    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onResume() {

        super.onResume();

            hand.postDelayed(r, 3000);





    }



    // //////////////////////// Async Function to execute long process ////////////////

/*
	private void startAsyncTask() {

		Intent intent = new Intent(SplashActivity.this, MyService.class);
		startService(intent);
	}
*/


}
