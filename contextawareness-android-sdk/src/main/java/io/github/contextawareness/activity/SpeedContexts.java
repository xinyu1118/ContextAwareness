package io.github.contextawareness.activity;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.Operators;
import io.github.contextawareness.core.PStream;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.purposes.Purpose;
import io.github.contextawareness.location.Geolocation;
import io.github.contextawareness.location.GeolocationOperators;

class SpeedContexts extends Contexts {
    private PStreamProvider pStreamProvider;
    private PStream pStream;
    private Float threshold;

    SpeedContexts(Float threshold) {
        this.pStreamProvider = this.getProvider();
        this.threshold = threshold;
    }

    @Override
    public PStreamProvider getProvider() {
        return Geolocation.asUpdates(5000, Geolocation.LEVEL_NEIGHBORHOOD);
    }

    @Override
    public PStream contextJudgment(UQI uqi) {
        pStream = uqi.getData(pStreamProvider, Purpose.UTILITY("speed monitor"))
                .setField("context_signal", GeolocationOperators.SpeedMonitor(Operators.GTE, threshold));
        return this.pStream;
    }
}
