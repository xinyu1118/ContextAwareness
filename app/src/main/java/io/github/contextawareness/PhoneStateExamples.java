package io.github.contextawareness;

import android.content.Context;
import android.util.Log;

import io.github.contextawareness.core.Callback;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.purposes.Purpose;
import io.github.contextawareness.device.DeviceOperators;
import io.github.contextawareness.device.DeviceState;

public class PhoneStateExamples {
    private UQI uqi;

    public PhoneStateExamples(Context context) {
        this.uqi = new UQI(context);
    }

    // Check the wifi connection status
    public void WiFiConnection() {
        try {
            uqi.getData(DeviceState.asUpdates(1000, DeviceState.Masks.WIFI_AP_LIST), Purpose.UTILITY("WiFi connection"))
                    .listening("wifiConnected", DeviceOperators.isWifiConnected())
                    .forEach(new Callback<Item>() {
                        @Override
                        protected void onInput(Item input) {
                            Log.d("Log", input.getAsBoolean("wifiConnected").toString());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
