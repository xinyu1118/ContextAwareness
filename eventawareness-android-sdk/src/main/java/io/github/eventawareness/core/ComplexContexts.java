package io.github.eventawareness.core;


/**
 * A helper class used to sense logically complicated contexts.
 */
public class ComplexContexts {

    /**
     * Support the logic operation 'and' for two contexts instances.
     * @param contexts1 one contexts instance
     * @param contexts2 the other contexts instance
     * @return Contexts provider
     */
    public static Contexts AND(Contexts contexts1, Contexts contexts2) {
        return new AND(contexts1, contexts2);
    }

    /**
     * Support the logic operation 'or' for two contexts instances.
     * @param contexts1 one contexts instance
     * @param contexts2 the other contexts instance
     * @return Contexts provider
     */
    public static Contexts OR(Contexts contexts1, Contexts contexts2) {
        return new OR(contexts1, contexts2);
    }

    /**
     * Support the logic operation 'not' for one contexts instance.
     * @param contexts one contexts instance
     * @return Contexts provider
     */
    public static Contexts NOT(Contexts contexts) {
        return new NOT(contexts);
    }
}
