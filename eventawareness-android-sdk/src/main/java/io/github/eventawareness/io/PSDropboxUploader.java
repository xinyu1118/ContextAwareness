package io.github.eventawareness.io;

import android.Manifest;

import io.github.eventawareness.core.Function;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.DropboxUtils;

/**
 * Upload an item to Dropbox
 */

final class PSDropboxUploader<Tin> extends PSFileWriter<Tin> {

    PSDropboxUploader(Function<Tin, String> filePathGenerator, boolean append) {
        super(filePathGenerator, false, append);
        this.addRequiredPermissions(Manifest.permission.INTERNET);
    }

    @Override
    public void applyInBackground(UQI uqi, Tin input) {
        super.applyInBackground(uqi, input);
        DropboxUtils.addToWaitingList(uqi, this.validFilePath);
        DropboxUtils.syncFiles(uqi, this.append);
    }
}
