package io.github.eventawareness.image;


import io.github.eventawareness.core.UQI;
import io.github.eventawareness.location.LatLon;

/**
 * Detect faces in an image.
 */
class ImageFaceDetector extends ImageProcessor<Boolean> {

    ImageFaceDetector(String imageDataField) {
        super(imageDataField);
    }

    @Override
    protected Boolean processImage(UQI uqi, ImageData imageData) {
        return imageData.hasFace(uqi);
    }

}
