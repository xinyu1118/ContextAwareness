package io.github.contextawareness.activity;

import java.util.List;
import io.github.contextawareness.core.Contexts;

public class Activity {

    public static final int IN_VEHICLE = 0;
    public static final int ON_BICYCLE = 1;
    public static final int ON_FOOT = 2;
    public static final int RUNNING = 3;
    public static final int STILL = 4;
    public static final int TILTING = 5;
    public static final int WALKING = 6;
    public static final int UNKNOWN = 7;

    public static Contexts Recognition(int queryActivity) {
        return new ActivityContexts(queryActivity);
    }

    public static Contexts CallFrom(String caller) {
        return new CallFromContexts(caller);
    }

    public static Contexts CallerInList(List<String> phoneList) {
        return new CallInListContexts(phoneList);
    }

    public static Contexts MessageFrom(String sender) {
        return new SenderFromContexts(sender);
    }

    public static Contexts MessageSenderInList(List<String> senderList) {
        return new SenderInListContexts(senderList);
    }

    public static Contexts isOverSpeed(Float threshold) {
        return new SpeedContexts(threshold);
    }

    public static Contexts LoudnessLevel(String operators, Double threshold) {
        return new LoudnessContexts(operators, threshold);
    }

}
