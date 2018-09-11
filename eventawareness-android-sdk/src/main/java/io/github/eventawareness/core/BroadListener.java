package io.github.eventawareness.core;

/**
 * An interface to help monitoring variable content change
 */
public interface BroadListener {
    public void onSuccess();
    public void onFail(String msg);
}
