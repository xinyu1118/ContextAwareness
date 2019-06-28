package io.github.contextawareness.activity;

import io.github.contextawareness.audio.Audio;
import io.github.contextawareness.audio.AudioOperators;
import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.PStream;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.purposes.Purpose;

class LoudnessContexts extends Contexts {
    private PStreamProvider pStreamProvider;
    private PStream pStream;
    private String operators;
    private Double threshold;

    LoudnessContexts(String operators, Double threshold) {
        this.pStreamProvider = this.getProvider();
        this.operators = operators;
        this.threshold = threshold;
    }

    @Override
    public PStreamProvider getProvider() {
        return Audio.recordPeriodic(1000, 5000);
    }

    @Override
    public PStream contextJudgment(UQI uqi) {
        pStream = uqi.getData(pStreamProvider, Purpose.UTILITY("loudenss level"))
                .setField("context_signal", AudioOperators.loudnessLevel(operators, threshold));
        return this.pStream;
    }
}
