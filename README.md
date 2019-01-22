# ContextAwareness

---

ContextAwareness is a privacy-preserving context awareness Android library. It could help developers ease the programming efforts of sensing various contexts by offering transparency in detailed processing steps. Meanwhile only context related fine-grained data is returned to applications, enabling the great isolation of developers and personal data and reducing end users' privacy concerns. Until now ContextAwareness supports phone state, personal data, public knowledge and user activity four categories of contexts. Logical operations (i.e. and, or, not) are supported among contexts to build complicated situations. All of them could be monitored and handled in a uniform query interface (UQI) as shown below.

    UQI.getData(Provider, Purpose)
        .listening("context-signal", ContextAwarenessFunction)
        .setField("fine-grained_data", FieldCalculator)
        .Callback();

In ContextAwareness, a context awareness query statement is made up of servel functions. Here Provider gets raw data from data sources including sensors, databases, systems etc. and converts it to a standard-format stream. Over the stream ContextAwarenessFunction generates and handles a context, and the result of its occurrence is set to a boolean field which could be named by developers (e.g. context-signal). If developers wanna to get context related fine-grained personal data via a series of transformations, they could use FieldCalculator to calculate various fields. Finally, the context signal and related fine-grained data are collected to output with Callback function.

In ContextAwareness, all developers should do is to find out proper functions to form a context awareness query statement. If the context happens, callback data will be returned to applications for subsequent procedures.

**Quick examples**
---

**Monitor WiFi connection state.**

    /**
     * Check WiFi connection every 10 seconds and return device related connection information. 
     * Make sure the following line is added to AndroidManifest.xml
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     * <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
     * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
     */
     UQI uqi = new UQI(context);
     try {
         uqi.getData(DeviceState.asUpdates(10000, DeviceState.Masks.WIFI_AP_LIST), Purpose.UTILITY("WiFi connection"))
            .setField("wifi", DeviceOperators.isWifiConnected())
            .forEach(new Callback<Item>() {
                @Override
                protected void onInput(Item input) {
                    Log.d("Log", input.getAsBoolean("wifi").toString());
                }
            });
    } catch (Exception e) {
        e.printStackTrace();
    }
        
 **Monitor location updates.**   

    /**
     * Check location updates every 10s and return the precise latitude and longitude when the context happens. 
     * Make sure the following line is added to AndroidManifest.xml
     * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
     */
    UQI uqi = new UQI(context);
    try {
        uqi.getData(Geolocation.asUpdates(10000, Geolocation.LEVEL_EXACT), Purpose.UTILITY("Location updates"))
        .setField("location", GeolocationOperators.getLatLon())
        .forEach(new Callback<Item>() {
            @Override
            protected void onInput(Item input) {
                LatLon latLon = input.getValueByField("location");
                Log.d("Log", "Lat: "+latLon.getLatitude()+" Long: "+latLon.getLongitude());
            }
        });
    } catch (Exception e) {
        e.printStackTrace();
    }
    
**Monitor the ambient loudness level.**  

    /**
     * Compare the average loudness with a threshold.
     * Make sure the following line is added to AndroidManifest.xml
     * <uses-permission android:name="android.permission.RECORD_AUDIO" />
     */
     UQI uqi = new UQI(context);
     try {
        uqi.getData(Audio.recordPeriodic(1000, 5000), Purpose.UTILITY("Loudness level"))
            .setField("loudnessLevel", AudioOperators.LoudnessLevel(Operators.GTE, 30.0))
            .forEach(new Callback<Item>() {
                @Override
                protected void onInput(Item input) {
                    Log.d("Log", input.getAsBoolean("loudnessLevel").toString());
                }
            });
    } catch (Exception e) {
        e.printStackTrace();
    }
    
**Simultaneous events.**      
    
    /**
     * Return the average loudness in dB when the user is still.
     * Make sure the following line is added to AndroidManifest.xml
     * <uses-permission android:name="com.google.android.gms.permission.ACTIVITY_RECOGNITION" />
     * <uses-permission android:name="android.permission.RECORD_AUDIO" />
     */
    UQI uqi = new UQI(context);
    
    HashMap<String, Function> fieldMap1 = new HashMap<>();
    fieldMap1.put("field1", AudioOperators.calcAvgLoudness(Audio.AUDIO_DATA));
    EnvironmentFactor factor1 = new EnvironmentFactor(Audio.recordPeriodic(1000, 1000), fieldMap1);
    
    HashMap<String, Function> fieldMap2 = new HashMap<>();
    fieldMap2.put("field2", UserActivityInfoOperators.isStill());
    EnvironmentFactor factor2 = new EnvironmentFactor(UserActivityInfo.asUpdates(uqi, DetectedActivity.STILL, 1000), fieldMap2);
    
    LinkedList<EnvironmentFactor> factors = new LinkedList<>();
    factors.add(factor1);
    factors.add(factor2);
    
    uqi.getData(Environment.asAnd(factors), Purpose.UTILITY("Awareness"))
        .forEach(new Callback<Item>() {
            @Override
            protected void onInput(Item input) {
                Log.d("Log", input.getAsDouble("field1").toString());
            }
        });

    
**Installation**
---    

**Option 1. Using Gradle (recommended for the most users)**

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. Add the dependency
    
    dependencies {
	   implementation 'com.github.xinyu1118:ContextAwareness:1.0.0'
	}

That's it!

Note that if you want to **use Google location service** instead of the Android location service, you will need one more step. (in China google location service is disabled!)

The Location APIs in ContextAwareness are based on Google location service. In order to access location with ContextAwareness, you will need to install Google location service. 

Specifically, add the following line to `build.gradle` file under tha app module.

    dependencies {
        ...
        implementation 'com.google.android.gms:play-services-location:16.0.0'
        ...
    }
    
Then in your app code, enable Google location service:

    Globals.LocationConfig.useGoogleService = true;
    

**Option 2. From source (recommended for contributors)**

In Android Studio, the installation involves the following steps:

1 Clone the project to your computer.

2 Open your own project, import contextawareness-android-sdk module.

2.1）Click File -> New -> Import module     
2.2）Select `contextawareness-android-sdk` directory as the source directory

3 In your app module, add the following line to dependencies:
`implementation project(':contextawareness-android-sdk')`

**Documentation**
---  
[LINK][1] (still evolving, initial name PrivacyStreamsEvents)

**Other Information**
---
ContextAwareness is initially developed by Xinyu Yang, under the advisory of Jason Hong and Yuvraj Agarwal, depending on CHIMPS Lab at Carnegie Mellon University and National Engineering Lab for Mobile Network Technologies at Beijing University of Posts and Telecommunications.

Contact Author (yangxycl@163.com)


  [1]: https://docs.google.com/document/d/13eyGI1-gqV4eXm467RjF6H9XuQYtx-tYG1vwPmzaT04/edit#heading=h.8bthu2z2dnv8




