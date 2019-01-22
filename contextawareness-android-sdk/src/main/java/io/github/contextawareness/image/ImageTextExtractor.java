package io.github.contextawareness.image;


import io.github.contextawareness.core.UQI;

/**
 * Detect texts in an image.
 */
class ImageTextExtractor extends ImageProcessor<String> {

    ImageTextExtractor(String imageDataField) {
        super(imageDataField);
    }

    @Override
    protected String processImage(UQI uqi, ImageData imageData) {
        return imageData.detectText(uqi);
    }

}
