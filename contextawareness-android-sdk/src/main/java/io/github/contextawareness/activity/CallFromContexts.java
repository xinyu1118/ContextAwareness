package io.github.contextawareness.activity;

import io.github.contextawareness.communication.Call;
import io.github.contextawareness.communication.CallOperators;
import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.PStream;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.purposes.Purpose;

class CallFromContexts extends Contexts {
    private PStreamProvider pStreamProvider;
    private PStream pStream;
    private String caller;

    CallFromContexts(String caller) {
        this.pStreamProvider = this.getProvider();
        this.caller = caller;
    }

    @Override
    public PStreamProvider getProvider() {
        return Call.asUpdates();
    }

    @Override
    public PStream contextJudgment(UQI uqi) {
        pStream = uqi.getData(pStreamProvider, Purpose.UTILITY("caller from"))
                .setField("context_signal", CallOperators.callerFrom(caller));
        return pStream;
    }
}