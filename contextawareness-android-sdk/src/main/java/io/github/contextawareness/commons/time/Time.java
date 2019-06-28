package io.github.contextawareness.commons.time;

import io.github.contextawareness.core.Contexts;

public class Time {

    public static class Dates {

        public static Contexts isHoliday(String date) {
            return new HolidayContexts(date);
        }

    }
}
