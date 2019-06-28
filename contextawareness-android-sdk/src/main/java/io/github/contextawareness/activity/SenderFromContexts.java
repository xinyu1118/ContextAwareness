package io.github.contextawareness.activity;

import io.github.contextawareness.communication.Message;
import io.github.contextawareness.communication.MessageOperators;
import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.PStream;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.purposes.Purpose;

class SenderFromContexts extends Contexts {
    private PStreamProvider pStreamProvider;
    private PStream pStream;
    private String sender;

    SenderFromContexts(String sender) {
        this.pStreamProvider = this.getProvider();
        this.sender = sender;
    }

    @Override
    public PStreamProvider getProvider() {
        return Message.asIncomingSMS();
    }

    @Override
    public PStream contextJudgment(UQI uqi) {
        pStream = uqi.getData(pStreamProvider, Purpose.UTILITY("sender from"))
                .setField("context_signal", MessageOperators.SenderFrom(sender));
        return pStream;
    }
}