package io.github.contextawareness.image;


import android.graphics.Bitmap;

import io.github.contextawareness.core.UQI;

/**
 * Retrieve the Bitmap from the photo specified by an ImageData field.
 */
class ImageBitmapGetter extends ImageProcessor<Bitmap> {

    ImageBitmapGetter(String imageDataField) {
        super(imageDataField);
    }

    @Override
    protected Bitmap processImage(UQI uqi, ImageData imageData) {
        return imageData.getBitmap(uqi);
    }

}
