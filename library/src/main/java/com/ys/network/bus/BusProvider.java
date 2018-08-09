package com.ys.network.bus;

import com.squareup.otto.Bus;

public class BusProvider extends Bus{

    private volatile static BusProvider bus;

    private BusProvider() {
    }

    public static BusProvider getInstance() {
        if (bus == null) {
            synchronized (BusProvider.class) {
                if (bus == null) {
                    bus = new BusProvider();
                }
            }
        }
        return bus;
    }
}
