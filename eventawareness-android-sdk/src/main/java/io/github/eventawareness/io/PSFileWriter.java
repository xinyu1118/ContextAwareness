package io.github.eventawareness.io;

import android.Manifest;

import org.json.JSONException;

import io.github.eventawareness.core.AsyncFunction;
import io.github.eventawareness.core.Function;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Assertions;
import io.github.eventawareness.utils.StorageUtils;

import java.io.File;

/**
 * Write an Item to a file
 */

class PSFileWriter<Tin> extends AsyncFunction<Tin, Void> {

    protected final Function<Tin, String> filePathGenerator;
    protected final boolean isPublic;
    protected final boolean append;

    PSFileWriter(Function<Tin, String> filePathGenerator, boolean isPublic, boolean append) {
        this.filePathGenerator = Assertions.notNull("filePathGenerator", filePathGenerator);
        this.isPublic = isPublic;
        this.append = append;

        this.addParameters(filePathGenerator, isPublic);
        if (isPublic) {
            this.addRequiredPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    protected String validFilePath = null;

    @Override
    public void applyInBackground(UQI uqi, Tin input) {
        String filePath = this.filePathGenerator.apply(uqi, input);
        File validFile = StorageUtils.getValidFile(uqi.getContext(), filePath, this.isPublic);
        this.validFilePath = validFile.getAbsolutePath();
        StorageUtils.writeToFile("" + input, validFile, append);
    }

    @Override
    protected Void init(UQI uqi, Tin input) {
        return null;
    }

}
