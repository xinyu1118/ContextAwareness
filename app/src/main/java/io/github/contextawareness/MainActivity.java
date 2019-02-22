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

        // Examples of personal data
        PersonalDataExamples personalDataExamples = new PersonalDataExamples(this);
//        personalDataExamples.LoudnessLevel();
//        personalDataExamples.LocationUpdates();
//        personalDataExamples.LocationInCircle();
//        personalDataExamples.LocationInSquare();
//        personalDataExamples.SpeedMonitor();
//        personalDataExamples.DestArrival();
//        personalDataExamples.PostcodeUpdates();
//        personalDataExamples.CityUpdates();
//        personalDataExamples.DirectionUpdates();
//        personalDataExamples.CallerFrom();
//        personalDataExamples.CallerInList();
//        personalDataExamples.EmailsChecker();
//        personalDataExamples.MessageSenderFrom();
//        personalDataExamples.MessageSenderInList();
//        personalDataExamples.ImageHasFace();

        // Examples of phone state
//        PhoneStateExamples phoneStateExamples = new PhoneStateExamples(this);
//        phoneStateExamples.WiFiConnection();

        // Examples of user activity
//        UserActivityExamples userActivityExamples = new UserActivityExamples(this);
//        userActivityExamples.IsStill();

        // Example of simultaneous events
//        SimultaneousExamples simultaneousExamples = new SimultaneousExamples(this);
//        simultaneousExamples.LoudnessOnStill();


    }
}
