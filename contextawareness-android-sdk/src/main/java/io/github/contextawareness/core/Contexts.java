package io.github.contextawareness.core;

public abstract class Contexts {

    public abstract PStreamProvider getProvider();
    public abstract PStream contextJudgment(UQI uqi);

}
