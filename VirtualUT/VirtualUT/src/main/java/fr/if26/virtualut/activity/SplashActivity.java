package fr.if26.virtualut.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import fr.if26.virtualut.R;

/**
 * Created by Thanh-Tuan on 10/01/14.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        /****** Create Thread that will sleep for 5 seconds *************/
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(5*1000);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),WelcomeActivity.class);
                    startActivity(i);

                    //Remove activity
                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }


}
