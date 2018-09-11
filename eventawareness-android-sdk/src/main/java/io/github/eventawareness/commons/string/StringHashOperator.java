package io.github.eventawareness.commons.string;

import io.github.eventawareness.utils.Assertions;
import io.github.eventawareness.utils.HashUtils;
import io.github.eventawareness.utils.Logging;

import java.security.NoSuchAlgorithmException;

/**
 * A function that hash a given string and return the hashed string
 */
final class StringHashOperator extends StringProcessor<String> {

    private final String hashAlgorithm;

    StringHashOperator(String stringField, String hashAlgorithm) {
        super(stringField);
        this.hashAlgorithm = Assertions.notNull("hashAlgorithm", hashAlgorithm);
        this.addParameters(hashAlgorithm);

    }

    @Override
    protected String processString(String stringValue) {
        try {
            return HashUtils.hash(stringValue, hashAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            Logging.warn("Hash function failed. Algorithm " + hashAlgorithm);
            e.printStackTrace();
            return null;
        }
    }
}
