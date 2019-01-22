package io.github.contextawareness.accessibility;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.utils.PermissionUtils;

/**
 * The abstract class of Accessibility-related stream providers.
 */
abstract class AccEventProvider extends PStreamProvider {

    private boolean registered = false;

    AccEventProvider() {
        this.addRequiredPermissions(PermissionUtils.USE_ACCESSIBILITY_SERVICE);
    }

    @Override
    protected void provide() {
        PSAccessibilityService.registerProvider(this);
        registered = true;
    }

    @Override
    protected void onCancel(UQI uqi) {
        PSAccessibilityService.unregisterProvider(this);
        registered = false;
    }

    public abstract void handleAccessibilityEvent(AccessibilityEvent event, AccessibilityNodeInfo rootNode);
}
