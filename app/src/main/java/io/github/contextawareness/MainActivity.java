package io.github.contextawareness;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.github.contextawareness.utils.Globals;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Globals.LocationConfig.useGoogleService = false;


//        PersonalDataExamples personalDataExamples = new PersonalDataExamples(this);
//        personalDataExamples.LoudnessLevel();
//        personalDataExamples.LocationUpdates();


//        PhoneStateExamples phoneStateExamples = new PhoneStateExamples(this);
//        phoneStateExamples.WiFiConnection();

//        UserActivityExamples userActivityExamples = new UserActivityExamples(this);
//        userActivityExamples.IsStill();

//        SimultaneousExamples simultaneousExamples = new SimultaneousExamples(this);
//        simultaneousExamples.LoudnessOnStill();



    }
}
