package io.github.contextawareness;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.github.contextawareness.activity.Activity;
import io.github.contextawareness.audio.Audio;
import io.github.contextawareness.audio.AudioOperators;
import io.github.contextawareness.commons.time.Time;
import io.github.contextawareness.core.Callback;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.PStream;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.exceptions.PSException;
import io.github.contextawareness.core.purposes.Purpose;
import io.github.contextawareness.device.Device;
import io.github.contextawareness.utils.Globals;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Globals.LocationConfig.useGoogleService = false;
        UQI uqi = new UQI(this);
        Boolean callback = null;

        try {
            callback = uqi.listening(Device.Battery.isLowBattery(30), Purpose.UTILITY("test"))
                    .getCallback();
        } catch (PSException e) {
            e.printStackTrace();
        }
        Log.d("Log", String.valueOf(callback));

//        uqi.listening(Device.Battery.isLowBattery(30), Purpose.UTILITY("test"))
//                .setCallback(new Callback<Boolean>() {
//                    @Override
//                    protected void onInput(Boolean input) {
//                        Log.d("Log", String.valueOf(input));
//                    }
//                });

//        uqi.listening(Device.Battery.isLowBattery(30f, 100f), Purpose.UTILITY("battery"))
//                .debug();
//        uqi.listening(Device.WiFi.isWiFiConnected(), Purpose.UTILITY("wifi"))
//                .debug();
//        uqi.listening(Device.Bluetooth.isBluetoothConnected(), Purpose.UTILITY("bluetooth"))
//                .debug();
//        uqi.listening(Device.Screen.isScreenOn(), Purpose.UTILITY("bluetooth"))
//                .debug();
//        uqi.listening(Device.Screen.isDeviceInteractive(), Purpose.UTILITY("device interactive"))
//                .debug();
//        uqi.listening(Time.Dates.isHoliday("20190618"), Purpose.UTILITY("holiday"))
//                .debug();
//        uqi.listening(Activity.Recognition(Activity.STILL), Purpose.UTILITY("activity"))
//                .debug();
//        uqi.listening(Environment.Location.LocationUpdates(), Purpose.UTILITY("location updates"))
//                .debug();
//        uqi.listening(Activity.CallFrom("Lucy"), Purpose.UTILITY("caller from"))
//                .debug();




    }
}
