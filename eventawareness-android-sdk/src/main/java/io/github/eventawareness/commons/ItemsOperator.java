package io.github.eventawareness.commons;

import io.github.eventawareness.core.Function;
import io.github.eventawareness.core.Item;

import java.util.List;

/**
 * A function that takes a list of items as input.
 */

public abstract class ItemsOperator<Tout> extends Function<List<Item>, Tout> {
}
