package io.github.contextawareness.environment;

import io.github.contextawareness.core.Contexts;

public class Environment {

    public static class Location {

        public static Contexts LocationUpdates() {
            return new LocationContexts();
        }

    }
}
