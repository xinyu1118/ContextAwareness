package io.github.eventawareness.image;


import io.github.eventawareness.core.UQI;

/**
 * Detect characters in an image.
 */
class ImageCharacterDetector extends ImageProcessor<Boolean> {

    ImageCharacterDetector(String imageDataField) {
        super(imageDataField);
    }

    @Override
    protected Boolean processImage(UQI uqi, ImageData imageData) {
        return imageData.hasCharacter(uqi);
    }

}
