package io.github.contextawareness.commons.time;

import android.util.Log;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.PStream;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.items.EmptyItem;
import io.github.contextawareness.core.purposes.Purpose;

class HolidayContexts extends Contexts {
    private PStreamProvider pStreamProvider;
    private PStream pStream;
    private String date;

    HolidayContexts(String date) {
        this.date = date;
        this.pStreamProvider = this.getProvider();
    }

    @Override
    public PStreamProvider getProvider() {
        return EmptyItem.asUpdates();
    }

    @Override
    public PStream contextJudgment(UQI uqi) {
        pStream = uqi.getData(pStreamProvider, Purpose.UTILITY("holidays"))
                .setField("context_signal", TimeOperators.isHoliday(this.date));
        return pStream;
    }
}
