package io.github.contextawareness.device;


import io.github.contextawareness.core.Contexts;

public class Device {

    public static class Battery {

        public static Contexts isLowBattery(float threshold) {
            return new BatteryContexts(threshold);
        }

        public static Contexts isLowBattery(float lowerBound, float upperBound) {
            return new BatteryContexts(lowerBound, upperBound);
        }

    }

    public static class WiFi {

        public static Contexts isWiFiConnected() {
            return new WiFiContexts();
        }

    }

    public static class Bluetooth {

        public static Contexts isBluetoothConnected() {
            return new BluetoothContexts();
        }

    }

    public static class Screen {

        public static Contexts isScreenOn() {
            return new ScreenContexts();
        }

        public static Contexts isDeviceInteractive() {
            return new DeviceActiveContexts();
        }

    }

}
