package io.github.contextawareness.core.ProviderAnd_Or;

import java.util.HashMap;
import java.util.Map;

import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.core.Function;

public class EnvironmentFactor {
    public PStreamProvider provider;
    public HashMap<String, Function> fieldMap;

    public EnvironmentFactor(PStreamProvider provider, HashMap<String, Function> fieldMap) {
        this.provider = provider;
        this.fieldMap = fieldMap;
    }
}
