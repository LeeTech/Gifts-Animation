package cn.lry.animation.utils;

import com.hwangjr.rxbus.Bus;

/**
 * Maintains a singleton instance for obtaining the bus. Ideally this would be replaced with a more efficient means
 * such as through injection directly into interested classes.
 */
public final class BusProvider {
    /**
     * Check out the bus, like identifier or thread enforcer etc.
     */
    private static Bus BUS = new Bus();

    private BusProvider() {
    }

    public static synchronized Bus getInstance() {
        if (BUS == null) {
            BUS = new Bus();
        }
        return BUS;
    }
}
