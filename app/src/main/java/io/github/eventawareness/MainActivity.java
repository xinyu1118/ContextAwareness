package io.github.eventawareness;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.DetectedActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.github.eventawareness.core.Callback;
import io.github.eventawareness.core.ComplexContexts;
import io.github.eventawareness.core.Contexts;

import io.github.eventawareness.core.EnvironmentVariable.EnvironmentVariable;
import io.github.eventawareness.core.Item;

import io.github.eventawareness.core.Operators;
import io.github.eventawareness.core.PersonalData.PersonalData;
import io.github.eventawareness.core.PhoneState.PhoneState;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.core.UserActivity.UserActivity;
import io.github.eventawareness.core.purposes.Purpose;
import io.github.eventawareness.utils.Globals;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Globals.LocationConfig.useGoogleService = true;

        final double latitude = 40.0;
        final double longitude = -80.0;
        final double radius = 20.0;


//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
//        Date curDate =  new Date(System.currentTimeMillis());
//        String str = formatter.format(curDate);


        UQI uqi = new UQI(this);
//        List<String> queryList = new ArrayList<String>();
//        queryList.add("yangxycl@163.com");
//        queryList.add("15555215556");
//
//        Date date = null;
//        try {
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//
//            date = format.parse("2018-12-20");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        uqi.addContexts(EnvironmentVariable.TemperatureLevel(Operators.LTE, 0), Purpose.UTILITY("test"))
                .listening(Contexts.AlwaysRepeat, 2000)
                .onEvent(new Callback<Item>() {
                    @Override
                    protected void onInput(Item input) {
                        Log.d("Log", "onEvent");
                    }
                });

//        uqi.addContexts(UserActivity.ActivityRecognition(DetectedActivity.STILL), Purpose.UTILITY("test"))
//                .listening(Contexts.AlwaysRepeat, 1000)
//                .forEach(new Callback<Item>() {
//                    @Override
//                    protected void onInput(Item input) {
//                        Log.d("Log", input.getAsBoolean("contexts_signal").toString());
//                    }
//                });

//        uqi.addContexts(PersonalData.FileInserted("/storage/emulated/0/DCIM/Camera/"), Purpose.UTILITY("test"))
//                .listening(3, Contexts.PassiveListening)
//                .forEach(new Callback<Item>() {
//                    @Override
//                    protected void onInput(Item input) {
//                        Log.d("Log", input.getAsBoolean("contexts_signal").toString());
//                    }
//                });


//                .onChange("contexts_signal", new Callback<Boolean>() {
//                    @Override
//                    protected void onInput(Boolean input) {
//                        Log.d("Log", String.valueOf(input));
//                    }
//                });

//                .forEach("contexts_signal", new Callback<Boolean>() {
//                    @Override
//                    protected void onInput(Boolean input) {
//                        Log.d("Log", String.valueOf(input));
//                    }
//                });








    }
}
