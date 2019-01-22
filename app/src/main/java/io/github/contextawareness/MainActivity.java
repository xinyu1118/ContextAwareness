package io.github.contextawareness;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.github.contextawareness.audio.Audio;
import io.github.contextawareness.audio.AudioOperators;
import io.github.contextawareness.core.Callback;
import io.github.contextawareness.core.ComplexContexts;
import io.github.contextawareness.core.Contexts;

import io.github.contextawareness.core.EnvironmentVariable.EnvironmentVariable;
import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;

import io.github.contextawareness.core.Operators;
import io.github.contextawareness.core.PStream;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.core.PersonalData.PersonalData;
import io.github.contextawareness.core.PhoneState.PhoneState;
import io.github.contextawareness.core.ProviderAnd_Or.Environment;
import io.github.contextawareness.core.ProviderAnd_Or.EnvironmentFactor;
import io.github.contextawareness.core.ProviderAnd_Or.EnvironmentOperators;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.UserActivity.UserActivity;
import io.github.contextawareness.core.UserActivity.UserActivityInfo;
import io.github.contextawareness.core.UserActivity.UserActivityInfoOperators;
import io.github.contextawareness.core.exceptions.PSException;
import io.github.contextawareness.core.purposes.Purpose;
import io.github.contextawareness.device.DeviceOperators;
import io.github.contextawareness.device.DeviceState;
import io.github.contextawareness.location.Geolocation;
import io.github.contextawareness.location.GeolocationOperators;
import io.github.contextawareness.utils.Globals;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Globals.LocationConfig.useGoogleService = false;

        final double latitude = 40.0;
        final double longitude = -80.0;
        final double radius = 20.0;


//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
//        Date curDate =  new Date(System.currentTimeMillis());
//        String str = formatter.format(curDate);


        UQI uqi = new UQI(this);

        // 音频 loudness 可用
//        try {
//            uqi.getData(Audio.recordPeriodic(1000, 1000), Purpose.UTILITY("Get loudness"))
//                    .setField("loudness", AudioOperators.calcAvgLoudness(Audio.AUDIO_DATA))
//                    .forEach(new Callback<Item>() {
//                        @Override
//                        protected void onInput(Item input) {
//                            Log.d("Log", input.getAsDouble("loudness").toString());
//                        }
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // WIFI 流程可用，但权限申请报错 logcat E/wifi: enabled
//        try {
//            uqi.getData(DeviceState.asUpdates(1000, DeviceState.Masks.WIFI_AP_LIST), Purpose.UTILITY("WIFI"))
//                    .setField("wifi", DeviceOperators.isWifiConnected())
//                    .forEach(new Callback<Item>() {
//                        @Override
//                        protected void onInput(Item input) {
//                            Log.d("Log", input.getAsBoolean("wifi").toString());
//                        }
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // 中国地区 google 服务不好使：Globals.LocationConfig.useGoogleService = false;
//        try {
//            uqi.getData(Geolocation.asUpdates(1000, Geolocation.LEVEL_EXACT), Purpose.UTILITY("Get location"))
//                    .setField("location", GeolocationOperators.getLatLon())
//                    .forEach(new Callback<Item>() {
//                        @Override
//                        protected void onInput(Item input) {
//                            Log.d("Logffff", "item:"+input.toString());
//                        }
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // 用户感知 UserActivityInfo
        try {
            uqi.getData(UserActivityInfo.asUpdates(uqi, DetectedActivity.STILL, 1000), Purpose.UTILITY("Awareness"))
                    .setField("Awareness", UserActivityInfoOperators.isStill())
                    .forEach(new Callback<Item>() {
                        @Override
                        protected void onInput(Item input) {
                            Log.d("Logd", input.getAsBoolean("Awareness").toString());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


//        联立事件
//        HashMap<String, Function> fieldMap1 = new HashMap<>();
//        fieldMap1.put("field1.1", AudioOperators.calcAvgLoudness(Audio.AUDIO_DATA));
//
//        EnvironmentFactor factor1 = new EnvironmentFactor(Audio.recordPeriodic(1000, 1000), fieldMap1);
//
//        HashMap<String, Function> fieldMap2 = new HashMap<>();
//        fieldMap2.put("field2.1", UserActivityInfoOperators.isStill());
//        EnvironmentFactor factor2 = new EnvironmentFactor(UserActivityInfo.asUpdates(uqi, DetectedActivity.STILL, 1000), fieldMap2);
//
//        LinkedList<EnvironmentFactor> factors = new LinkedList<>();
//        factors.add(factor1);
//        factors.add(factor2);
//
//        uqi.getData(Environment.asAnd(factors), Purpose.UTILITY("Awareness"))
//                .forEach(new Callback<Item>() {
//                    @Override
//                    protected void onInput(Item input) {
//                        Log.d("Log", input.getAsDouble("field1.1").toString());
//
//                    }
//                });

//        uqi.addContexts(UserActivity.ActivityRecognition(DetectedActivity.STILL), Purpose.UTILITY("test"))
//                .listening(Contexts.AlwaysRepeat, 1000)
//                .forEach(new Callback<Item>() {
//                    @Override
//                    protected void onInput(Item input) {
//                        Log.d("Log", input.getAsBoolean("contexts_signal").toString());
//                    }
//                });

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
//        uqi.addContexts(EnvironmentVariable.TemperatureLevel(Operators.LTE, 0), Purpose.UTILITY("test"))
//                .listening(Contexts.AlwaysRepeat, 2000)
//                .onEvent(new Callback<Item>() {
//                    @Override
//                    protected void onInput(Item input) {
//                        Log.d("Log", "onEvent");
//                    }
//                });

//        uqi.addContexts(UserActivity.ActivityRecognition(DetectedActivity.STILL), Purpose.UTILITY("test"))
//                .listening(Contexts.AlwaysRepeat, 1000)
//                .forEach(new Callback<Item>() {
//                    @Override
//                    protected void onInput(Item input) {
//                        Log.d("Logfff", input.getAsBoolean("contexts_signal").toString());
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
