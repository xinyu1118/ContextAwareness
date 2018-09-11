package io.github.eventawareness.accessibility;

import io.github.eventawareness.commons.item.ItemOperators;
import io.github.eventawareness.core.Callback;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.PStreamProvider;
import io.github.eventawareness.core.purposes.Purpose;
import io.github.eventawareness.utils.AppUtils;

/**
 * Provide a live stream of browser search events.
 */

class BrowserSearchEventsProvider extends PStreamProvider {

    @Override
    protected void provide() {
        getUQI().getData(AccEvent.asTextEntries(), Purpose.LIB_INTERNAL("Event Triggers"))
                .filter(ItemOperators.isFieldIn(AccEvent.PACKAGE_NAME,
                        new String[]{
                                AppUtils.APP_PACKAGE_SEARCHBOX,
                                AppUtils.APP_PACKAGE_FIREFOX,
                                AppUtils.APP_PACKAGE_OPERA,
                                AppUtils.APP_PACKAGE_CHROME}))
                .forEach(new Callback<Item>() {
                    @Override
                    protected void onInput(Item input) {
                        output(new BrowserSearch(input.getValueByField(AccEvent.TEXT).toString()));
                    }
                });
    };
}
