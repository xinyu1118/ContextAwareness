package io.github.contextawareness.activity;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.PStream;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.purposes.Purpose;

class ActivityContexts extends Contexts {
    private PStreamProvider pStreamProvider;
    private PStream pStream;
    private int queryActivity;

    ActivityContexts(int queryActivity) {
        this.pStreamProvider = this.getProvider();
        this.queryActivity = queryActivity;
    }

    @Override
    public PStreamProvider getProvider() {
        return UserActivityInfo.asUpdates(3000);
    }

    @Override
    public PStream contextJudgment(UQI uqi) {
        pStream = uqi.getData(pStreamProvider, Purpose.UTILITY("activity recognition"))
                .setField("context_signal", UserActivityInfoOperators.recognition(queryActivity));
        return pStream;
    }
}
