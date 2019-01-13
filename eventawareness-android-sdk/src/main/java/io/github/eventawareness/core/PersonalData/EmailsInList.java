package io.github.eventawareness.core.PersonalData;


import android.Manifest;
import android.util.Log;

import java.util.List;

import io.github.eventawareness.communication.Contact;
import io.github.eventawareness.communication.ContactOperators;
import io.github.eventawareness.core.Contexts;
import io.github.eventawareness.core.SignalItem;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.core.exceptions.PSException;
import io.github.eventawareness.core.purposes.Purpose;
import io.github.eventawareness.utils.Consts;

public class EmailsInList extends Contexts {

    private List<String> queryList;

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    public EmailsInList(List<String> queryList) {
        this.queryList = queryList;
        this.addRequiredPermissions(Manifest.permission.READ_CONTACTS);
    }

    @Override
    public void setListeningParameters(UQI uqi, int numOfRecurrences, long interval) {
        this.uqi = uqi;
        this.numOfRecurrences = numOfRecurrences;
        this.interval = interval;
    }

    public SignalItem listening() {
        UQI emailUQI = new UQI(this.getUQI().getContext());
        try {
            List<List<String>> contactEmails = emailUQI.getData(Contact.getAll(), Purpose.UTILITY("Get contact emails."))
                    .setField("emails", ContactOperators.getContactEmails())
                    .asList("emails");

            if (contactEmails.size() != 0) {
                for (int i = 0; i < contactEmails.size(); i++) {
                    for (int j = 0; j < contactEmails.get(i).size(); j++) {
                        if (queryList == null) {
                            Log.d(Consts.LIB_TAG, "Please provide email lists, it's null now.");
                        }
                        for (String email : queryList) {
                            if (contactEmails.get(i).get(j).equals(email)) {
                                Log.d(Consts.LIB_TAG, "Contact emails in the list.");
                                this.isContextsAwared = true;
                                break;
                            }
                        }
                    }
                }
            } else {
                Log.d(Consts.LIB_TAG, "No emails in contact lists, please check it.");
            }
        } catch (PSException e) {
            e.printStackTrace();
        }
        return new SignalItem(System.currentTimeMillis(), this.isContextsAwared);
    }

    @Override
    protected void provide() {
        int recurrences = 0;
        while (!this.isCancelled) {
            SignalItem signalItem = listening();
            if (signalItem != null) this.output(signalItem);
            recurrences++;
            if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences >= numOfRecurrences) {
                Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                this.isCancelled = true;
            } else {
                try {
                    Thread.sleep(this.interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        this.finish();
    }
}
