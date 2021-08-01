package ru.gb.sklyarov.cloud.client.factory;

import ru.gb.sklyarov.cloud.client.service.NetworkService;
import ru.gb.sklyarov.cloud.client.service.impl.NettyNetworkService;
import ru.gb.sklyarov.cloud.client.util.PropertyUtil;
import ru.gb.sklyarov.cloud.client.util.impl.PropertyUtilImpl;

public class Factory {

    public static NetworkService getNetworkService() {
        return NettyNetworkService.getInstance();
    }

    public static PropertyUtil getProperty() {
        return new PropertyUtilImpl();
    }
}
