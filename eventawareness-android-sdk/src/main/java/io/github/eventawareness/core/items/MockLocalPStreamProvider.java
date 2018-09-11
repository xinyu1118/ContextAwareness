package io.github.eventawareness.core.items;

import io.github.eventawareness.core.PStreamProvider;

import java.util.List;


/**
 * Provide a bunch of local TestItems based on a list of TestObjects.
 */

class MockLocalPStreamProvider extends PStreamProvider {

    private final List<TestObject> testObjects;

    MockLocalPStreamProvider(List<TestObject> testObjects) {
        this.testObjects = testObjects;
        this.addParameters(testObjects);
    }

    @Override
    protected void provide() {
        for(TestObject testObject : testObjects){
            if (this.isCancelled) break;
            TestItem item = new TestItem(testObject);
            this.output(item);
        }
        this.finish();
    }

}
