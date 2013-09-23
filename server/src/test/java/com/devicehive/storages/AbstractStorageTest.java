package com.devicehive.storages;

import com.devicehive.configuration.Constants;
import com.devicehive.messages.handler.HandlerCreator;
import com.devicehive.messages.subscriptions.AbstractStorage;
import com.devicehive.messages.subscriptions.Subscription;
import com.google.gson.JsonElement;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(JUnit4.class)
public class AbstractStorageTest {

    @Test
    public void insertGetTest() {
        AbstractStorage<Long, SubscriptionExtender> storage = new AbstractStorage<>();

        String subscriberId1 = "subscriberId_1";
        Long eventSourceId = 1l;
        SubscriptionExtender subscription1 = new SubscriptionExtender(eventSourceId, subscriberId1, null);
        storage.insert(subscription1);

        String subscriberId2 = "subscriberId_2";
        SubscriptionExtender subscription2 = new SubscriptionExtender(eventSourceId, subscriberId2, null);
        storage.insert(subscription2);
        storage.insert(subscription2);

        SubscriptionExtender subscription3 = new SubscriptionExtender(Constants
                .DEVICE_NOTIFICATION_NULL_ID_SUBSTITUTE, subscriberId1, null);
        storage.insert(subscription3);

        Set<SubscriptionExtender> extendersByEvent = storage.get(eventSourceId);
        assertEquals(2, extendersByEvent.size());
        Set<SubscriptionExtender> extendersBySubscription = storage.get(subscriberId1);
        assertEquals(2, extendersBySubscription.size());
        Set<SubscriptionExtender> otherExtenders = storage.get(Constants.DEVICE_NOTIFICATION_NULL_ID_SUBSTITUTE);
        assertEquals(1, otherExtenders.size());

    }

    @Test
    public void insertRemoveTest() {
        AbstractStorage<Long, SubscriptionExtender> storage = new AbstractStorage<>();

        String subscriberId1 = "subscriberId_1";
        Long eventSourceId = 1l;
        SubscriptionExtender subscription1 = new SubscriptionExtender(eventSourceId, subscriberId1, null);
        storage.insert(subscription1);

        String subscriberId2 = "subscriberId_2";
        SubscriptionExtender subscription2 = new SubscriptionExtender(eventSourceId, subscriberId2, null);
        storage.insert(subscription2);
        storage.insert(subscription2);

        String subscriberId3 = "other_1";
        SubscriptionExtender subscription3 = new SubscriptionExtender(Constants
                .DEVICE_NOTIFICATION_NULL_ID_SUBSTITUTE, subscriberId3, null);
        storage.insert(subscription3);
        storage.insert(subscription2);

        storage.remove(subscription2);

        Set<SubscriptionExtender> extendersByEventSource = storage.get(eventSourceId);
        assertEquals(1, extendersByEventSource.size());
        Set<SubscriptionExtender> extendersBySubscriber = storage.get(subscriberId2);

        Set<SubscriptionExtender> otherExtenders = storage.get(Constants.DEVICE_NOTIFICATION_NULL_ID_SUBSTITUTE);
        assertEquals(1, otherExtenders.size());
    }

    @Test
    public void insertRewriteTest() {
        AbstractStorage<Long, SubscriptionExtender> storage = new AbstractStorage<>();

        String subscriberId = "subscriberId";
        Long eventSourceId = 1l;
        SubscriptionExtender subscription1 =
                new SubscriptionExtender(eventSourceId, subscriberId, new HandlerCreator() {
                    @Override
                    public Runnable getHandler(JsonElement message) {
                        return null;
                    }
                });
        storage.insert(subscription1);

        SubscriptionExtender subscription2 = new SubscriptionExtender(eventSourceId, subscriberId, null);
        storage.insert(subscription2);

        Set<SubscriptionExtender> extenders = storage.get(eventSourceId);
        assertEquals(1, extenders.size());

        assertFalse(extenders.contains(subscription2));
    }

    @Test
    public void insertMultipleRemoveTest() {
        AbstractStorage<Long, SubscriptionExtender> storage = new AbstractStorage<>();

        String subscriberId = "subscriberId";
        Long eventSourceId = 1l;
        final SubscriptionExtender subscription1 = new SubscriptionExtender(eventSourceId, subscriberId, null);
        storage.insert(subscription1);

        String subscriberId2 = "subscriberId_2";
        final SubscriptionExtender subscription2 = new SubscriptionExtender(eventSourceId, subscriberId2, null);
        storage.insert(subscription2);
        storage.insert(subscription2);

        String subscriberId3 = "other_1";
        SubscriptionExtender subscription3 = new SubscriptionExtender(Constants
                .DEVICE_NOTIFICATION_NULL_ID_SUBSTITUTE, subscriberId3, null);
        storage.insert(subscription3);
        storage.insert(subscription2);

        Set<SubscriptionExtender> subscriptionSetToRemove = new HashSet<SubscriptionExtender>() {{
            add(subscription1);
            add(subscription2);
        }};

        storage.removeAll(subscriptionSetToRemove);

        Set<SubscriptionExtender> aliveExtendersThatShouldBeRemoved = storage.get(eventSourceId);
        assertEquals(0, aliveExtendersThatShouldBeRemoved.size());
    }

    @Test(expected = NullPointerException.class)
    public void nullInsertTest() {
        AbstractStorage<Long, SubscriptionExtender> storage = new AbstractStorage<>();
        storage.insert(new SubscriptionExtender(null, null, null));
        storage.insert(new SubscriptionExtender(null, "someId", null));
        storage.insert(new SubscriptionExtender(1l, null, null));
        storage.removeAll(null);
    }

    @Test
    public void removeBySubscriberTest() {
        AbstractStorage<Long, SubscriptionExtender> storage = new AbstractStorage<>();

        final String subscriberId = "subscriberId_1";
        final Long eventSourceId = 1l;
        SubscriptionExtender subscription1 = new SubscriptionExtender(eventSourceId, subscriberId, null);
        storage.insert(subscription1);

        final Long eventSourceId2 = 2l;
        SubscriptionExtender subscription2 = new SubscriptionExtender(eventSourceId2, subscriberId, null);
        storage.insert(subscription2);

        final String subscriberIdOther = "other_1";
        SubscriptionExtender subscription3 = new SubscriptionExtender(Constants
                .DEVICE_NOTIFICATION_NULL_ID_SUBSTITUTE, subscriberIdOther, null);
        storage.insert(subscription3);

        Collection<Pair<Long, String>> pairsToRemove = new HashSet<Pair<Long, String>>() {
            {
                add(ImmutablePair.of(eventSourceId2, subscriberId));
                add(ImmutablePair.of(Constants.DEVICE_NOTIFICATION_NULL_ID_SUBSTITUTE, subscriberIdOther));
            }

            private static final long serialVersionUID = -3382403110218581241L;
        };

        storage.removePairs(pairsToRemove);

        Set<SubscriptionExtender> extendersByEventSource = storage.get(eventSourceId);
        assertEquals(1, extendersByEventSource.size());

        Set<SubscriptionExtender> extendersBySubscriber = storage.get(subscriberId);
        assertEquals(1, extendersBySubscriber.size());

        Set<SubscriptionExtender> otherExtenders = storage.get(Constants.DEVICE_NOTIFICATION_NULL_ID_SUBSTITUTE);
        assertEquals(0, otherExtenders.size());
    }

    private class SubscriptionExtender extends Subscription<Long> {

        public SubscriptionExtender(Long eventSourceId, String subscriberId, HandlerCreator handlerCreator) {
            super(eventSourceId, subscriberId, handlerCreator);
        }
    }


}
