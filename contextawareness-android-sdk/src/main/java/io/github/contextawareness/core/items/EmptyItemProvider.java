package io.github.contextawareness.core.items;

import io.github.contextawareness.core.PStreamProvider;


class EmptyItemProvider extends PStreamProvider {

    EmptyItemProvider() {
    }

    @Override
    protected void provide() {
        this.output(new EmptyItem());
        this.finish();
    }
}