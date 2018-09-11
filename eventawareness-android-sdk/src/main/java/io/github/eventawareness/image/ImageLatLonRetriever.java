package io.github.eventawareness.image;


import io.github.eventawareness.core.UQI;
import io.github.eventawareness.location.LatLon;

/**
 * Retrieve the latitude and longitude of the image.
 */
class ImageLatLonRetriever extends ImageProcessor<LatLon> {

    ImageLatLonRetriever(String imageDataField) {
        super(imageDataField);
    }

    @Override
    protected LatLon processImage(UQI uqi, ImageData imageData) {
        return imageData.getLatLon(uqi);
    }

}
