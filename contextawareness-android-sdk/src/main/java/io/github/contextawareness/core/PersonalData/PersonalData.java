package io.github.contextawareness.core.PersonalData;


import java.util.List;

import io.github.contextawareness.core.Contexts;

/**
 * A helper class used to sense personal data related contexts.
 */
public class PersonalData {

    /**
     * Compare the average loudness (recorded from microphone in dB) with a threshold.
     * @param comparisonOperator the comparison operator,
     *                           could be found in 'Operators' class, e.g. Operators.GTE.
     * @param threshold the compared number
     * @return Contexts provider
     */
    public static Contexts LoudnessLevel(String comparisonOperator, double threshold) {
        return new LoudnessLevel(comparisonOperator, threshold);
    }

    /**
     * Monitor incoming calls.
     * @return Contexts provider
     */
    public static Contexts CallsComingIn() {
        return new CallsComingIn();
    }

    /**
     * Identify an incoming call from a certain phone number.
     * @param phoneNumber the phone number
     * @return Contexts provider
     */
    public static Contexts CallerFrom(String phoneNumber) {
        return new CallerFrom(phoneNumber);
    }

    /**
     * Identify an incoming call in a phone number list.
     * @param queryList the query phone number list
     * @return Contexts provider
     */
    public static Contexts CallerFrom(List<String> queryList) {
        return new CallerFrom(queryList);
    }

    /**
     * Check whether there are contact emails in a given list.
     * @param queryList the query email list
     * @return Contexts provider
     */
    public static Contexts EmailsInList(List<String> queryList) {
        return new EmailsInList(queryList);
    }

    /**
     * Monitor contacts updates.
     * @return Contexts provider
     */
    public static Contexts ContactsUpdated() {
        return new ContactsUpdated();
    }

    /**
     * Monitor incoming messages.
     * @return Contexts provider
     */
    public static Contexts MessagesComingIn() {
        return new MessagesComingIn();
    }

    /**
     * Identify an incoming message from a certain phone number.
     * @param phoneNumber the phone number
     * @return Contexts provider
     */
    public static Contexts SenderFrom(String phoneNumber) {
        return new SenderFrom(phoneNumber);
    }

    /**
     * Identify an incoming call in a phone number list.
     * @param queryList the query phone number list
     * @return Contexts provider
     */
    public static Contexts SenderFrom(List<String> queryList) {
        return new SenderFrom(queryList);
    }

    /**
     * Listen to message content updates.
     * @return Contexts provider
     */
    public static Contexts MessagesUpdated() {
        return new MessagesUpdated();
    }

    /**
     * Listen to media library updates.
     * @return Contexts provider
     */
    public static Contexts MediaLibraryUpdated() {
        return new MediaLibraryUpdated();
    }

    /**
     * Monitor a file inserted to a folder.
     * @param path the folder path
     * @return Contexts provider
     */
    public static Contexts FileInserted(String path) {
        return new FileInserted(path);
    }

}
