package io.github.contextawareness.environment;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.PStream;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.purposes.Purpose;
import io.github.contextawareness.location.Geolocation;
import io.github.contextawareness.location.GeolocationOperators;

class LocationContexts extends Contexts {
    private PStreamProvider pStreamProvider;
    private PStream pStream;

    LocationContexts() {
        this.pStreamProvider = this.getProvider();
    }

    @Override
    public PStreamProvider getProvider() {
        return Geolocation.asUpdates(5000, Geolocation.LEVEL_EXACT);
    }

    @Override
    public PStream contextJudgment(UQI uqi) {
        pStream = uqi.getData(pStreamProvider, Purpose.UTILITY("location updates"))
                .setField("context_signal", GeolocationOperators.updates());
        return this.pStream;
    }
}
