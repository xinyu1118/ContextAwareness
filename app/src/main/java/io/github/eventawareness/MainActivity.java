package io.github.eventawareness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.github.eventawareness.audio.Audio;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.core.purposes.Purpose;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UQI uqi = new UQI(this);
        uqi.getData(Audio.recordPeriodic(1000, 3000), Purpose.UTILITY("test"))
                .debug();
    }
}
