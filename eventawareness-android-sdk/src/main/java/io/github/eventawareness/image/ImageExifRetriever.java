package io.github.eventawareness.image;


import android.media.ExifInterface;

import io.github.eventawareness.core.UQI;

/**
 * Retrieve the EXIF metadata of photo.
 * The EXIF information is an ExifInterface in Android.
 */
class ImageExifRetriever extends ImageProcessor<ExifInterface> {

    ImageExifRetriever(String imageDataField) {
        super(imageDataField);
    }

    @Override
    protected ExifInterface processImage(UQI uqi, ImageData imageData) {
        return imageData.getExif(uqi);
    }

}
