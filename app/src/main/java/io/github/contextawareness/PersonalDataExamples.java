package io.github.contextawareness;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.github.contextawareness.audio.Audio;
import io.github.contextawareness.audio.AudioOperators;
import io.github.contextawareness.communication.Call;
import io.github.contextawareness.communication.CallOperators;
import io.github.contextawareness.communication.Contact;
import io.github.contextawareness.communication.ContactOperators;
import io.github.contextawareness.communication.Message;
import io.github.contextawareness.communication.MessageOperators;
import io.github.contextawareness.core.Callback;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.Operators;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.purposes.Purpose;
import io.github.contextawareness.image.Image;
import io.github.contextawareness.image.ImageOperators;
import io.github.contextawareness.location.Geolocation;
import io.github.contextawareness.location.GeolocationOperators;
import io.github.contextawareness.location.LatLon;

public class PersonalDataExamples {
    private UQI uqi;

    public PersonalDataExamples(Context context) {
        this.uqi = new UQI(context);
    }

    // Get the average loudness when it is greater than or equal to 30dB.
    public void LoudnessLevel() {
        try {
            uqi.getData(Audio.recordPeriodic(1000, 3000), Purpose.UTILITY("Get loudness"))
                    .listening("contextSignal", AudioOperators.loudnessLevel(Operators.GTE, 30.0))
                    .setField("loudness", AudioOperators.calcAvgLoudness(Audio.AUDIO_DATA))
                    .forEach(new Callback<Item>() {
                        @Override
                        protected void onInput(Item input) {
                            if (input.getAsBoolean("contextSignal"))
                                Log.d("Log", "Loudness: "+String.valueOf(input.getAsDouble("loudness")));
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get the precise latitude and longitude when location is updated.
    // In China google services are not available, make sure 'Globals.LocationConfig.useGoogleService = false';
    public void LocationUpdates() {
        try {
            uqi.getData(Geolocation.asUpdates(1000, Geolocation.LEVEL_EXACT), Purpose.UTILITY("Get location"))
                    .setField("location", GeolocationOperators.getLatLon())
                    .forEach(new Callback<Item>() {
                        @Override
                        protected void onInput(Item input) {
                            LatLon latLon = input.getValueByField("location");
                            Log.d("Log", "location: "+String.valueOf(latLon.getLatitude())+", "+String.valueOf(latLon.getLongitude()));
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Check the location in a circle specified by center latitude, longitude, and radius.
    public void LocationInCircle() {
        uqi.getData(Geolocation.asUpdates(1000, Geolocation.LEVEL_EXACT), Purpose.UTILITY("In circle"))
                .listening("inCircle", GeolocationOperators.inCircle(Geolocation.LAT_LON, 20.0, -40.0, 100))
                .forEach(new Callback<Item>() {
                    @Override
                    protected void onInput(Item input) {
                        Log.d("Log", "In Circle: "+String.valueOf(input.getAsBoolean("inCircle")));
                    }
                });
    }

    // Check the location in a square specified by minimum latitude and longitude, maximum latitude and longitude.
    public void LocationInSquare() {
        uqi.getData(Geolocation.asUpdates(1000, Geolocation.LEVEL_EXACT), Purpose.UTILITY("In square"))
                .listening("inSquare", GeolocationOperators.inSquare(Geolocation.LAT_LON, 20.0, -40.0, 30.0, -60.0))
                .forEach(new Callback<Item>() {
                    @Override
                    protected void onInput(Item input) {
                        Log.d("Log", "In Square: "+String.valueOf(input.getAsBoolean("inSquare")));
                    }
                });
    }

    // Monitor speed with a threshold in m/s.
    public void SpeedMonitor() {
        uqi.getData(Geolocation.asUpdates(1000, Geolocation.LEVEL_EXACT), Purpose.UTILITY("Speed monitor"))
                .listening("speedMonitor", GeolocationOperators.SpeedMonitor(Operators.GTE, 30.0f))
                .forEach(new Callback<Item>() {
                    @Override
                    protected void onInput(Item input) {
                        Log.d("Log", "Over speed? "+String.valueOf(input.getAsBoolean("speedMonitor")));
                    }
                });
    }

    // Check whether the user has arrived the destination.
    public void DestArrival() {
        final LatLon destination = new LatLon(40.0, -120.0);
        uqi.getData(Geolocation.asUpdates(1000, Geolocation.LEVEL_EXACT), Purpose.UTILITY("Destination arrival"))
                .listening("destArrival", GeolocationOperators.DestArrival(Geolocation.LAT_LON, destination))
                .forEach(new Callback<Item>() {
                    @Override
                    protected void onInput(Item input) {
                        Log.d("Log", "Destination arrived? "+String.valueOf(input.getAsBoolean("destArrival")));
                    }
                });
    }

    // Postcode updated.
    public void PostcodeUpdates() {
        uqi.getData(Geolocation.asUpdates(1000, Geolocation.LEVEL_NEIGHBORHOOD), Purpose.UTILITY("Postcode updates"))
                .setField("postcode", GeolocationOperators.getPostcode(Geolocation.LAT_LON))
                .onChange(new Callback<Item>() {
                    @Override
                    protected void onInput(Item input) {
                        Log.d("Log", input.getAsString("postcode"));
                    }
                });
    }

    // City updated.
    public void CityUpdates() {
        uqi.getData(Geolocation.asUpdates(1000, Geolocation.LEVEL_CITY), Purpose.UTILITY("City updates"))
                .setField("city", GeolocationOperators.getCity(Geolocation.LAT_LON))
                .onChange(new Callback<Item>() {
                    @Override
                    protected void onInput(Item input) {
                        Log.d("Log", input.getAsString("city"));
                    }
                });
    }

    // Direction (left or right) updated.
    public void DirectionUpdates() {
        uqi.getData(Geolocation.asUpdates(1000, Geolocation.LEVEL_EXACT), Purpose.UTILITY("Direction updates"))
                .setField("direction", GeolocationOperators.getDirection())
                .onChange(new Callback<Item>() {
                    @Override
                    protected void onInput(Item input) {
                        Log.d("Log", input.getAsString("direction"));
                    }
                });
    }

    // Caller from a certain phone number.
    public void CallerFrom() {
        uqi.getData(Call.asUpdates(), Purpose.UTILITY("Caller from"))
                .listening("callerFrom", CallOperators.callerFrom("15555215556"))
                .forEach(new Callback<Item>() {
                    @Override
                    protected void onInput(Item input) {
                        Log.d("Log", String.valueOf(input.getAsBoolean("callerFrom")));
                    }
                });
    }

    // Caller in a specified phone list.
    public void CallerInList() {
        List<String> phoneList = new ArrayList<>();
        phoneList.add("15555215556");
        phoneList.add("15555215554");

        uqi.getData(Call.asUpdates(), Purpose.UTILITY("Caller from"))
                .listening("callerIn", CallOperators.callerInList(phoneList))
                .forEach(new Callback<Item>() {
                    @Override
                    protected void onInput(Item input) {
                        Log.d("Log", String.valueOf(input.getAsBoolean("callerIn")));
                    }
                });
    }

    // Contact emails checker, used for handling a certain email address in the contacts, emails updates etc.
    public void EmailsChecker() {
        uqi.getData(Contact.getAll(), Purpose.UTILITY("Contact emails"))
                .setField("emails", ContactOperators.getContactEmails())
                .forEach(new Callback<Item>() {
                    @Override
                    protected void onInput(Item input) {
                        List<String> emails = input.getValueByField("emails");

                        for (String email : emails) {
                            if (email.equals("test@gmail.com")) Log.d("Log", "The email in contacts");
                        }

                    }
                });
    }

    // Message sender from a certain phone number.
    public void MessageSenderFrom() {
        uqi.getData(Message.asIncomingSMS(), Purpose.UTILITY("Sender from"))
                .listening("senderFrom", MessageOperators.SenderFrom("15555215556"))
                .forEach(new Callback<Item>() {
                    @Override
                    protected void onInput(Item input) {
                        Log.d("Log", String. valueOf(input.getAsBoolean("senderFrom")));
                    }
                });
    }

    // Message sender in a specified phone list.
    public void MessageSenderInList() {
        List<String> phoneList = new ArrayList<>();
        phoneList.add("15555215556");
        phoneList.add("15555215554");

        uqi.getData(Message.asIncomingSMS(), Purpose.UTILITY("Sender In List"))
                .listening("senderIn", MessageOperators.SenderInList(phoneList))
                .forEach(new Callback<Item>() {
                    @Override
                    protected void onInput(Item input) {
                        Log.d("Log", String. valueOf(input.getAsBoolean("senderIn")));
                    }
                });
    }

    // Check whether the image taken from camera has faces or not.
    public void ImageHasFace() {
        uqi.getData(Image.takeFromCamera(), Purpose.UTILITY("Has Face"))
                .setField("hasFace", ImageOperators.hasFace(Image.IMAGE_DATA))
                .forEach(new Callback<Item>() {
                    @Override
                    protected void onInput(Item input) {
                        Log.d("Log", String.valueOf(input.getAsBoolean("hasFace")));
                    }
                });
    }




}
