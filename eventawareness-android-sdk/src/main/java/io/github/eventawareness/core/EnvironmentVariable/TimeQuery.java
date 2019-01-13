package io.github.eventawareness.core.EnvironmentVariable;


import android.text.format.DateFormat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.github.eventawareness.core.Contexts;
import io.github.eventawareness.core.SignalItem;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Consts;

/**
 * Sense time related contexts.
 */
public class TimeQuery extends Contexts {

    public static final int Query_Date = 0;
    public static final int Query_Day_Of_Week = 1;
    public static final int Query_Hour_Of_Day = 2;
    public static final int Query_Hour_In_Section = 3;

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    private int queryMode;

    private Date date;
    private int time;
    private int startHour;
    private int endHour;

    public TimeQuery(int queryMode, Date date) {
        this.queryMode = queryMode;
        this.date = date;
    }

    public TimeQuery(int queryMode, int time) {
        this.queryMode = queryMode;
        this.time = time;
    }

    public TimeQuery(int queryMode, int startHour, int endHour) {
        this.queryMode = queryMode;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    @Override
    public void setListeningParameters(UQI uqi, int numOfRecurrences, long interval) {
        this.uqi = uqi;
        this.numOfRecurrences = numOfRecurrences;
        this.interval = interval;
    }

    public SignalItem listening() {
        Calendar calendar = Calendar.getInstance();
        switch (queryMode) {
            case Query_Date:
                try {
                    Date calendarDate = calendar.getTime();
                    String dateString = DateFormat.format("yyyy-MM-dd", calendarDate).toString();
//                    String dateString = DateFormat.format("yyyy-MM-dd kk:mm:ss", calendarDate).toString();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date formattedDate = format.parse(dateString);
                    if (date.equals(formattedDate)) {
                        Log.d(Consts.LIB_TAG, "It is "+dateString+".");
                        this.isContextsAwared = true;
                    }
                } catch (ParseException e) {
                    e.getStackTrace();
                }
                break;
            case Query_Day_Of_Week:
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek == time) {
                    Log.d(Consts.LIB_TAG, "It is the "+String.valueOf(dayOfWeek)+"th day.");
                    this.isContextsAwared = true;
                }
                break;
            case Query_Hour_Of_Day:
                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                if (hourOfDay == time) {
                    Log.d(Consts.LIB_TAG, "It is "+String.valueOf(hourOfDay)+".");
                    this.isContextsAwared = true;
                }
                break;
            case Query_Hour_In_Section:
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                if (currentHour >= startHour && currentHour <= endHour) {
                    Log.d(Consts.LIB_TAG, "It is between "+String.valueOf(startHour)+" and "+String.valueOf(endHour)+" hours.");
                    this.isContextsAwared = true;
                }
                break;
            default:
                Log.d(Consts.LIB_TAG, "No matchable operator, please select one from the predefined collection.");
        }
        return new SignalItem(System.currentTimeMillis(), this.isContextsAwared);

    }

    @Override
    protected void provide() {
        int recurrences = 0;
        SignalItem signalItem;
        while (!this.isCancelled) {
            signalItem = listening();
            recurrences++;
            if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences > numOfRecurrences) {
                Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                this.isCancelled = true;
            } else {
                this.output(signalItem);
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.isContextsAwared = false;
        }
        this.finish();
    }
}
