# PCAF

---

Nowadays mobile applications can provide customized services by inferring users' contexts with a wide range of personal data, such as GPS, microphones, accelerometers etc. There are two challenges for context listening and handling:

- [ ] It is **difficult and error-prone for app developers** to handle a variety of contexts easily and efficiently with existing native system APIs.
- [ ] End users are unwilling to expose all their raw data for context inferrence to developers as they hardly know what developers do with their data, which can lead to **privacy concerns**. 

To address the above challenges, we propose a privacy-preserving mobile app context-aware API named PCAF. It can provide programming support for context-aware app development while limiting the access amount of personal data as much as possible. Raw data is encapsulated and only a set of coarse-grained data is provided for context inference, rather than raw data at any granularity at any time. At the same time PCAF frees app developers from a large amount of processing details. 

The core idea of PCAF is to centralize context listening and handling in a unified query interface (UQI) as follows. All developers should do is to find out proper coarse-grained data and built-in functions to specify a query statement:

    UQI.listening(Contexts, Purpose)
        .setCallback(CallbackFunction)

Here *Contexts* is used to specify a context that needs to be listened to and handled. App developers should first follow our personal data tree until arriving at the required coarse-grained data in a context. Then they should find out a proper built-in function related to the coarse-grained data to form a context. The personal data tree is refined layer by layer according to the data granularity. It is shown in the following figure, and we only show two-tier structure due to length limitation. 



For example, if an app developer wants to check whether 'the battery level is lower than 15%', then s/he should find the coarse-grained data 'battery level' following the path 'Device->Battery', and then use the built-in function 'isLowBattery(15f)' to implement the context generation. A set of enumeration values of context occurrence states are returned with *setCallback*. The most typical callback is a Boolean value indicating whether the context happens or not. In addition, *Purpose* is provided for developers to explain why they monitor a context for end users and auditors. 

Next we will give some quick examples. 

**Quick examples**
---

**Check whether the WiFi is connected.**

    /**
     * Make sure the following line is added to AndroidManifest.xml
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     */
     UQI uqi = new UQI (context); // omitted below
     uqi.listening(Device.WiFi.isWiFiConnected(), Purpose.UTILITY("check wifi connection status"))
        .setCallback(new Callback<Boolean>() {
            @Override
            protected void onInput(Boolean input) {
                Log.d("Log", String.valueOf(input));
            }
        });
        
**Check whether the battery is lower than 30%.**


     UQI.listening(Device.Battery.isLowBattery(30f), Purpose.TEST("test"))
        .setCallback(new Callback<Boolean>() {
            @Override
            protected void onInput(Boolean input) {
                Log.d("Log", String.valueOf(input));
            }
        });
        
 **Monitor location updates.**   

    /**
     * Make sure the following line is added to AndroidManifest.xml
     * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
     */
     UQI.listening(Environment.Location.LocationUpdates(), Purpose.UTILITY("monitor location updates"))
        .setCallback(new Callback<Boolean>() {
            @Override
            protected void onInput(Boolean input) {
                Log.d("Log", String.valueOf(input));
            }
        });
    
**Listen to phone calls from Lucy.**  

    /**
     * Make sure the following line is added to AndroidManifest.xml
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     * uses-permission android:name="android.permission.READ_CONTACTS" />
     */
    UQI.listening(Activity.CallFrom("Lucy"), Purpose.UTILITY("caller from"))
        .setCallback(new Callback<Boolean>() {
            @Override
            protected void onInput(Boolean input) {
                Log.d("Log", String.valueOf(input));
            }
        });
    

    
**Installation**
---    

**Option 1. Using Gradle (recommended for the most users)**

Step 1. Add the JitPack repository to your build file. 
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
PCAF is initially developed by Xinyu Yang, under the advisory of Jason Hong and Yuvraj Agarwal, depending on CHIMPS Lab at Carnegie Mellon University and National Engineering Lab for Mobile Network Technologies at Beijing University of Posts and Telecommunications.

Contact Author (yangxycl@163.com)


  [1]: https://docs.google.com/document/d/13eyGI1-gqV4eXm467RjF6H9XuQYtx-tYG1vwPmzaT04/edit#heading=h.8bthu2z2dnv8









