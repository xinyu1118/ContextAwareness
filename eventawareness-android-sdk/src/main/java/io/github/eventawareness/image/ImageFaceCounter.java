package io.github.eventawareness.image;


import io.github.eventawareness.core.UQI;

/**
 * Count faces in an image.
 */
class ImageFaceCounter extends ImageProcessor<Integer> {

    ImageFaceCounter(String imageDataField) {
        super(imageDataField);
    }

    @Override
    protected Integer processImage(UQI uqi, ImageData imageData) {
        return imageData.countFaces(uqi);
    }

}
