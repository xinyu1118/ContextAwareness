package io.github.contextawareness.core.PersonalData;

import android.Manifest;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.SignalItem;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Consts;

/**
 * Monitor media library updated, including adding, deleting, modifying.
 */
public class MediaLibraryUpdated extends Contexts {

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    int recurrences;
    ImagesObserver imagesObserver = new ImagesObserver(new Handler());

    public MediaLibraryUpdated() {
        recurrences = 0;
        this.addRequiredPermissions(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    public void setListeningParameters(UQI uqi, int numOfRecurrences, long interval) {
        this.uqi = uqi;
        this.numOfRecurrences = numOfRecurrences;
        this.interval = interval;
    }

    @Override
    protected void provide() {
        uqi.getContext().getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,true, imagesObserver);
    }

    /**
     * Inner class extends from ContentObserver, and overrides onChange() method
     * used to monitor image content changes.
     */
    private final class ImagesObserver extends ContentObserver {
        public ImagesObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            recurrences++;
            if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences > numOfRecurrences) {
                Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                uqi.getContext().getContentResolver().unregisterContentObserver(imagesObserver);
                MediaLibraryUpdated.this.isCancelled = true;
                MediaLibraryUpdated.this.finish();
            } else {
                Log.d(Consts.LIB_TAG, "Media library updated.");
                MediaLibraryUpdated.this.isContextsAwared = true;
                MediaLibraryUpdated.this.output(new SignalItem(System.currentTimeMillis(), MediaLibraryUpdated.this.isContextsAwared));
            }
            MediaLibraryUpdated.this.isContextsAwared = false;
        }
    }
}
