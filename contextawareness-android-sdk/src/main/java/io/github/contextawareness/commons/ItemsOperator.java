package io.github.contextawareness.commons;

import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;

import java.util.List;

/**
 * A function that takes a list of items as input.
 */

public abstract class ItemsOperator<Tout> extends Function<List<Item>, Tout> {
}
