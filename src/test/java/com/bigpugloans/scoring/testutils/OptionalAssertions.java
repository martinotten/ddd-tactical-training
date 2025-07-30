package com.bigpugloans.scoring.testutils;

import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Utility class for safely handling Optional values in tests
 */
public final class OptionalAssertions {
    
    private OptionalAssertions() {
        // Utility class
    }
    
    /**
     * Asserts that the Optional is present and runs assertions on the contained value
     */
    public static <T> void assertPresent(Optional<T> optional, Consumer<T> assertions) {
        assertTrue(optional.isPresent(), "Expected Optional to be present but was empty");
        assertions.accept(optional.get());
    }
    
    /**
     * Asserts that the Optional is present and returns the contained value safely
     */
    public static <T> T assertPresent(Optional<T> optional) {
        assertTrue(optional.isPresent(), "Expected Optional to be present but was empty");
        return optional.get();
    }
    
    /**
     * Asserts that the Optional is present with a custom message and returns the contained value
     */
    public static <T> T assertPresent(Optional<T> optional, String message) {
        assertTrue(optional.isPresent(), message);
        return optional.get();
    }
}