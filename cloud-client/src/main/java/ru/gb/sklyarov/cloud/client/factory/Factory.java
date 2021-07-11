package ru.gb.sklyarov.cloud.client.factory;

import ru.gb.sklyarov.cloud.client.service.NetworkService;
import ru.gb.sklyarov.cloud.client.service.impl.IONetworkService;
import ru.gb.sklyarov.cloud.client.util.PropertyUtil;
import ru.gb.sklyarov.cloud.client.util.impl.PropertyUtilImpl;

public class Factory {

    public static NetworkService getNetworkService() {
        return IONetworkService.getInstance();
    }

    public static PropertyUtil getProperty() {
        return new PropertyUtilImpl();
    }
}
