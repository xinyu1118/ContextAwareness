package io.github.contextawareness.core.PersonalData;

import android.Manifest;
import android.os.FileObserver;
import android.util.Log;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.SignalItem;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Consts;

/**
 * Monitor a file inserted to a folder.
 */
public class FileInserted extends Contexts {

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    private String path;
    FilesObserver filesObserver;
    int recurrences;

    public FileInserted(String path) {
        recurrences = 0;
        this.path = path;
        this.addRequiredPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        this.addRequiredPermissions(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS);
    }

    @Override
    public void setListeningParameters(UQI uqi, int numOfRecurrences, long interval) {
        this.uqi = uqi;
        this.numOfRecurrences = numOfRecurrences;
        this.interval = interval;
    }

    @Override
    protected void provide() {
        if (path.isEmpty())
            Log.d(Consts.LIB_TAG, "Path doesn't exist.");
        else
            Log.d(Consts.LIB_TAG, path+" is being monitored...");
        filesObserver = new FilesObserver(path);
        filesObserver.startWatching();
    }

    /**
     * Inner class extends from FileObserver, and overrides onEvent() method
     * used to monitor file folder content changes, specially checking an image inserted
     * to a folder (move from or created).
     */
    private final class FilesObserver extends FileObserver {
        public FilesObserver (String path) {
            super(path, FileObserver.ALL_EVENTS);
        }

        @Override
        public void onEvent(int i, String path) {
            int event = i & FileObserver.ALL_EVENTS;
            if (event == FileObserver.DELETE)
                Log.d(Consts.LIB_TAG, "A file was deleted from the monitored directory.");
            if (event == FileObserver.MODIFY)
                Log.d(Consts.LIB_TAG, "Data was written to a file.");
            if (event == FileObserver.CREATE)
                Log.d(Consts.LIB_TAG, "A new file or subdirectory was created under the monitored directory.");
            // A file or subdirectory was moved from the monitored directory
            if (event == FileObserver.MOVED_FROM)
                Log.d(Consts.LIB_TAG, "A file or subdirectory was moved from the monitored directory.");

            if (event == FileObserver.MOVED_TO) {
                recurrences++;

                if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences > numOfRecurrences) {
                    Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                    filesObserver.stopWatching();
                    FileInserted.this.isCancelled = true;
                    FileInserted.this.finish();
                } else {
                    Log.d(Consts.LIB_TAG, "File inserted to the folder.");
                    FileInserted.this.isContextsAwared = true;
                    FileInserted.this.output(new SignalItem(System.currentTimeMillis(), FileInserted.this.isContextsAwared));
                }
                FileInserted.this.isContextsAwared = false;
            }

            if (event == FileObserver.ACCESS)
                Log.d(Consts.LIB_TAG, "Data was read from a file.");
        }
    }
}
