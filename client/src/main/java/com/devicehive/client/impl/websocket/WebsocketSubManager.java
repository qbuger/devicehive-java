package com.devicehive.client.impl.websocket;


import com.devicehive.client.impl.context.HiveContext;
import com.devicehive.client.impl.context.Subscription;
import com.devicehive.client.impl.json.GsonFactory;
import com.devicehive.client.impl.json.adapters.TimestampAdapter;
import com.devicehive.client.model.exceptions.HiveException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Set;

public class WebsocketSubManager {

    private static Logger logger = LoggerFactory.getLogger(WebsocketSubManager.class);
    private final HiveContext hiveContext;

    public WebsocketSubManager(HiveContext hiveContext) {
        this.hiveContext = hiveContext;
    }

    /**
     * Adds commands subscription to storage. Creates task that store commands in context's command queue. In case
     * when no device identifiers specified, subscription "for all available" will be added.
     *
     * @param timestamp first command timestamp
     * @param names     names of commands that defines
     * @param deviceIds devices identifiers of devices that should be subscribed
     */
    public synchronized void addCommandsSubscription(Timestamp timestamp,
                                                     Set<String> names, String... deviceIds) throws HiveException {
        JsonObject request = new JsonObject();
        request.addProperty("action", "command/subscribe");
        request.addProperty("timestamp", TimestampAdapter.formatTimestamp(timestamp));
        Gson gson = GsonFactory.createGson();
        request.add("deviceGuids", gson.toJsonTree(deviceIds));
        request.add("names", gson.toJsonTree(names));
        hiveContext.setLastCommandSubscription(new Subscription(timestamp, names, deviceIds));
        hiveContext.getHiveWebSocketClient().sendMessage(request);
    }

    /**
     * Put command updates into the queue as soon as update coming. Command update subscription adds when the command
     * insert executes.
     *
     * @param commandId command identifier
     * @param deviceId  device identifier
     */
    public synchronized void addCommandUpdateSubscription(long commandId, String deviceId) {
        //nothing to do - this subscription is added implicitly on command send
    }

    /**
     * Remove command subscription for all previous commands.
     */
    public synchronized void removeCommandSubscriptions() throws HiveException {
        JsonObject request = new JsonObject();
        request.addProperty("action", "command/unsubscribe");
        hiveContext.getHiveWebSocketClient().sendMessage(request);
    }

    /**
     * Adds subscription for notifications with following set of notification's names from device with defined device
     * identifiers. In case when no device identifiers specified, subscription for all available devices will be added.
     *
     * @param timestamp start timestamp
     * @param names     notifications names (statistics)
     * @param deviceIds device identifiers
     */
    public synchronized void addNotificationSubscription(Timestamp timestamp, Set<String> names,
                                                         String... deviceIds) throws HiveException {
        JsonObject request = new JsonObject();
        request.addProperty("action", "notification/subscribe");
        request.addProperty("timestamp", TimestampAdapter.formatTimestamp(timestamp));
        Gson gson = GsonFactory.createGson();
        request.add("deviceGuids", gson.toJsonTree(deviceIds));
        request.add("names", gson.toJsonTree(names));
        hiveContext.setLastCommandSubscription(new Subscription(timestamp, names, deviceIds));
        hiveContext.getHiveWebSocketClient().sendMessage(request);
    }

    /**
     * Remove notification subscription for all previous notifications.
     */
    public synchronized void removeNotificationSubscription() throws HiveException {
        JsonObject request = new JsonObject();
        request.addProperty("action", "notification/unsubscribe");
        hiveContext.getHiveWebSocketClient().sendMessage(request);
    }

    public synchronized void resubscribeAll() throws HiveException {
        Subscription lastCommand = hiveContext.getLastCommandSubscription();
        String[] commandsIds = new String[lastCommand.getDeviceIds().size()];
        commandsIds = lastCommand.getDeviceIds().toArray(commandsIds);
        addCommandsSubscription(lastCommand.getLastTimestamp(), lastCommand.getNames(), commandsIds);

        Subscription lastNotification = hiveContext.getLastNotificationSubscription();
        String[] notificationIds = new String[lastNotification.getDeviceIds().size()];
        notificationIds = lastNotification.getDeviceIds().toArray(notificationIds);
        addNotificationSubscription(lastNotification.getLastTimestamp(), lastNotification.getNames(), notificationIds);
    }

    public void close() {
        //nothing to do
    }
}
