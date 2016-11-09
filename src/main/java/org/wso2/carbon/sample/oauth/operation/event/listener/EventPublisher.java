/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.sample.oauth.operation.event.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.impl.APIManagerAnalyticsConfiguration;
import org.wso2.carbon.databridge.agent.DataPublisher;
import org.wso2.carbon.databridge.agent.exception.DataEndpointAgentConfigurationException;
import org.wso2.carbon.databridge.agent.exception.DataEndpointAuthenticationException;
import org.wso2.carbon.databridge.agent.exception.DataEndpointConfigurationException;
import org.wso2.carbon.databridge.agent.exception.DataEndpointException;
import org.wso2.carbon.databridge.commons.exception.TransportException;
import org.wso2.carbon.sample.oauth.operation.event.listener.internal.DataHolder;

public class EventPublisher {
    private static final Log log = LogFactory.getLog(EventPublisher.class);
    private static DataPublisher publisher;

    private EventPublisher(){

    }

    public static DataPublisher getInstance() {
        if (publisher == null) {
            synchronized (EventPublisher.class) {
                if (publisher == null) {
                    APIManagerAnalyticsConfiguration config = DataHolder.getInstance().getApiManagerConfigService()
                            .getAPIAnalyticsConfiguration();
                    String serverUser = config.getDasReceiverServerUser();
                    String serverPassword = config.getDasReceiverServerPassword();
                    String serverURL = config.getDasReceiverUrlGroups();
                    String serverAuthURL = config.getDasReceiverAuthUrlGroups();

                    try {
                        //Create new DataPublisher for the tenant.
                        publisher = new DataPublisher(null, serverURL, serverAuthURL, serverUser, serverPassword);
                    } catch (DataEndpointConfigurationException e) {
                        log.error("Error while creating data publisher", e);
                    } catch (DataEndpointException e) {
                        log.error("Error while creating data publisher", e);
                    } catch (DataEndpointAgentConfigurationException e) {
                        log.error("Error while creating data publisher", e);
                    } catch (TransportException e) {
                        log.error("Error while creating data publisher", e);
                    } catch (DataEndpointAuthenticationException e) {
                        log.error("Error while creating data publisher", e);
                    }
                }
            }
        }
        return publisher;
    }
}
