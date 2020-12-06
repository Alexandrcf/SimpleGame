package com.bignerdranch.android.simplegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.facebook.FacebookSdk;
import com.facebook.applinks.AppLinkData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("test")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int selectedActivity = preferences.getInt("Activity", 0);

        if(selectedActivity != 0)
        {
            Intent intent = null;
            switch (selectedActivity){
                case (1):
                    intent = new Intent(getApplicationContext(), GameActivity.class);
                    break;
                case (2):
                    intent = new Intent(getApplicationContext(), WebActivity.class);

            }
            startActivity(intent);
        }

        FacebookSdk.setAutoInitEnabled(true);
        FacebookSdk.fullyInitialize();

        AppLinkData.fetchDeferredAppLinkData(this,
                new AppLinkData.CompletionHandler() {
                    @Override
                    public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
                        Intent intent = null;
                        if (appLinkData != null) {
                            intent = new Intent(getApplicationContext(), WebActivity.class);
                        }
                        else {
                            intent = new Intent(getApplicationContext(), GameActivity.class);
                        }
                        startActivity(intent);
                    }
                }
        );
    }
}
