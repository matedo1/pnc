/**
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.pnc.spi.notifications;

import java.util.Optional;

/**
 * Notification mechanism for Web Sockets. All implementation details should be placed in AttachedClient.
 */
public interface Notifier {

    void attachClient(AttachedClient attachedClient);

    void detachClient(AttachedClient attachedClient);

    int getAttachedClientsCount();

    void sendMessage(Object message);

    Optional<AttachedClient> getAttachedClient(String sessionId);

    MessageCallback getCallback();

    /**
     * Sends a mesage to clients that has subscribed to the topic.
     * Messages from the topic can be optionally refined by qualifier which is topic specific
     *
     * @param message
     * @param topic
     * @param qualifier
     */
    void sendToSubscribers(Object message, String topic, String qualifier);

    void onBpmProcessClientSubscribe(AttachedClient client, String messagesId);

    enum Topic {
        COMPONENT_BUILD("component-build"),
        CAUSEWAY_PUSH("causeway-push");

        Topic(String id) {
            this.id = id;
        };

        private String id;

        public String getId() {
            return id;
        }
    }
}
