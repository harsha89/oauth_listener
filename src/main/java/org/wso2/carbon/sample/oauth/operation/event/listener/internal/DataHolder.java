package org.wso2.carbon.sample.oauth.operation.event.listener.internal;

import org.wso2.carbon.apimgt.impl.APIManagerConfigurationService;
import org.wso2.carbon.sample.oauth.operation.event.listener.SampleOAuthOperationListener;
import org.wso2.carbon.user.core.service.RealmService;

public class DataHolder {

    private static RealmService realmService;
    private static volatile DataHolder dataHolder;
    private static SampleOAuthOperationListener customOAuthOperationEventListener;
    private APIManagerConfigurationService apiManagerConfigService;

    private DataHolder() {

    }

    public static DataHolder getInstance() {

        if (dataHolder == null) {

            synchronized (DataHolder.class) {
                if (dataHolder == null) {
                    dataHolder = new DataHolder();
                    customOAuthOperationEventListener = new SampleOAuthOperationListener();
                }
            }

        }

        return dataHolder;
    }

    public void setRealmService(RealmService realmService) {
        this.realmService = realmService;
    }

    public RealmService getRealmService() {
        return realmService;
    }

    public SampleOAuthOperationListener getCustomOAuthOperationEventListener() {
        return customOAuthOperationEventListener;
    }

    public void setAPIManagerConfigService(APIManagerConfigurationService apiManagerConfigService) {
        this.apiManagerConfigService = apiManagerConfigService;
    }

    public APIManagerConfigurationService getApiManagerConfigService() {
        return apiManagerConfigService;
    }
}
