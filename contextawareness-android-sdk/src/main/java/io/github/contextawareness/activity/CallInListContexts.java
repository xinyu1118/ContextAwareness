package io.github.contextawareness.activity;

import java.util.List;

import io.github.contextawareness.communication.Call;
import io.github.contextawareness.communication.CallOperators;
import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.PStream;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.purposes.Purpose;

class CallInListContexts extends Contexts {
    private PStreamProvider pStreamProvider;
    private PStream pStream;
    private List<String> phoneList;

    CallInListContexts(List<String> phoneList) {
        this.pStreamProvider = this.getProvider();
        this.phoneList = phoneList;
    }

    @Override
    public PStreamProvider getProvider() {
        return Call.asUpdates();
    }

    @Override
    public PStream contextJudgment(UQI uqi) {
        pStream = uqi.getData(pStreamProvider, Purpose.UTILITY("caller in list"))
                .setField("context_signal", CallOperators.callerInList(phoneList));
        return pStream;
    }
}