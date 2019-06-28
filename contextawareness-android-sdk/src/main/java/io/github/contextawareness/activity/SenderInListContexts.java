package io.github.contextawareness.activity;

import java.util.List;

import io.github.contextawareness.communication.Message;
import io.github.contextawareness.communication.MessageOperators;
import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.PStream;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.purposes.Purpose;

class SenderInListContexts extends Contexts {
    private PStreamProvider pStreamProvider;
    private PStream pStream;
    private List<String> senderList;

    SenderInListContexts(List<String> senderList) {
        this.pStreamProvider = this.getProvider();
        this.senderList = senderList;
    }

    @Override
    public PStreamProvider getProvider() {
        return Message.asIncomingSMS();
    }

    @Override
    public PStream contextJudgment(UQI uqi) {
        pStream = uqi.getData(pStreamProvider, Purpose.UTILITY("sender in list"))
                .setField("context_signal", MessageOperators.SenderInList(senderList));
        return pStream;
    }
}