package io.github.contextawareness.image;


import io.github.contextawareness.core.UQI;
import io.github.contextawareness.location.LatLon;

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
